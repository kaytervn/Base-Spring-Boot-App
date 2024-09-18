package com.app.validation.impl;

import com.app.constant.AppConstant;
import com.app.validation.EmailConstraint;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidation implements ConstraintValidator<EmailConstraint, String> {
    private boolean allowNull;
    private static final String PATTERN = AppConstant.EMAIL_PATTERN;

    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.isBlank(value) ? allowNull : StringUtils.isNotBlank(value) && value.matches(PATTERN);
    }
}
