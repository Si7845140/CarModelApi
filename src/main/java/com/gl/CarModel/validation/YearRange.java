//package com.gl.CarModel.validation;
//
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//// Custom annotation for year validation
//@Target({ ElementType.FIELD, ElementType.PARAMETER })
//@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = YearRangeValidator.class)
//public @interface YearRange {
//
//    String message() default "Year must be between 1970 and current year + 1";
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//}
//

package com.gl.CarModel.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YearRangeValidator.class)
public @interface YearRange {

    String message() default "Year must be between {minYear} and {maxYear}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
