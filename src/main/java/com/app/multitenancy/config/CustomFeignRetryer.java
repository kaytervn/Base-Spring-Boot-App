package com.app.multitenancy.config;

import com.app.service.impl.UserServiceImpl;
import com.app.multitenancy.component.ApplicationContextProvider;
import com.app.multitenancy.constant.FeignConstant;
import com.app.multitenancy.dto.LoginAuthDto;
import com.app.multitenancy.feign.FeignAccountAuthService;
import feign.RetryableException;
import feign.Retryer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomFeignRetryer implements Retryer {
    static final int DEFAULT_BACKOFF = 1000;
    final int maxAttempts;
    final long backoff;
    int attempt = 0;

    @Autowired
    public CustomFeignRetryer(@Value("${feign.client.retryer.config.maxAttempt}") int maxAttempts) {
        this(maxAttempts, DEFAULT_BACKOFF);
    }

    public CustomFeignRetryer(int maxAttempts, long backoff) {
        this.maxAttempts = maxAttempts;
        this.backoff = backoff;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        if (++attempt > maxAttempts) {
            throw e;
        }
        if (e.status() == 401 || e.status() == 403) {
            log.error("Feign retry attempt {} due to {}", attempt, e.getMessage());
            e.request().requestTemplate().removeHeader("Authorization");
            refreshAccessToken();
            sleep();
        } else {
            throw e;
        }
    }

    private void refreshAccessToken() {
        ApplicationContext applicationContext = ApplicationContextProvider.getContext();
        FeignAccountAuthService feignAccountAuthService = applicationContext.getBean(FeignAccountAuthService.class);
        UserServiceImpl userService = applicationContext.getBean(UserServiceImpl.class);
        String username = applicationContext.getEnvironment().getProperty("auth.internal.username");
        String password = applicationContext.getEnvironment().getProperty("auth.internal.password");
        userService.AUTH_SERVER_TOKEN = getNewAccessToken(feignAccountAuthService, username, password);
    }

    private String getNewAccessToken(FeignAccountAuthService feignAccountAuthService, String username, String password) {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("grant_type", "password");
        request.add("username", username);
        request.add("password", password);
        LoginAuthDto response = feignAccountAuthService.authLogin(FeignConstant.LOGIN_TYPE_INTERNAL, request);
        if (response == null || response.getAccessToken() == null) {
            throw new RuntimeException("Cannot get new access token");
        }
        return response.getAccessToken();
    }

    private void sleep() {
        try {
            Thread.sleep(backoff);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while sleeping", e);
        }
    }

    @Override
    public Retryer clone() {
        return new CustomFeignRetryer(maxAttempts, backoff);
    }
}
