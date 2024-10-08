package com.app.multitenancy.tenant;

import com.app.dto.ApiMessageDto;
import com.app.multitenancy.feign.FeignDbConfigAuthService;
import com.app.multitenancy.constant.TenantConstant;
import com.app.multitenancy.dto.DbConfigDto;
import com.google.common.cache.*;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Component
@Slf4j
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl implements ResourceLoaderAware {
    private LoadingCache<String, DataSource> dataSourcesMtApp;
    @Autowired
    private TenantDatabaseConfigProperties configProperties;
    @Autowired
    private FeignDbConfigAuthService dbConfigAuthService;
    @Value("${multitenancy.datasource-cache.maximumSize}")
    private Long maximumSize;
    @Value("${multitenancy.datasource-cache.expireAfterAccess}")
    private Integer expireAfterAccess;
    @Autowired
    @Qualifier("tenantLiquibaseProperties")
    private LiquibaseProperties liquibaseProperties;
    private ResourceLoader resourceLoader;

    @PostConstruct
    private void createCache() {
        dataSourcesMtApp = CacheBuilder.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(expireAfterAccess, TimeUnit.MINUTES)
                .removalListener((RemovalListener<String, DataSource>) removal -> {
                    HikariDataSource ds = (HikariDataSource) removal.getValue();
                    ds.close();
                    log.warn("Closed datasource: {}", ds.getPoolName());
                })
                .build(new CacheLoader<>() {
                    @Override
                    public DataSource load(@NonNull String key) {
                        log.warn("Loading tenant: {}", key);
                        ApiMessageDto<DbConfigDto> tenant = dbConfigAuthService.authGetByName(key);
                        if (tenant == null || !tenant.getResult() || tenant.getData() == null) {
                            throw new RuntimeException("No such tenant: " + key);
                        }
                        return createAndConfigureDataSource(tenant.getData(), false);
                    }
                });
    }

    @Override
    protected DataSource selectAnyDataSource() {
        if (dataSourcesMtApp.asMap().isEmpty()) {
            DbConfigDto defaultTenant = createDefaultTenantConfig();
            dataSourcesMtApp.asMap().put(defaultTenant.getName(), createAndConfigureDataSource(defaultTenant, false));
        }
        log.warn("selectAnyDataSource() called. Total tenants: {}", dataSourcesMtApp.size());
        return dataSourcesMtApp.asMap().values().iterator().next();
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        log.warn("Getting datasource for tenant: {}", tenantIdentifier);
        try {
            return dataSourcesMtApp.get(tenantIdentifier);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to load DataSource for tenant: " + tenantIdentifier, e);
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    private DbConfigDto createDefaultTenantConfig() {
        DbConfigDto tenant = new DbConfigDto();
        tenant.setUsername(configProperties.getUsername());
        tenant.setPassword(configProperties.getPassword());
        tenant.setDriverClassName(configProperties.getDriverClassName());
        tenant.setUrl(configProperties.getUrl());
        tenant.setName(TenantConstant.DEFAULT_TENANT_ID);
        return tenant;
    }

    private DataSource createAndConfigureDataSource(DbConfigDto dbConfig, boolean isBootstrap) {
        HikariDataSource ds = new HikariDataSource();
        ds.setUsername(dbConfig.getUsername());
        ds.setPassword(dbConfig.getPassword());
        ds.setJdbcUrl(dbConfig.getUrl());
        ds.setDriverClassName(dbConfig.getDriverClassName());
        ds.setConnectionTimeout(configProperties.getConnectionTimeout());
        ds.setMinimumIdle(configProperties.getMinIdle());
        ds.setMaximumPoolSize(dbConfig.getMaxConnection() != null ? dbConfig.getMaxConnection() : configProperties.getMaxPoolSize());
        ds.setIdleTimeout(configProperties.getIdleTimeout());
        ds.setPoolName(dbConfig.getName() + "-connection-pool");
        log.warn("Configured datasource for tenant: {}. Pool name: {}", dbConfig.getName(), ds.getPoolName());
        if (!isBootstrap) {
            runLiquibase(ds, parseDatabaseNameFromUrl(dbConfig.getUrl()));
        }
        return ds;
    }

    private String parseDatabaseNameFromUrl(String url) {
        String cleanString = url.substring("jdbc:mysql://".length(), url.indexOf("?"));
        return cleanString.substring(cleanString.indexOf("/") + 1);
    }

    private void runLiquibase(DataSource dataSource, String schema) {
        SpringLiquibase liquibase = getSpringLiquibase(dataSource, schema);
        try {
            liquibase.afterPropertiesSet();
        } catch (LiquibaseException e) {
            log.error("Error running Liquibase for schema: {}", schema, e);
        }
    }

    private SpringLiquibase getSpringLiquibase(DataSource dataSource, String schema) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setResourceLoader(resourceLoader);
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(schema);
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters() != null ? liquibaseProperties.getParameters() : Collections.singletonMap("schema", schema));
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setShouldRun(liquibaseProperties.isEnabled());
        liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());
        return liquibase;
    }

    /** DataSource Config to Run DDL Auto **/
    private void runDdlAuto(DataSource ds, String schema) {
        LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();
        emfBean.setDataSource(ds);
        emfBean.setPackagesToScan(TenantConstant.MODEL_PACKAGE, TenantConstant.REPOSITORY_PACKAGE);
        emfBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emfBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        Map<String, Object> properties = new HashMap<>();
        properties.put(Environment.HBM2DDL_AUTO, "update");
        properties.put(Environment.DEFAULT_SCHEMA, schema);
        emfBean.setJpaPropertyMap(properties);
        emfBean.setPersistenceUnitName(ds.toString());
        emfBean.afterPropertiesSet();
    }
}
