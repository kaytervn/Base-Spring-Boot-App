package auth.base.user.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    JwtAccessTokenConverter jwtAccessTokenConverter;
    JsonToUrlEncodedAuthenticationFilter jsonFilter;

    static String[] PUBLIC_ENDPOINTS = {
            "/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
            "/swagger-ui.html", "/index", "/pub/**", "/api/token", "/api/auth/pwd/verify-token",
            "/api/auth/activate/resend", "/api/auth/pwd", "/api/auth/logout", "/actuator/**",
            "/v1/account/request-forget-password", "/v1/account/forget-password",
            "/v1/file/download/**"
    };

    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        jwtAccessTokenConverter.setAccessTokenConverter(new CustomTokenConverter());
        services.setTokenStore(new JwtTokenStore(jwtAccessTokenConverter));
        return services;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(jsonFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(PUBLIC_ENDPOINTS).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("base-auth-user");
    }
}

