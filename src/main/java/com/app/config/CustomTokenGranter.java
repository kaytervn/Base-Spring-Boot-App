package com.app.config;

import com.app.constant.SecurityConstant;
import com.app.service.impl.UserServiceImpl;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

public class CustomTokenGranter extends AbstractTokenGranter {
    private final UserServiceImpl userService;

    public CustomTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType, UserServiceImpl userService) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.userService = userService;
    }

    @Override
    protected OAuth2AccessToken getAccessToken(ClientDetails client, TokenRequest tokenRequest) {
        if (SecurityConstant.GRANT_TYPE_PASSWORD.equalsIgnoreCase(tokenRequest.getGrantType())) {
            return userService.getAccessToken(client, tokenRequest, getTokenServices());
        } else if (SecurityConstant.GRANT_TYPE_USER.equalsIgnoreCase(tokenRequest.getGrantType())) {
            return userService.getAccessTokenForUser(client, tokenRequest, getTokenServices());
        } else {
            throw new InvalidTokenException("Invalid grant type: " + tokenRequest.getGrantType());
        }
    }
}