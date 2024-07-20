package auth.base.user.service.impl;

import auth.base.user.constant.AppConstant;
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
@Service(AppConstant.APP_USER_SERVICE)
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

    public OAuth2AccessToken getAccessToken(ClientDetails client, TokenRequest tokenRequest, String username, String password, AuthorizationServerTokenServices tokenServices) {
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("grantType", AppConstant.GRANT_TYPE_PASSWORD);
        OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, client.getClientId(),
                loadUserByUsername(username).getAuthorities(), true, client.getScope(),
                client.getResourceIds(), null, new HashSet<>(Collections.singletonList("code")), new HashMap<>());
        UserDetails userDetails = loadUserByUsername(username);
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