package com.alifetvaci.voyagelink.authservice.api.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ValidIdentificationNumberValidator.class)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIdentificationNumber {

    String message() default "Invalid Identification Number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}