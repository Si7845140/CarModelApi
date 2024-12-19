package com.gl.CarModel.validation;

import com.gl.CarModel.utils.StringFormatUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class MakeValidator implements ConstraintValidator<ValidMake, String> {


    private static final List<String> VALID_MAKES = Arrays.asList(
            "ACURA", "ALFA ROMEO", "AUDI", "BMW", "BUICK", "CADILLAC", "CHEVROLET", "CHRYSLER",
            "DODGE", "FERRARI", "FIAT", "FORD", "GMC", "HONDA", "HYUNDAI", "INFINITI", "JAGUAR",
            "JEEP", "KIA", "LAMBORGHINI", "LAND ROVER", "LEXUS", "LINCOLN", "MASERATI", "MAZDA",
            "MCLAREN", "MERCEDES-BENZ", "MINI", "MITSUBISHI", "NISSAN", "PAGANI", "PEUGEOT",
            "PORSCHE", "RAM", "RENAULT", "ROLLS-ROYCE", "SAAB", "SUBARU", "SUZUKI", "TESLA",
            "TOYOTA", "VOLKSWAGEN", "VOLVO", "ASTON MARTIN", "BENTLEY", "BUGATTI", "CATERHAM",
            "CHERY", "CITROËN", "DAIHATSU", "DONGFENG", "FISKER", "GEELY", "GREAT WALL", "HAVAL",
            "ISUZU", "JLR", "LADA", "LEXUS", "LINCOLN", "LOTUS", "MASERATI", "MAZDA", "MCLAREN",
            "NISSAN", "PAGANI", "PEUGEOT", "PORSCHE", "RAM", "RIVIAN", "SAAB", "SUBARU", "VAUXHALL",
            "VOLKSWAGEN", "WIESMANN", "ZOTYE", "BYD", "MAHINDRA", "TATA MOTORS", "MARUTI SUZUKI",
            "REVA", "PIAGGIO", "ASHOK LEYLAND", "FORCE MOTORS", "BAJAJ AUTO", "HERO MOTOCORP",
            "TVS MOTOR", "EICHER MOTORS", "SML ISUZU", "OKINAWA", "ATHER ENERGY", "EUREKA FORBES",
            "INDIAN MOTORCYCLES", "PEUGEOT", "GREAT WALL MOTORS", "MG MOTORS", "STELLANTIS", "DAIMLER",
            "RIVIAN", "BYD", "HAVAL", "TATA", "FIAT", "MITSUBISHI", "SKODA", "HONDA", "VOLKSWAGEN",
            "MAHINDRA & MAHINDRA", "GM", "STELLANTIS", "TATA", "FORD", "HYUNDAI", "BMW", "BAJAJ",
            "ISUZU", "TOYOTA", "SUZUKI", "MARUTI SUZUKI", "HONDA", "CHEVROLET"
    );

    @Override
    public void initialize(ValidMake constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }


        String upperCaseValue = value.trim().toUpperCase();


        boolean isValid = VALID_MAKES.contains(upperCaseValue);

        if (!isValid) {

            String validMakesMessage = String.join(", ", VALID_MAKES);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
//                            "Invalid car make. Put Valid makes."
                            StringFormatUtils.formatInvalidResourceMessage("Makes","Makes")
                    )
//                                    + validMakesMessage)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
