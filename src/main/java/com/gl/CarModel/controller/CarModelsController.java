package com.gl.CarModel.controller;

import com.gl.CarModel.dto.CarModelResponseDto;
import com.gl.CarModel.dto.CarModelsDto;
import com.gl.CarModel.dto.ModelColorsDto;
import com.gl.CarModel.service.Impl.CarModelsServiceImpl;
import com.gl.CarModel.utils.StringFormatUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
public class CarModelsController {

    @Autowired
    CarModelsServiceImpl carModelsService;

    @PostMapping("/v1/api/car/models")
    public ResponseEntity<CarModelResponseDto> addCarModels(@Valid @RequestBody CarModelsDto carModelsDto){
        CarModelResponseDto carModelsDtoSaved=carModelsService.addCarModels(carModelsDto);
        return new ResponseEntity<>(carModelsDtoSaved, HttpStatus.CREATED);
    }

    @GetMapping("/v1/api/car/models")
    public ResponseEntity<List<CarModelResponseDto>> getAllCarModels(){
        List<CarModelResponseDto>carModelsDtos=carModelsService.getAllCarModels();
        return new ResponseEntity<>(carModelsDtos, HttpStatus.OK);
    }

    @GetMapping("/v1/api/car/models/{models_id}")
    public ResponseEntity<CarModelResponseDto> getCarModelsById(@PathVariable Long models_id){
        CarModelResponseDto carModelsDto=carModelsService.getCarModelsById(models_id);
        return new ResponseEntity<>(carModelsDto, HttpStatus.OK);
    }

    @DeleteMapping("/v1/api/car/models/{id}")
    public ResponseEntity<String> deleteCarModelsById(@PathVariable Long id){
       carModelsService.deleteCarModelsById(id);
        String str= StringFormatUtils.formatDeletionMessage("CarModel",id);
        return ResponseEntity.status(HttpStatus.OK).body(str);
    }

    @PutMapping("/v1/api/cars/models/{id}")
    public ResponseEntity<CarModelResponseDto> updateCarModelsById(@PathVariable Long id,@Valid @RequestBody CarModelsDto carModelsDto){
        CarModelResponseDto carModelsDtoSaved=carModelsService.updateCarModelsById(id,carModelsDto);
        return new ResponseEntity<>(carModelsDtoSaved, HttpStatus.CREATED);

    }

    @GetMapping("/v1/api/cars/models/{year}")
    public ResponseEntity<List<CarModelResponseDto>> getAllCarModelsByYear(@PathVariable Integer year){
        List<CarModelResponseDto>carModelsDtos=carModelsService.getCarModelsByYear(year);
        return new ResponseEntity<>(carModelsDtos, HttpStatus.OK);
    }

    @GetMapping("/v1/api/cars/models/{year}/{makes_id}")
    public ResponseEntity<List<CarModelResponseDto>> getAllCarModelsByYearAndMakes(@PathVariable Integer year,@PathVariable String makes_id){
        List<CarModelResponseDto>carModelsDtos=carModelsService.getCarModelsByYearAndMakes(year,makes_id);
        return new ResponseEntity<>(carModelsDtos, HttpStatus.OK);
    }

    @GetMapping("/v1/api/cars/colors/{year}/{makes_id}/{name}")
    public ResponseEntity<List<ModelColorsDto>> getAllCarModelsByYearAndMakesAndName(@PathVariable Integer year,@PathVariable String makes_id,@PathVariable String name){
        List<ModelColorsDto>modelColorsDtos=carModelsService.getModelColorsByYearAndMakesAnsModel(year,makes_id,name);
        return new ResponseEntity<>(modelColorsDtos, HttpStatus.OK);
    }

}
