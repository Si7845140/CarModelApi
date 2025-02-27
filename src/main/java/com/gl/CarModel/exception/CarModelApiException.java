package com.gl.CarModel.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class CarModelApiException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    public CarModelApiException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
