package com.gl.CarModel.dto;

import lombok.Data;

import java.util.Date;



@Data
public class ErrorResponse {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String details;

    public ErrorResponse(int statusCode, Date timestamp, String message, String details) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }


    public String formatErrorResponse() {
        return "ErrorResponse{" +
                "statusCode=" + statusCode +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
