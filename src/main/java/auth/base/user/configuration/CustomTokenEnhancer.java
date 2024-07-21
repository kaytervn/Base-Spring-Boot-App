package auth.base.user.configuration;

import auth.base.user.dto.AccountForTokenDto;
import auth.base.user.utils.ZipUtils;
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
        if (grantType == null) {
            grantType = authentication.getOAuth2Request().getRequestParameters().get("grantType");
        }
        AccountForTokenDto account = getAccountByUsername(username);
        if (account != null) {
            additionalInfo = createAdditionalInfo(account, username, grantType);
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

    private Map<String, Object> createAdditionalInfo(AccountForTokenDto account, String username, String grantType) {
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("user_id", account.getId());
        additionalInfo.put("user_kind", account.getKind());
        additionalInfo.put("grant_type", grantType);
        String additionalInfoStr = ZipUtils.zipString(
                String.join(DELIM,
                        account.getId().toString(),
                        "-1", // storeId
                        account.getKind().toString(),
                        "<>", // permission
                        "-1", // deviceId
                        account.getKind().toString(),
                        username,
                        "-1", // tabletKind
                        "-1", // orderId
                        account.getIsSuperAdmin().toString()
                )
        );
        additionalInfo.put("additional_info", additionalInfoStr);
        return additionalInfo;
    }

    private AccountForTokenDto getAccountByUsername(String username) {
        String query = "SELECT id, kind, username, email, full_name, is_super_admin " +
                "FROM db_fn_account WHERE username = ? and status = 1 limit 1";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{username}, new BeanPropertyRowMapper<>(AccountForTokenDto.class));
        } catch (Exception e) {
            log.error("Error fetching account for username: {}", username, e);
            return null;
        }
    }
}
