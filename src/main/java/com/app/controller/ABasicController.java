package com.app.controller;

import com.app.dto.ApiMessageDto;
import com.app.service.impl.UserServiceImpl;
import com.app.jwt.AppJwt;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ABasicController {
    @Autowired
    UserServiceImpl userService;

    protected long getCurrentUser() {
        return getSessionFromToken().getAccountId();
    }

    protected long getTokenId() {
        return getSessionFromToken().getTokenId();
    }

    protected AppJwt getSessionFromToken() {
        return userService.getAddInfoFromToken();
    }

    protected boolean isSuperAdmin() {
        AppJwt appJwt = getSessionFromToken();
        return appJwt != null && appJwt.getIsSuperAdmin();
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
