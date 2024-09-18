package com.app.validation;

import com.app.validation.impl.GroupKindValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GroupKindValidation.class)
@Documented
public @interface GroupKind {
    boolean allowNull() default false;
    String message() default "Group kind is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
