package com.user.center.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Bean validation annotation to check allowed values
 */
@Documented
@Inherited
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { AllowedValuesValidator.class })
public @interface AllowedValues {

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
    String allowedValues();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
