package com.user.center.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bean Validation: valid when "given day > current day + given day interval"
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DayAfterValidator.class})
public @interface DayAfter {

    String message() default "Field not valid";

    int dayAfter() default 2;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}