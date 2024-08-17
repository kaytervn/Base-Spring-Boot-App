package com.app.service;

import com.app.configuration.CustomFeignConfig;
import com.app.dto.account.LoginAuthDto;
import com.app.form.account.LoginAccountForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "account-svr", url = "${auth.internal.auth.url}", configuration = CustomFeignConfig.class)
public interface FeignAccountAuthService {
    String AUTHORIZATION_HEADER = "Authorization";

    @PostMapping("/api/token")
    LoginAuthDto authLogin(@RequestHeader(AUTHORIZATION_HEADER) String authorization, @RequestBody LoginAccountForm loginAccountForm);
}