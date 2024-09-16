package com.app.multitenancy.feign;

import com.app.multitenancy.config.CustomFeignConfig;
import com.app.multitenancy.dto.LoginAuthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-svr", url = "${auth.internal.base.url}", configuration = CustomFeignConfig.class)
public interface FeignAccountAuthService {
    String LOGIN_TYPE = "BASIC_LOGIN_AUTH";
    String TOKEN_TYPE = "TOKEN_TYPE";

    @PostMapping(value = "/api/token")
    LoginAuthDto authLogin(@RequestHeader(LOGIN_TYPE) String type, @RequestParam MultiValueMap<String,String> request);
}
