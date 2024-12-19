package com.gl.CarModel.dto;

import com.gl.CarModel.validation.ValidColor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ModelColorsResponseDto {
    private Long color_id;

    private String color;
}
