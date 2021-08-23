package com.user.center.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeAnnoValidator implements ConstraintValidator<AgeAnno, Integer> {
    @Override
    public void initialize(AgeAnno constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer age, ConstraintValidatorContext constraintValidatorContext) {
        if (age == null) {
            return false;
        }
        return age >= 18;
    }
}