
package com.gl.CarModel.validation;

import com.gl.CarModel.utils.StringFormatUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class YearRangeValidator implements ConstraintValidator<YearRange, Integer> {

    private int minYear;
    private int maxYear;

    @Override
    public void initialize(YearRange constraintAnnotation) {
        minYear = 1970;
        maxYear = Year.now().getValue() + 1;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        boolean isValid = value >= minYear && value <= maxYear;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
//                    "Year must be between " + minYear + " and " + maxYear
                    StringFormatUtils.formatYearRangeMessage(minYear,maxYear)

            ).addConstraintViolation();
        }

        return isValid;
    }
}
