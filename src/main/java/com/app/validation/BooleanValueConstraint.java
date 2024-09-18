package com.app.validation;

import com.app.validation.impl.BooleanValueValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BooleanValueValidation.class)
@Documented
public @interface BooleanValueConstraint {
    boolean allowNull() default false;
    String message() default "Value must be 0 or 1";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
