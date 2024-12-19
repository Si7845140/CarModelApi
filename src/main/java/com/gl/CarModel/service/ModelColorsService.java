package com.gl.CarModel.service;

import com.gl.CarModel.dto.ModelColorsDto;
import com.gl.CarModel.dto.ModelColorsResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ModelColorsService {
//    ModelColorsDto addModelColors(ModelColorsDto modelColorsDto);
//    List<ModelColorsDto>getAllModelColors();
//    ModelColorsDto getModelColorsById(Long id);
//    void deleteModelColorsById(Long id);
      ModelColorsResponseDto addModelColors(ModelColorsDto modelColorsDto);
      List<ModelColorsResponseDto>getAllModelColors();
      ModelColorsResponseDto getModelColorsById(Long id);
      void deleteModelColorsById(Long id);

}
