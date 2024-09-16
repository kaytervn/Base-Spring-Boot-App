package com.app.component;

import com.app.context.constant.ContextConstant;
import com.app.multitenancy.tenant.TenantDBContext;
import com.app.service.impl.UserServiceImpl;
import com.app.jwt.AppJwt;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogInterceptor implements HandlerInterceptor {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    @Qualifier(ContextConstant.APP_CONFIG_MAP)
    ConcurrentMap<String, String> concurrentMap;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        tenantResolver(request);
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        log.debug("Starting call url: [{}]", getUrl(request));
        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute("startTime");
        long executeTime = System.currentTimeMillis() - startTime;
        log.debug("Complete [{}] executeTime: {} ms", getUrl(request), executeTime);
        if (ex != null) {
            log.error("afterCompletion>> {}", ex.getMessage());
        }
    }

    private String getUrl(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();
        if (queryString != null) {
            requestURL.append('?').append(queryString);
        }
        return requestURL.toString();
    }

    private void appVerification(HttpServletRequest request) {
        List<String> allowedUrls = Arrays.asList("/v1/account/**", "/v1/group/**", "/v1/permission/**");
        AntPathMatcher pathMatcher = new AntPathMatcher();
        AppJwt appJwt = userService.getAddInfoFromToken();
        if (appJwt != null && concurrentMap.get(ContextConstant.APP_PRIVATE_KEY) == null) {
            boolean isNotSuperAdmin = !appJwt.getIsSuperAdmin();
            boolean isUrlNotAllowed = allowedUrls.stream().noneMatch(pattern -> pathMatcher.match(pattern, request.getRequestURI()));
            if (isNotSuperAdmin || isUrlNotAllowed) {
                throw new AccessDeniedException("Not ready");
            }
        }
    }

    private void tenantResolver(HttpServletRequest request) {
        AppJwt appJwt = userService.getAddInfoFromToken();
        String tenantName = request.getHeader("X-tenant");
        String jwtTenantId = appJwt.getTenantId() != null && appJwt.getTenantId().contains("&") ? appJwt.getTenantId().split("&")[0] : null;
        TenantDBContext.setCurrentTenant(tenantName != null ? tenantName : jwtTenantId);
    }
}
