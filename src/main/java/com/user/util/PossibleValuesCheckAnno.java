package com.user.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Inherited
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { PossibleValuesCheckAnnoValidator.class })
public @interface PossibleValuesCheckAnno {

    /**
     * Message
     * 
     * @return
     */
    String message() default "error";

    /**
     * Set possible values
     * 
     * @return
     */
    String possibleValues();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
