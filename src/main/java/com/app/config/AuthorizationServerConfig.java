package com.app.config;

import com.app.constant.SecurityConstant;
import com.app.exception.oauth2.CustomOauthException;
import com.app.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.*;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserServiceImpl userService;
    @Value("${auth.signing.key}")
    String signingKey;

    @Bean
    public TokenStore tokenStore() {
        JdbcTokenStore store = new JdbcTokenStore(Objects.requireNonNull(jdbcTemplate.getDataSource()));
        store.setAuthenticationKeyGenerator(new CustomAuthenticationKeyGenerator());
        return store;
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(new CustomTokenConverter());
        converter.setSigningKey(signingKey);
        return converter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer.jdbc(jdbcTemplate.getDataSource());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new CustomTokenEnhancer(jdbcTemplate), accessTokenConverter()));
        endpoints
                .pathMapping("/oauth/authorize", "/api/authorize")
                .pathMapping("/oauth/token", "/api/token")
                .authenticationManager(authenticationManager)
                .tokenEnhancer(tokenEnhancerChain)
                .tokenGranter(tokenGranter(endpoints))
                .accessTokenConverter(accessTokenConverter())
                .tokenStore(tokenStore())
                .reuseRefreshTokens(false)
                .userDetailsService(userDetailsService)
                .exceptionTranslator(exception -> {
                    if (exception instanceof OAuth2Exception) {
                        OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
                        return ResponseEntity
                                .status(oAuth2Exception.getHttpErrorCode())
                                .body(new CustomOauthException(oAuth2Exception.getMessage()));
                    }
                    throw exception;
                });
    }

    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
        granters.add(new CustomTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), SecurityConstant.GRANT_TYPE_PASSWORD, userService));
        granters.add(new CustomTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), SecurityConstant.GRANT_TYPE_USER, userService));
        return new CompositeTokenGranter(granters);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.allowFormAuthenticationForClients()
                .tokenKeyAccess("hasRole('TRUSTED_CLIENT')")
                .checkTokenAccess("permitAll()");
    }
}