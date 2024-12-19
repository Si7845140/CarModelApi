package com.gl.CarModel.service.Impl;

import com.gl.CarModel.dto.ModelColorsDto;
import com.gl.CarModel.dto.ModelColorsResponseDto;
import com.gl.CarModel.entity.ModelColors;
import com.gl.CarModel.exception.ResourceNotFoundException;
import com.gl.CarModel.repository.ModelColorsRepo;
import com.gl.CarModel.service.ModelColorsService;
import com.gl.CarModel.utils.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelColorsServiceImpl implements ModelColorsService {
    private static final Logger logger = LoggerFactory.getLogger(ModelColorsServiceImpl.class);

    @Autowired
    private ModelColorsRepo modelColorsRepo;

    @Override
    public ModelColorsResponseDto addModelColors(ModelColorsDto modelColorsDto) {
        logger.info("Entering addModelColors with modelColorsDto: {}", modelColorsDto);

        ModelColors modelColors = Mapper.mapToModelColorsEntity(modelColorsDto);
        modelColors.setColor(modelColors.getColor().trim().toUpperCase());

        logger.debug("Transformed ModelColors: {}", modelColors);

        ModelColors modelColorsSaved = modelColorsRepo.save(modelColors);
        logger.info("ModelColors saved successfully with ID: {}", modelColorsSaved.getColor_id());

        ModelColorsResponseDto modelColorsDtoSaved = Mapper.mapToModelColorsResponseDto(modelColorsSaved);
        logger.info("Returning ModelColorsDto: {}", modelColorsDtoSaved);

        return modelColorsDtoSaved;
    }

    @Override
    public List<ModelColorsResponseDto> getAllModelColors() {
        logger.info("Entering getAllModelColors");

        List<ModelColors> modelColors = modelColorsRepo.findAll();
        List<ModelColorsResponseDto> modelColorsDtos = new ArrayList<>();
        for (ModelColors color : modelColors) {
            modelColorsDtos.add(Mapper.mapToModelColorsResponseDto(color));
        }

        logger.info("Retrieved {} model colors", modelColorsDtos.size());
        return modelColorsDtos;
    }

    @Override
    public ModelColorsResponseDto getModelColorsById(Long id) {
        logger.info("Entering getModelColorsById with ID: {}", id);

        ModelColors modelColors = modelColorsRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("ModelColor not found for ID: {}", id);
                    return new ResourceNotFoundException("Color", "Id", id);
                });

        ModelColorsResponseDto modelColorsResponseDto = Mapper.mapToModelColorsResponseDto(modelColors);
        logger.info("Returning ModelColorsDto: {}", modelColorsResponseDto);

        return modelColorsResponseDto;
    }

    @Override
    public void deleteModelColorsById(Long id) {
        logger.info("Entering deleteModelColorsById with ID: {}", id);

        ModelColors modelColors = modelColorsRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("ModelColor not found for ID: {}", id);
                    return new ResourceNotFoundException("Color", "Id", id);
                });

        modelColorsRepo.delete(modelColors);
        logger.info("Deleted ModelColor with ID: {}", id);
    }
}
