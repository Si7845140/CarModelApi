package com.gl.CarModel.service.Impl;

import com.gl.CarModel.dto.ModelMakesDto;
import com.gl.CarModel.dto.ModelMakesResponseDto;
import com.gl.CarModel.entity.ModelMakes;
import com.gl.CarModel.exception.ResourceNotFoundException;
import com.gl.CarModel.repository.ModelMakesRepo;
import com.gl.CarModel.service.ModelMakesService;
import com.gl.CarModel.utils.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelMakesServiceImpl implements ModelMakesService {
    private static final Logger logger = LoggerFactory.getLogger(ModelMakesServiceImpl.class);

    @Autowired
    private ModelMakesRepo modelMakesRepo;

    @Override
    public ModelMakesResponseDto addModelMakes(ModelMakesDto modelMakesDto) {
        logger.info("Entering addModelMakes with modelMakesDto: {}", modelMakesDto);

        ModelMakes modelMakes = Mapper.mapToModelMakesEntity(modelMakesDto);
        modelMakes.setMakes(modelMakes.getMakes().trim().toUpperCase());

        logger.debug("Transformed ModelMakes: {}", modelMakes);

        ModelMakes modelMakesSaved = modelMakesRepo.save(modelMakes);
        logger.info("ModelMakes saved successfully with ID: {}", modelMakesSaved.getMakes_id());

        ModelMakesResponseDto modelMakesResponseDtoSaved = Mapper.mapToModelMakesResponseDto(modelMakesSaved);
        logger.info("Returning ModelMakesDto: {}", modelMakesResponseDtoSaved);

        return modelMakesResponseDtoSaved;
    }

    @Override
    public List<ModelMakesResponseDto> getAllModelMakes() {
        logger.info("Entering getAllModelMakes");

        List<ModelMakes> modelMakes = modelMakesRepo.findAll();
        List<ModelMakesResponseDto> modelMakesResponseDtos = new ArrayList<>();
        for (ModelMakes make : modelMakes) {
            modelMakesResponseDtos.add(Mapper.mapToModelMakesResponseDto(make));
        }

        logger.info("Retrieved {} model makes", modelMakesResponseDtos.size());
        return modelMakesResponseDtos;
    }

    @Override
    public ModelMakesResponseDto getModelMakesById(Long make_id) {
        logger.info("Entering getModelMakesById with ID: {}", make_id);

        ModelMakes modelMakes = modelMakesRepo.findById(make_id)
                .orElseThrow(() -> {
                    logger.error("ModelMake not found for ID: {}", make_id);
                    return new ResourceNotFoundException("Make", "Id", make_id);
                });

        ModelMakesResponseDto modelMakesResponseDto = Mapper.mapToModelMakesResponseDto(modelMakes);
        logger.info("Returning ModelMakesDto: {}", modelMakesResponseDto);

        return modelMakesResponseDto;
    }

    @Override
    public void deleteModelMakesById(Long make_id) {
        logger.info("Entering deleteModelMakesById with ID: {}", make_id);

        ModelMakes modelMakes = modelMakesRepo.findById(make_id)
                .orElseThrow(() -> {
                    logger.error("ModelMake not found for ID: {}", make_id);
                    return new ResourceNotFoundException("Make", "Id", make_id);
                });

        modelMakesRepo.delete(modelMakes);
        logger.info("Deleted ModelMake with ID: {}", make_id);
    }
}

