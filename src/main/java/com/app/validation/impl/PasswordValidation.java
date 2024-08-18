package com.app.validation.impl;

import com.app.constant.AppConstant;
import com.app.validation.PasswordConstraint;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordValidation implements ConstraintValidator<PasswordConstraint, String> {
    boolean allowNull;

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(value) && allowNull) {
            return true;
        }
        return StringUtils.isNotBlank(value) && value.matches(AppConstant.PASSWORD_PATTERN);
    }
}
