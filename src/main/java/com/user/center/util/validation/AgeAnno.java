package com.user.center.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { AgeAnnoValidator.class })
public @interface AgeAnno {


    String message() default "error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}