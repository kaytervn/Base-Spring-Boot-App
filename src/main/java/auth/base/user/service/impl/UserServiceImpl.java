package auth.base.user.service.impl;

import auth.base.user.constant.AppConstant;
import auth.base.user.constant.EnumDef;
import auth.base.user.model.Account;
import auth.base.user.repository.AccountRepository;
import auth.base.user.service.AppJwt;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service(value = AppConstant.APP_USER_SERVICE)
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserDetailsService {
    AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        Account user = accountRepository.findAccountByUsername(userId).orElse(null);
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
        Account user = accountRepository.findAccountByPhone(phone)
                .filter(u -> Objects.equals(EnumDef.STATUS_ACTIVE, u.getStatus()))
                .orElseThrow(() -> new UsernameNotFoundException("Invalid phone"));
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
            if (oauthDetails != null) {
                Map<String, Object> decodedDetails = (Map<String, Object>) oauthDetails.getDecodedDetails();
                String encodedData = (String) decodedDetails.get("additional_info");
                if (encodedData != null && !encodedData.isEmpty()) {
                    return AppJwt.decode(encodedData);
                }
            }
        }
        return null;
    }
}