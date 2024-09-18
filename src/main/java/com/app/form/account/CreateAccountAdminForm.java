package com.app.form.account;

import com.app.validation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateAccountAdminForm {
    @UsernameConstraint
    @ApiModelProperty(name = "username", required = true)
    private String username;
    @PasswordConstraint
    @ApiModelProperty(name = "password", required = true)
    private String password;
    @NotBlank(message = "fullName cannot be null")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;
    @ApiModelProperty(name = "avatar")
    private String avatar;
    @EmailConstraint
    @ApiModelProperty(name = "email", required = true)
    private String email;
    @PhoneConstraint
    @ApiModelProperty(name = "phone", required = true)
    private String phone;
    @AccountKind
    @ApiModelProperty(name = "kind", required = true)
    private Integer kind;
    @StatusConstraint
    @ApiModelProperty(name = "status", required = true)
    private Integer status;
    @NotNull(message = "groupId cannot be null")
    @ApiModelProperty(name = "groupId", required = true)
    private Long groupId;
}
