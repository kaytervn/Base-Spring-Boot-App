package com.app.form.account;

import com.app.validation.AccountKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccountAdminForm {
    @NotBlank(message = "username is required")
    String username;
    @NotBlank(message = "password is required")
    String password;
    @NotBlank(message = "fullName is required")
    String fullName;
    String avatarPath;
    @Email
    @NotBlank(message = "email is required")
    String email;
    String phone;
    @NotNull(message = "kind is required")
    @AccountKind
    Integer kind;
    @NotNull(message = "status is required")
    Integer status;
    @NotNull(message = "groupId is required")
    Long groupId;
}
