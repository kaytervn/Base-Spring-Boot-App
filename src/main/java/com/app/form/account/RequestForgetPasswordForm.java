package com.app.form.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestForgetPasswordForm {
    @Email
    @NotBlank(message = "email is required")
    @ApiModelProperty(required = true)
    String email;
}
