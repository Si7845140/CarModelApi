package com.gl.CarModel.service;

import com.gl.CarModel.dto.CarModelResponseDto;
import com.gl.CarModel.dto.CarModelsDto;
import com.gl.CarModel.dto.ModelColorsDto;
import com.gl.CarModel.entity.ModelColors;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarModelsService{
    CarModelResponseDto addCarModels(CarModelsDto carModelsDto);
    List<CarModelResponseDto>getAllCarModels();
    CarModelResponseDto getCarModelsById(Long models_id);
    CarModelResponseDto updateCarModelsById(Long models_id,CarModelsDto carModelsDto);
    void deleteCarModelsById(Long models_id);
    List<CarModelResponseDto>getCarModelsByYear(Integer year);
    List<CarModelResponseDto>getCarModelsByYearAndMakes(Integer year,String makes_id);
    List<ModelColorsDto>getModelColorsByYearAndMakesAnsModel(Integer year, String makes_id, String model_name);



}
