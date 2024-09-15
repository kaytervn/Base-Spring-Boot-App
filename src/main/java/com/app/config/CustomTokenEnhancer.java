package com.app.config;

import com.app.dto.account.AccountForTokenDto;
import com.app.utils.ZipUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomTokenEnhancer implements TokenEnhancer {
    JdbcTemplate jdbcTemplate;
    static String DELIM = "|";

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        String username = authentication.getName();
        String grantType = authentication.getOAuth2Request().getGrantType();
        String permission = authentication.getAuthorities().toString();
        if (grantType == null) {
            grantType = authentication.getOAuth2Request().getRequestParameters().get("grantType");
        }
        AccountForTokenDto account = getAccountByUsername(username);
        if (account != null) {
            additionalInfo = createAdditionalInfo(account, username, grantType, permission);
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

    private Map<String, Object> createAdditionalInfo(AccountForTokenDto account, String username, String grantType, String permission) {
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("user_id", account.getId());
        additionalInfo.put("user_kind", account.getKind());
        additionalInfo.put("grant_type", grantType);
        String additionalInfoStr = ZipUtils.zipString(
                account.getId() + DELIM
                        + -1L + DELIM                        // storeId, long value
                        + account.getKind() + DELIM          // kind: type of token
                        + permission + DELIM                 // permission: empty string
                        + -1L + DELIM                        // deviceId: stored in the device table to get the Firebase URL
                        + account.getKind() + DELIM          // userKind: type of user (e.g., admin or other types)
                        + username + DELIM                   // username
                        + -1 + DELIM                         // tabletKind: type of tablet, int value
                        + -1L + DELIM                        // orderId, long value
                        + account.getIsSuperAdmin() + DELIM  // isSuperAdmin
                        + "<>"                               // tenantId: empty string
        );
        additionalInfo.put("additional_info", additionalInfoStr);
        return additionalInfo;
    }

    private AccountForTokenDto getAccountByUsername(String username) {
        String query = "SELECT id, kind, username, email, full_name, is_super_admin " +
                "FROM db_app_account WHERE username = ? and status = 1 limit 1";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{username}, new BeanPropertyRowMapper<>(AccountForTokenDto.class));
        } catch (Exception e) {
            log.error("Error fetching account for username: {}", username, e);
            return null;
        }
    }
}
