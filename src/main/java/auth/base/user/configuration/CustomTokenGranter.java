package auth.base.user.configuration;

import auth.base.user.constant.AppConstant;
import auth.base.user.service.impl.UserServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomTokenGranter extends AbstractTokenGranter {
    UserServiceImpl userService;

    public CustomTokenGranter(AuthenticationManager authenticationManager,
                              AuthorizationServerTokenServices tokenServices,
                              ClientDetailsService clientDetailsService,
                              OAuth2RequestFactory requestFactory,
                              String grantType,
                              UserServiceImpl userService) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.userService = userService;
    }

    @Override
    protected OAuth2AccessToken getAccessToken(ClientDetails client, TokenRequest tokenRequest) {
        if (AppConstant.GRANT_TYPE_PASSWORD.equalsIgnoreCase(tokenRequest.getGrantType())) {
            return userService.getAccessToken(client, tokenRequest, getTokenServices());
        } else if (AppConstant.GRANT_TYPE_USER.equalsIgnoreCase(tokenRequest.getGrantType())) {
            return userService.getAccessTokenForUser(client, tokenRequest, getTokenServices());
        } else {
            throw new InvalidTokenException("Invalid grant type: " + tokenRequest.getGrantType());
        }
    }
}