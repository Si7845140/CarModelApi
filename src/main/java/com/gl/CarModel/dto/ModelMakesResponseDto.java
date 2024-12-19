package com.gl.CarModel.dto;

import com.gl.CarModel.validation.ValidMake;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ModelMakesResponseDto {
    private Long makes_id;

    private String makes;

}
