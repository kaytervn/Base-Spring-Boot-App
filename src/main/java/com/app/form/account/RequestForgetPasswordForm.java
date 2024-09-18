package com.app.form.account;

import com.app.validation.EmailConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestForgetPasswordForm {
    @EmailConstraint
    @ApiModelProperty(name = "email", required = true)
    private String email;
}
