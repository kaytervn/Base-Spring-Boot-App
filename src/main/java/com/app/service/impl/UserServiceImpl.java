package com.app.service.impl;

import com.app.constant.AppConstant;
import com.app.constant.AppEnum;
import com.app.model.Account;
import com.app.repository.AccountRepository;
import com.app.jwt.AppJwt;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service(value = AppConstant.APP_USER_SERVICE)
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserDetailsService {
    public String AUTH_SERVER_TOKEN = "";
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        Account user = accountRepository.findFirstByUsername(userId).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        boolean enabled = user.getStatus() == 1;
        Set<SimpleGrantedAuthority> authorities = getAccountPermissions(user);
        return new User(user.getUsername(), user.getPassword(), enabled, true, true, true, authorities);
    }

    private Set<SimpleGrantedAuthority> getAccountPermissions(Account user) {
        return user.getGroup().getPermissions().stream()
                .filter(p -> p.getPermissionCode() != null)
                .map(p -> new SimpleGrantedAuthority("ROLE_" + p.getPermissionCode().toUpperCase()))
                .collect(Collectors.toSet());
    }

    public OAuth2AccessToken getAccessToken(ClientDetails client, TokenRequest tokenRequest, AuthorizationServerTokenServices tokenServices) {
        String username = tokenRequest.getRequestParameters().get("username");
        UserDetails userDetails = loadUserByUsername(username);
        return createAccessToken(client, userDetails, AppConstant.GRANT_TYPE_PASSWORD, tokenServices);
    }

    public OAuth2AccessToken getAccessTokenForUser(ClientDetails client, TokenRequest tokenRequest, AuthorizationServerTokenServices tokenServices) {
        String phone = tokenRequest.getRequestParameters().get("phone");
        String password = tokenRequest.getRequestParameters().get("password");
        if (!(StringUtils.isNotBlank(phone) && phone.matches(AppConstant.PHONE_PATTERN))) {
            throw new InvalidClientException("Invalid phone number format");
        }
        if (!(StringUtils.isNotBlank(password) && password.matches(AppConstant.PASSWORD_PATTERN))) {
            throw new InvalidClientException("Password must be at least 6 characters");
        }
        Account user = accountRepository.findFirstByPhone(phone)
                .filter(u -> Objects.equals(AppEnum.STATUS_ACTIVE, u.getStatus()))
                .orElseThrow(() -> new InvalidClientException("User not found with this phone number"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidClientException("Invalid password");
        }
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        return createAccessToken(client, userDetails, AppConstant.GRANT_TYPE_USER, tokenServices);
    }

    private OAuth2AccessToken createAccessToken(ClientDetails client, UserDetails userDetails, String grantType, AuthorizationServerTokenServices tokenServices) {
        Map<String, String> requestParameters = Map.of("grantType", grantType);
        OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, client.getClientId(), userDetails.getAuthorities(), true, client.getScope(), client.getResourceIds(), null, Set.of("code"), Map.of());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        OAuth2Authentication auth = new OAuth2Authentication(oAuth2Request, authenticationToken);
        return tokenServices.createAccessToken(auth);
    }

    @SuppressWarnings("unchecked")
    public AppJwt getAddInfoFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
            if (oauthDetails != null && oauthDetails.getDecodedDetails() != null) {
                Map<String, Object> decodedDetails = (Map<String, Object>) oauthDetails.getDecodedDetails();
                String encodedData = (String) decodedDetails.get("additional_info");
                if (encodedData != null && !encodedData.isEmpty()) {
                    return AppJwt.decode(encodedData);
                }
            }
        }
        return null;
    }

    public String getCurrentToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
        return oauthDetails != null ? oauthDetails.getTokenValue() : null;
    }
}