package com.gl.CarModel.controller;

import com.gl.CarModel.dto.ModelColorsDto;
import com.gl.CarModel.dto.ModelMakesDto;
import com.gl.CarModel.dto.ModelMakesResponseDto;
import com.gl.CarModel.service.Impl.ModelColorsServiceImpl;
import com.gl.CarModel.service.Impl.ModelMakesServiceImpl;
import com.gl.CarModel.utils.StringFormatUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ModelMakesController {

    @Autowired
    ModelMakesServiceImpl modelMakesService;

    @PostMapping("/v1/api/makes")
    public ResponseEntity<ModelMakesResponseDto> addModelMakes(@Valid @RequestBody ModelMakesDto modelMakesDto){

        ModelMakesResponseDto ModelMakesDtoSaved=modelMakesService.addModelMakes(modelMakesDto);
        return new ResponseEntity<>(ModelMakesDtoSaved, HttpStatus.CREATED);
    }

    @GetMapping("/v1/api/makes")
    public ResponseEntity<List<ModelMakesResponseDto>> getAllModelMakes(){

        List<ModelMakesResponseDto>modelMakesDtos=modelMakesService.getAllModelMakes();
        return new ResponseEntity<>(modelMakesDtos, HttpStatus.OK);
    }

    @GetMapping("/v1/api/makes/{makes_id}")
    public ResponseEntity<ModelMakesResponseDto> getModelMakesById(@PathVariable Long makes_id){
        ModelMakesResponseDto modelMakesDto=modelMakesService.getModelMakesById(makes_id);
        return new ResponseEntity<>(modelMakesDto, HttpStatus.OK);
    }

    @DeleteMapping("/v1/api/makes/{makes_id}")
    public ResponseEntity<String> deleteModelMakesById(@PathVariable Long makes_id){
        modelMakesService.deleteModelMakesById(makes_id);
        String str= StringFormatUtils.formatDeletionMessage("Makes",makes_id);
        return ResponseEntity.status(HttpStatus.OK).body(str);
    }
}
