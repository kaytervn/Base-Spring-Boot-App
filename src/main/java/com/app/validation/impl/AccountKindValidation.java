package com.app.validation.impl;

import com.app.constant.AppConstant;
import com.app.validation.AccountKind;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class AccountKindValidation implements ConstraintValidator<AccountKind, Integer> {
    private boolean allowNull;
    private static final List<Integer> VALID_VALUES = List.of(
            AppConstant.ACCOUNT_KIND_ADMIN, AppConstant.ACCOUNT_KIND_USER, AppConstant.ACCOUNT_KIND_MANAGER
    );

    @Override
    public void initialize(AccountKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null ? allowNull : VALID_VALUES.contains(value);
    }
}