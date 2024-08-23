package com.app.form.account;

import com.app.validation.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccountAdminForm {
    @UsernameConstraint
    String username;
    @PasswordConstraint
    String password;
    @NotBlank(message = "fullName is required")
    String fullName;
    String avatar;
    @EmailConstraint
    String email;
    @PhoneConstraint
    String phone;
    @AccountKind
    Integer kind;
    @StatusConstraint
    Integer status;
    @NotNull(message = "groupId is required")
    Long groupId;
}
