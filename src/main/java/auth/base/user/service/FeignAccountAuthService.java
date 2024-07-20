package auth.base.user.service;

import auth.base.user.dto.account.LoginAuthDto;
import auth.base.user.form.account.LoginAccountForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

//@FeignClient(name = "account-svr", url = "${auth.internal.auth.url}", configuration = CustomFeignConfig.class)
public interface FeignAccountAuthService {
    String AUTHORIZATION_HEADER = "Authorization";

    @PostMapping("/api/token")
    LoginAuthDto authLogin(@RequestHeader(AUTHORIZATION_HEADER) String authorization, @RequestBody LoginAccountForm loginAccountForm);
}

