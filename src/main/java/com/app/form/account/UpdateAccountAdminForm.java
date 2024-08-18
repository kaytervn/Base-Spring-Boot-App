package com.app.form.account;

import com.app.validation.EmailConstraint;
import com.app.validation.PhoneConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAccountAdminForm {
    @NotNull(message = "id is required")
    Long id;
    @Size(min = 6, message = "password must be at least 6 characters long.")
    String password;
    @NotBlank(message = "fullName is required")
    String fullName;
    String avatar;
    @EmailConstraint
    String email;
    @PhoneConstraint
    String phone;
    @NotNull(message = "status is required")
    Integer status;
    @NotNull(message = "groupId is required")
    Long groupId;
}
