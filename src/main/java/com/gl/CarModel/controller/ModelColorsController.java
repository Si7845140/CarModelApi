package com.gl.CarModel.controller;

import com.gl.CarModel.dto.ModelColorsDto;
import com.gl.CarModel.dto.ModelColorsResponseDto;
import com.gl.CarModel.entity.ModelColors;
import com.gl.CarModel.service.Impl.ModelColorsServiceImpl;
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
public class ModelColorsController {

    @Autowired
    ModelColorsServiceImpl modelColorsService;
    @PostMapping("/v1/api/colors")
    public ResponseEntity<ModelColorsResponseDto> addModelColor(@Valid @RequestBody ModelColorsDto modelColorsDto){
        ModelColorsResponseDto ModelColorsDtoSaved=modelColorsService.addModelColors(modelColorsDto);

        return new ResponseEntity<>(ModelColorsDtoSaved, HttpStatus.CREATED);
    }

    @GetMapping("/v1/api/colors")
    public ResponseEntity<List<ModelColorsResponseDto>> getAllModelColors(){
        List<ModelColorsResponseDto>modelColorsDtos=modelColorsService.getAllModelColors();

        return new ResponseEntity<>(modelColorsDtos, HttpStatus.OK);
    }

    @GetMapping("/v1/api/colors/{color_id}")
    public ResponseEntity<ModelColorsResponseDto> getModelColorsById(@PathVariable Long color_id){
        ModelColorsResponseDto modelColorsDto=modelColorsService.getModelColorsById(color_id);

        return new ResponseEntity<>(modelColorsDto, HttpStatus.OK);
    }

    @DeleteMapping("/v1/api/colors/{id}")
    public ResponseEntity<String> deleteModelColorsById(@PathVariable Long id){
        modelColorsService.deleteModelColorsById(id);
        String str= StringFormatUtils.formatDeletionMessage("Color",id);
        return ResponseEntity.status(HttpStatus.OK).body(str);
    }

}
