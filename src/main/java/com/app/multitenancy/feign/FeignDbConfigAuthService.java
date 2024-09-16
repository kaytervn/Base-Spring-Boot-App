package com.app.multitenancy.feign;

import com.app.dto.ApiMessageDto;
import com.app.multitenancy.config.CustomFeignConfig;
import com.app.multitenancy.dto.DbConfigDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "dbconfig-svr", url = "${auth.internal.base.url}", configuration = CustomFeignConfig.class)
public interface FeignDbConfigAuthService {
    @GetMapping(value = "/v1/db-config/get_by_name")
    ApiMessageDto<DbConfigDto> authGetByName(@RequestParam(value = "name") String name);

    @GetMapping(value = "/v1/db-config/list")
    ApiMessageDto<List<DbConfigDto>> authGetList();
}
