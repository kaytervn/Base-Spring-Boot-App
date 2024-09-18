package com.app.form.account;

import com.app.validation.EmailConstraint;
import com.app.validation.PasswordConstraint;
import com.app.validation.PhoneConstraint;
import com.app.validation.StatusConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateAccountAdminForm {
    @NotNull(message = "id cannot be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
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
    @StatusConstraint
    @ApiModelProperty(name = "status", required = true)
    private Integer status;
    @NotNull(message = "groupId cannot be null")
    @ApiModelProperty(name = "groupId", required = true)
    private Long groupId;
}
