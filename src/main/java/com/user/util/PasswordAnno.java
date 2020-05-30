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
@Constraint(validatedBy = { PasswordAnnoValidator.class })
public @interface PasswordAnno {

    /**
     * Message
     * 
     * @return
     */
    String message() default "error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
