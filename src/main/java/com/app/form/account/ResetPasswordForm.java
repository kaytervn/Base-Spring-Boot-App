package com.app.form.account;

import com.app.validation.PasswordConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordForm {
    @NotBlank(message = "otp is required")
    String otp;
    @NotBlank(message = "userId is required")
    String userId;
    @PasswordConstraint
    String newPassword;
}
