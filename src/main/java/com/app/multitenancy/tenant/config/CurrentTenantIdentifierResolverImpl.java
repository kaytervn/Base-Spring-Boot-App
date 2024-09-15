package com.app.multitenancy.tenant.config;

import com.app.multitenancy.tenant.TenantDBContext;
import com.app.multitenancy.tenant.constant.TenantConstant;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
    String defaultTenant = TenantConstant.DEFAULT_TENANT_ID;

    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantDBContext.getCurrentTenant() != null ? TenantDBContext.getCurrentTenant() : defaultTenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
