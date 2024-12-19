//package com.gl.CarModel.exception;
//
//import lombok.Getter;
//
//
//@Getter
//public class SizeLimitExceededException extends RuntimeException {
//    private String resourceName;
//    private String fieldName;
//    private int size;
//
//    public SizeLimitExceededException(String resourceName, String fieldName, int size) {
//        super(String.format("Size of %s for %s should not be greater than 1, but was: %d", fieldName, resourceName, size));
//        this.resourceName = resourceName;
//        this.fieldName = fieldName;
//        this.size = size;
//    }
//}

package com.gl.CarModel.exception;

import com.gl.CarModel.utils.StringFormatUtils;
import lombok.Getter;

@Getter
public class SizeLimitExceededException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private int size;

    public SizeLimitExceededException(String resourceName, String fieldName, int size) {
        super(StringFormatUtils.formatSizeLimitExceededMessage(resourceName, fieldName, size));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.size = size;
    }
}
