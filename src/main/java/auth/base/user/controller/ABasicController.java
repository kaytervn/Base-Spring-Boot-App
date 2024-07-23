package auth.base.user.controller;

import auth.base.user.dto.ApiMessageDto;
import auth.base.user.service.impl.UserServiceImpl;
import auth.base.user.utils.JwtUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class ABasicController {
    UserServiceImpl userService;

    protected long getCurrentUser() {
        return getSessionFromToken().getAccountId();
    }

    protected long getTokenId() {
        return getSessionFromToken().getTokenId();
    }

    protected JwtUtils getSessionFromToken() {
        return userService.getAddInfoFromToken();
    }

    protected boolean isSuperAdmin() {
        JwtUtils financeJwt = getSessionFromToken();
        return financeJwt != null && financeJwt.getIsSuperAdmin();
    }

    protected String getCurrentToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
        return oauthDetails != null ? oauthDetails.getTokenValue() : null;
    }

    protected <T> ApiMessageDto<T> makeErrorResponse(String code, String message) {
        ApiMessageDto<T> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setResult(false);
        apiMessageDto.setCode(code);
        apiMessageDto.setMessage(message);
        return apiMessageDto;
    }

    protected <T> ApiMessageDto<T> makeSuccessResponse(T data, String message) {
        ApiMessageDto<T> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(data);
        apiMessageDto.setMessage(message);
        return apiMessageDto;
    }
}
