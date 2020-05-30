package com.user.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordAnnoValidator implements ConstraintValidator<PasswordAnno, String> {

    @Override
    public void initialize(PasswordAnno constraintAnnotation) {
    }

    @Override
    public boolean isValid(String givenStr, ConstraintValidatorContext constraintValidatorContext) {
        // Only character and numbers is accepted
        if (givenStr != null && givenStr.length() > 8 && givenStr.length() <= 18) {
            if (givenStr.matches("([a-z[A-Z]][0-9]*)*") || givenStr.matches("([0-9][a-z[A-Z]]*)*")) {
                return true;
            }

        }
        return false;
    }

}