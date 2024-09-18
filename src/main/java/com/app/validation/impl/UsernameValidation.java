package com.app.validation.impl;

import com.app.constant.AppConstant;
import com.app.validation.UsernameConstraint;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidation implements ConstraintValidator<UsernameConstraint, String> {
    private boolean allowNull;
    private static final String PATTERN = AppConstant.USERNAME_PATTERN;

    @Override
    public void initialize(UsernameConstraint constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.isBlank(value) ? allowNull : StringUtils.isNotBlank(value) && value.matches(PATTERN);
    }
}
