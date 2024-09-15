package com.app.multitenancy.feign.config;

import com.app.exception.BadRequestException;
import com.app.exception.ForbiddenException;
import com.app.exception.NotFoundException;
import com.app.exception.UnauthorizationException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomFeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        String requestInfo = String.format("[%s] %s", response.request().httpMethod(), response.request().url());
        String errorMessage = response.body() != null ? response.body().toString() : "No error message available";
        switch (response.status()) {
            case 400:
                return new BadRequestException(errorMessage);
            case 401:
                return new UnauthorizationException("No access to " + requestInfo);
            case 403:
                return new ForbiddenException("No access to " + requestInfo);
            case 404:
                return new NotFoundException("Resource not found");
            default:
                log.error("Unexpected error: {} - {}", response.status(), errorMessage);
                return new RuntimeException("Unexpected error occurred");
        }
    }
}
