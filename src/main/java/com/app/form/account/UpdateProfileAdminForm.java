package com.app.form.account;

import com.app.validation.PasswordConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProfileAdminForm {
    @NotBlank(message = "fullName is required")
    String fullName;
    String avatar;
    @PasswordConstraint
    String oldPassword;
}
