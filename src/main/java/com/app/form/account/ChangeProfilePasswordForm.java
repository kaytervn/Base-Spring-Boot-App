package com.app.form.account;

import com.app.validation.PasswordConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeProfilePasswordForm {
    @PasswordConstraint
    private String oldPassword;
    @PasswordConstraint
    private String newPassword;
}
