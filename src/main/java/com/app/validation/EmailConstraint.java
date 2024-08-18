package com.app.validation;

import com.app.validation.impl.EmailValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidation.class)
@Documented
public @interface EmailConstraint {
    boolean allowNull() default false;

    String message() default "Email must be a well-formed email address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
