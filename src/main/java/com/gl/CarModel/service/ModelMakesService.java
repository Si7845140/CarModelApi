package com.gl.CarModel.service;

import com.gl.CarModel.dto.ModelColorsDto;
import com.gl.CarModel.dto.ModelMakesDto;
import com.gl.CarModel.dto.ModelMakesResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ModelMakesService {
//    ModelMakesDto addModelMakes(ModelMakesDto modelMakesDto);
//    List<ModelMakesDto> getAllModelMakes();
//    ModelMakesDto getModelMakesById(Long id);
//    void deleteModelMakesById(Long id);

    ModelMakesResponseDto addModelMakes(ModelMakesDto modelMakesDto);
    List<ModelMakesResponseDto> getAllModelMakes();
    ModelMakesResponseDto getModelMakesById(Long id);
    void deleteModelMakesById(Long id);

}
