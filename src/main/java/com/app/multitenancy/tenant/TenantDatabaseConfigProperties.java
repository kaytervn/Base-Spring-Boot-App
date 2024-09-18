package com.app.multitenancy.tenant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties("multitenancy.tenant.datasource")
public class TenantDatabaseConfigProperties {
    private Long connectionTimeout;
    private Integer maxPoolSize;
    private Long idleTimeout;
    private Integer minIdle;
    private String dialect;
    private boolean showSql;
    private String ddlAuto;
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
