package com.app.validation.impl;

import com.app.constant.AppConstant;
import com.app.validation.BooleanValueConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class BooleanValueValidation implements ConstraintValidator<BooleanValueConstraint, Integer> {
    private boolean allowNull;
    private static final List<Integer> VALID_VALUES = List.of(
            AppConstant.BOOLEAN_TRUE, AppConstant.BOOLEAN_FALSE
    );

    @Override
    public void initialize(BooleanValueConstraint constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null ? allowNull : VALID_VALUES.contains(value);
    }
}
