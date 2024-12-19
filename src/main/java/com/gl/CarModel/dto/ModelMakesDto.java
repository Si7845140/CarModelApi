package com.gl.CarModel.dto;

import com.gl.CarModel.validation.ValidMake;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data

public class ModelMakesDto {


    @ValidMake
    private String makes;


}
