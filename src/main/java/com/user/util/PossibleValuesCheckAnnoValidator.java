package com.user.util;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PossibleValuesCheckAnnoValidator implements ConstraintValidator<PossibleValuesCheckAnno, String> {
    private String constraintAnnotation;

    @Override
    public void initialize(PossibleValuesCheckAnno constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation.possibleValues();
    }

    @Override
    public boolean isValid(String givenStr, ConstraintValidatorContext constraintValidatorContext) {
        return this.check(givenStr, constraintAnnotation);
    }

    private boolean check(String givenStr, String constraintAnnotation) {
        boolean isValid = false;
        List<String> list = this.convertToList(constraintAnnotation);
        if (list.contains(givenStr)) {
            isValid = true;
        }
        return isValid;
    }

    private List<String> convertToList(String constraintAnnotation) {
        List<String> list = new ArrayList<String>();

        String[] strArray = constraintAnnotation.split(",");
        for (String OneStr : strArray) {
            list.add(OneStr.trim());
        }
        return list;
    }
}