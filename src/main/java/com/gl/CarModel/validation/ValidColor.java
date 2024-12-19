package com.gl.CarModel.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ColorValidator.class)
public @interface ValidColor {

    String message() default "Invalid color:Colors must be Valid Colors and Capitalize .Reference is given below";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

