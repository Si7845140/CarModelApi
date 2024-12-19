package com.gl.CarModel.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class CarModelResponseDto {
    private Long model_id;
    private String makes_name;
    private String model_name;
    private List<String> colors;
    private Integer year;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
}
