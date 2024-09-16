package com.app.multitenancy.component;

import com.app.service.impl.UserServiceImpl;
import com.app.multitenancy.constant.FeignConstant;
import com.app.multitenancy.feign.FeignAccountAuthService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Data
@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {
    static final String AUTHORIZATION_HEADER = "Authorization";
    static final String BEARER_TOKEN_TYPE = "Bearer";
    static final String BASIC_AUTH_TYPE = "Basic";
    @Value("${auth.internal.basic.username}")
    String internalAuthUsername;
    @Value("${auth.internal.basic.password}")
    String internalAuthPassword;
    @Autowired
    UserServiceImpl userService;

    @Override
    public void apply(RequestTemplate template) {
        Collection<String> tokenTypeHeader = template.headers().get(FeignAccountAuthService.TOKEN_TYPE);
        Collection<String> loginTypeHeader = template.headers().get(FeignAccountAuthService.LOGIN_TYPE);
        if (tokenTypeHeader != null && !tokenTypeHeader.isEmpty()) {
            String tokenType = tokenTypeHeader.iterator().next();
            if (Objects.equals(tokenType, FeignConstant.TOKEN_TYPE_PROJECT)) {
                template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, userService.getCurrentToken()));
                return;
            } else {
                log.error("-----------> not found token ype = {}", tokenType);
                return;
            }
        }
        if (loginTypeHeader != null && !loginTypeHeader.isEmpty()) {
            String loginType = loginTypeHeader.iterator().next();
            if (Objects.equals(loginType, FeignConstant.LOGIN_TYPE_INTERNAL)) {
                String auth = internalAuthUsername + ":" + internalAuthPassword;
                log.error("-----------> internal = {}", auth);
                String encodedAuth = Base64.encodeBase64String(auth.getBytes(StandardCharsets.UTF_8));
                template.header(AUTHORIZATION_HEADER, String.format("%s %s", BASIC_AUTH_TYPE, encodedAuth));
                template.removeHeader(FeignAccountAuthService.LOGIN_TYPE);
                return;
            } else {
                log.error("-----------> not found login type = {}", loginType);
                return;
            }
        }
        String defaultToken = String.format("%s %s", BEARER_TOKEN_TYPE, userService.AUTH_SERVER_TOKEN);
        log.error("-----------> Constructing Header {} for Token {}, token {}", AUTHORIZATION_HEADER, BEARER_TOKEN_TYPE, defaultToken);
        template.header(AUTHORIZATION_HEADER, defaultToken);
    }
}
