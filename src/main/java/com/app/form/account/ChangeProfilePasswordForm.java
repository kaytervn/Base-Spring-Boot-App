package com.app.form.account;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeProfilePasswordForm {
    @Size(min = 6, message = "oldPassword must be at least 6 characters long.")
    private String oldPassword;
    @Size(min = 6, message = "newPassword must be at least 6 characters long.")
    private String newPassword;
}
