package com.gl.CarModel.utils;

import com.gl.CarModel.CarModelApplication;
import com.gl.CarModel.dto.*;
import com.gl.CarModel.entity.CarModels;
import com.gl.CarModel.entity.ModelColors;
import com.gl.CarModel.entity.ModelMakes;
import com.gl.CarModel.repository.ModelColorsRepo;
import com.gl.CarModel.repository.ModelMakesRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class Mapper {


    public static ModelColorsDto mapToModelColorsDto(ModelColors modelColors) {
        return CarModelApplication.modelMapper().map(modelColors, ModelColorsDto.class);

    }

    public static ModelColorsResponseDto mapToModelColorsResponseDto(ModelColors modelColors) {
        return CarModelApplication.modelMapper().map(modelColors, ModelColorsResponseDto.class);

    }

    public static ModelColors mapToModelColorsEntity(ModelColorsDto modelColorsDto) {
        return CarModelApplication.modelMapper().map(modelColorsDto, ModelColors.class);

    }

    public static ModelMakesDto mapToModelMakesDto(ModelMakes modelMakes) {
        return CarModelApplication.modelMapper().map(modelMakes, ModelMakesDto.class);

    }

    public static ModelMakesResponseDto mapToModelMakesResponseDto(ModelMakes modelMakes) {
        return CarModelApplication.modelMapper().map(modelMakes, ModelMakesResponseDto.class);

    }

    public static ModelMakes mapToModelMakesEntity(ModelMakesDto modelMakesDto) {
        return CarModelApplication.modelMapper().map(modelMakesDto, ModelMakes.class);

    }

    public static CarModelsDto mapToCarModelsDto(CarModels carModels) {
        return CarModelApplication.modelMapper().map(carModels, CarModelsDto.class);

    }

    public static CarModels mapToCarModelsEntity(CarModelsDto carModelsDto) {
        return CarModelApplication.modelMapper().map(carModelsDto, CarModels.class);

    }

    public static CarModelResponseDto mapToCarModelResponseDto(CarModels carModels) {
        CarModelResponseDto carModelResponseDto=new CarModelResponseDto();

        carModelResponseDto.setMakes_name(carModels.getMakes_id());
        carModelResponseDto.setModel_id(carModels.getModel_id());
        carModelResponseDto.setModel_name(carModels.getModel_name());
        carModelResponseDto.setColors(new ArrayList<>());
        carModelResponseDto.setYear(carModels.getYear());
//        carModelResponseDto.setCreatedAt(carModels.getCreatedAt());
//        carModelResponseDto.setUpdatedAt(carModels.getUpdatedAt());
        return carModelResponseDto;

    }





}
