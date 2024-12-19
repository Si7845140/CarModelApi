package com.gl.CarModel.validation;

import com.gl.CarModel.utils.StringFormatUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class ColorValidator implements ConstraintValidator<ValidColor, String> {


    private static final List<String> VALID_COLORS = Arrays.asList(
            "ALABASTER WHITE", "ALUMINUM", "ASH", "ATOMIC ORANGE", "BURGUNDY", "CARMINE RED",
            "CHARCOAL", "CHERRY RED", "CRYSTAL BLACK", "DARK BLUE", "DARK BROWN", "DARK GREEN",
            "DARK PURPLE", "DEEP BLUE", "DIAMOND WHITE", "EBONY", "ELECTRIC BLUE", "ELECTRIC GREEN",
            "EMERALD GREEN", "FIRE RED", "FOREST GREEN", "GOLD", "GRAPHITE", "GRANITE", "GRAY",
            "GUNMETAL", "ICE SILVER", "INDIGO", "IVORY", "JET BLACK", "KONA BLUE", "LIME GREEN",
            "MAGENTA", "MATTE BLACK", "METALLIC BLUE", "METALLIC GREEN", "MIDNIGHT BLACK",
            "MIDNIGHT BLUE", "MOSS GREEN", "NARDO GRAY", "ORANGE", "OXFORD WHITE", "PEARL WHITE",
            "PLATINUM", "PORSCHE RED", "RACING GREEN", "RAVEN BLACK", "RED", "ROYAL BLUE",
            "SAPPHIRE BLUE", "SILVER", "SLATE", "SMOKY QUARTZ", "SNOW WHITE", "SONIC SILVER",
            "SPACE GRAY", "SPARKLING SILVER", "SUNSET ORANGE", "SUPER WHITE", "TANGERINE",
            "TITANIUM", "TUNGSTEN", "VERMILLION RED", "VIVID BLUE", "WHITE", "YELLOW"
    );

    @Override
    public void initialize(ValidColor constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }


        String upperCaseValue = value.trim().toUpperCase();


        boolean isValid = VALID_COLORS.contains(upperCaseValue);

        if (!isValid) {

            String validColorsMessage = String.join(", ", VALID_COLORS);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            StringFormatUtils.formatInvalidResourceMessage("Color","Colors")
//                            "Invalid color.Put Valid colors"
                    )
//                                    + validColorsMessage)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
