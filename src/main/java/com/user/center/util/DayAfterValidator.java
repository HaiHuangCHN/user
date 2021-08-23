package com.user.center.util;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DayAfterValidator implements ConstraintValidator<DayAfter, String> {

    private int dayAfter;

    @Override
    public void initialize(DayAfter constraintAnnotation) {
        this.dayAfter = constraintAnnotation.dayAfter();
    }

    @Override
    public boolean isValid(String actualDayStr, ConstraintValidatorContext constraintValidatorContext) {
        if (actualDayStr == null) {
            log.error("Actual day is null! Please check: actualDay=" + actualDayStr);
            return false;
        }

        LocalDateTime actualDay = LocalDateTime.parse(actualDayStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime futureDay = LocalDateTime.now().plusDays(dayAfter);

        // Note: Allow equals
        if (actualDay.isEqual(futureDay)) {
            return true;
        }

        if (actualDay.isBefore(futureDay)) {
            log.error("Expect at least after: " + futureDay + ", actual: " + actualDay);
            return false;
        }

        return true;
    }

}