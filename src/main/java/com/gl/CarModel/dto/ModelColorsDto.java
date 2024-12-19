package com.gl.CarModel.dto;

import com.gl.CarModel.validation.ValidColor;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data

public class ModelColorsDto {

//    private Long color_id;
    @ValidColor
    private String color;

//    private LocalDateTime createdAt;
//
//    private LocalDateTime updatedAt;

}
