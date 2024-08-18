package com.app.validation;

import com.app.validation.impl.PasswordValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidation.class)
@Documented
public @interface PasswordConstraint {
    boolean allowNull() default false;

    String message() default "Password must be at least 6 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
