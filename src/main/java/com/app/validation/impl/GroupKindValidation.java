package com.app.validation.impl;

import com.app.constant.AppEnum;
import com.app.validation.GroupKind;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupKindValidation implements ConstraintValidator<GroupKind, Integer> {
    boolean allowNull;

    @Override
    public void initialize(GroupKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return allowNull;
        }
        return Set.of(AppEnum.GROUP_KIND_ADMIN, AppEnum.GROUP_KIND_MANAGER, AppEnum.GROUP_KIND_USER).contains(value);
    }
}