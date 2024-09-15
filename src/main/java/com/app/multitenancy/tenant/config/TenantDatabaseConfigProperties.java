package com.app.multitenancy.tenant.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("multitenancy.tenant.datasource")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TenantDatabaseConfigProperties {
    Long connectionTimeout;
    Integer maxPoolSize;
    Long idleTimeout;
    Integer minIdle;
    String dialect;
    boolean showSql;
    String ddlAuto;
    String url;
    String username;
    String password;
    String driverClassName;
}
