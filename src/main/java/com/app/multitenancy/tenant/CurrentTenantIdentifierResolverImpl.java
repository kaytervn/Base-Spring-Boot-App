package com.app.multitenancy.tenant;

import com.app.multitenancy.constant.TenantConstant;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
    private static final String defaultTenant = TenantConstant.DEFAULT_TENANT_ID;

    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantDBContext.getCurrentTenant() != null ? TenantDBContext.getCurrentTenant() : defaultTenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
