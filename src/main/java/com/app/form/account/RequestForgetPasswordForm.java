package com.app.form.account;

import com.app.validation.EmailConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestForgetPasswordForm {
    @EmailConstraint
    String email;
}
