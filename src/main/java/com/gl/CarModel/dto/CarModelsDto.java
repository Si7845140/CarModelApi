package com.gl.CarModel.dto;

import com.gl.CarModel.validation.YearRange;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Validated
public class CarModelsDto {
    @NotBlank(message = "Must Contain Something")
    private String makes_id;
    @NotBlank(message = "Must Contain Something")
    private String model_name;
    @NotBlank(message = "Must Contain Something")
    private String color_id;
    @YearRange
    private Integer year;
}
