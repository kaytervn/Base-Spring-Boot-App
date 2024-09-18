package com.app.form.account;

import com.app.validation.PasswordConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChangeProfilePasswordForm {
    @PasswordConstraint
    @ApiModelProperty(name = "oldPassword", required = true)
    private String oldPassword;
    @PasswordConstraint
    @ApiModelProperty(name = "newPassword", required = true)
    private String newPassword;
}
