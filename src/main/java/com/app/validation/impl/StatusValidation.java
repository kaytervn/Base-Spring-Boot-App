package com.app.validation.impl;

import com.app.constant.AppConstant;
import com.app.validation.StatusConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class StatusValidation implements ConstraintValidator<StatusConstraint, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(StatusConstraint constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null ? allowNull : List.of(
                AppConstant.STATUS_ACTIVE,
                AppConstant.STATUS_PENDING,
                AppConstant.STATUS_LOCK
        ).contains(value);
    }
}