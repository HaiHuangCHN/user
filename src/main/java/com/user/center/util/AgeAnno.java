package com.user.center.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { AgeAnnoValidator.class })
public @interface AgeAnno {
    String message() default "error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}