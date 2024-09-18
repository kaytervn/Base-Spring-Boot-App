package com.app.form.account;

import com.app.validation.PasswordConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateProfileAdminForm {
    @NotBlank(message = "fullName cannot be null")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;
    @ApiModelProperty(name = "avatar")
    private String avatar;
    @PasswordConstraint
    @ApiModelProperty(name = "oldPassword", required = true)
    private String oldPassword;
}
