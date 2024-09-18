package com.app.form.account;

import com.app.validation.PasswordConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordForm {
    @NotBlank(message = "otp cannot be null")
    @ApiModelProperty(name = "otp", required = true)
    private String otp;
    @NotBlank(message = "userId cannot be null")
    @ApiModelProperty(name = "userId", required = true)
    private String userId;
    @PasswordConstraint
    @ApiModelProperty(name = "newPassword", required = true)
    private String newPassword;
}
