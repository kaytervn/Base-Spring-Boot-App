package com.app.form.account;

import com.app.validation.EmailConstraint;
import com.app.validation.PasswordConstraint;
import com.app.validation.PhoneConstraint;
import com.app.validation.StatusConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAccountAdminForm {
    @NotNull(message = "id is required")
    Long id;
    @PasswordConstraint
    String password;
    @NotBlank(message = "fullName is required")
    String fullName;
    String avatar;
    @EmailConstraint
    String email;
    @PhoneConstraint
    String phone;
    @StatusConstraint
    Integer status;
    @NotNull(message = "groupId is required")
    Long groupId;
}
