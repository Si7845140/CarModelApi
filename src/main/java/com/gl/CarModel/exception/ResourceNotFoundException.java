//package com.gl.CarModel.exception;
//
//import lombok.Getter;
//
//@Getter
//public class ResourceNotFoundException extends RuntimeException {
//    private String resourceName;
//    private String fieldName;
//    private Object fieldValue;
//    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
//        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
//        this.resourceName = resourceName;
//        this.fieldName = fieldName;
//        this.fieldValue = fieldValue;
//    }
//}

package com.gl.CarModel.exception;

import com.gl.CarModel.utils.StringFormatUtils;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(StringFormatUtils.formatResourceNotFoundMessage(resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
