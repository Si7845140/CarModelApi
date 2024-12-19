
package com.gl.CarModel.utils;

public class StringFormatUtils {


    public static String formatResourceNotFoundMessage(String resourceName, String fieldName, Object fieldValue) {
        return String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue);
    }

    // Utility method to format the exception message for SizeLimitExceededException
    public static String formatSizeLimitExceededMessage(String resourceName, String fieldName, int size) {
        return String.format("Size of %s for %s should not be greater than 1, but was: %d", fieldName, resourceName, size);
    }

    public static String formatDataAlreadyExist() {
        return String.format("The value you have put already exits in database put a new value");
    }

    public static String formatDuplicateKey() {
        return String.format("Duplicate Key Error");
    }

    public static String formatValidationFailed() {
        return String.format("Validation failed");
    }
    public static String formatDeletionMessage(String resourceName, Long id) {
        return String.format("You have successfully deleted %s with: %s", resourceName, id);
    }
    public static String formatInvalidResourceMessage(String resourceName, String action) {
        return String.format("Invalid %s. Please provide a valid %s.", resourceName, action);
    }
    public static String formatYearRangeMessage(int minYear, int maxYear) {
        return String.format("Year must be between %d and %d", minYear, maxYear);
    }

}
