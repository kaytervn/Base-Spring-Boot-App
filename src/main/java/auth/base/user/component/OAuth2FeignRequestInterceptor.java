package auth.base.user.component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {
    static String AUTH_SERVER_TOKEN = "";
    static String AUTHORIZATION_HEADER = "Authorization";
    static String BEARER_TOKEN_TYPE = "Bearer";

    @Override
    public void apply(RequestTemplate template) {
        String authHeader = String.format("%s %s", BEARER_TOKEN_TYPE, AUTH_SERVER_TOKEN);
        template.header(AUTHORIZATION_HEADER, authHeader);
        log.debug("Added Authorization header: {}", authHeader);
    }
}
