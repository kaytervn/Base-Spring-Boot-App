package com.app.validation.impl;

import com.app.constant.AppEnum;
import com.app.validation.StatusConstraint;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusValidation implements ConstraintValidator<StatusConstraint, Integer> {
    boolean allowNull;

    @Override
    public void initialize(StatusConstraint constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return allowNull;
        }
        return Set.of(AppEnum.STATUS_ACTIVE, AppEnum.STATUS_PENDING, AppEnum.STATUS_LOCK).contains(value);
    }
}