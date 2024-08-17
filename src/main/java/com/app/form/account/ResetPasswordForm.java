package com.app.form.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordForm {
    @NotBlank(message = "otp is required")
    @ApiModelProperty(required = true)
    String otp;
    @NotBlank(message = "userId is required")
    @ApiModelProperty(required = true)
    String userId;
    @Size(min = 6, message = "newPassword minimum 6 character")
    @NotBlank(message = "newPassword is required")
    @ApiModelProperty(required = true)
    String newPassword;
}
