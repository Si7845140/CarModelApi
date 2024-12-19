
package com.gl.CarModel.service.Impl;

import com.gl.CarModel.controller.CarModelsController;
import com.gl.CarModel.dto.CarModelResponseDto;
import com.gl.CarModel.dto.CarModelsDto;
import com.gl.CarModel.dto.ModelColorsDto;
import com.gl.CarModel.entity.CarModels;
import com.gl.CarModel.entity.ModelColors;
import com.gl.CarModel.entity.ModelMakes;
import com.gl.CarModel.exception.ResourceNotFoundException;
import com.gl.CarModel.exception.SizeLimitExceededException;
import com.gl.CarModel.repository.CarModelsRepo;
import com.gl.CarModel.repository.ModelColorsRepo;
import com.gl.CarModel.repository.ModelMakesRepo;
import com.gl.CarModel.service.CarModelsService;
import com.gl.CarModel.utils.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarModelsServiceImpl implements CarModelsService {

    private static final Logger logger = LoggerFactory.getLogger(CarModelsServiceImpl.class);

    @Autowired
    private CarModelsRepo carModelsRepo;

    @Autowired
    private ModelColorsRepo modelColorsRepo;

    @Autowired
    private ModelMakesRepo modelMakesRepo;

    @Override
    public CarModelResponseDto addCarModels(CarModelsDto carModelsDto) {
        logger.info("Entering addCarModels with carModelsDto: {}", carModelsDto);

        CarModels carModels = Mapper.mapToCarModelsEntity(carModelsDto);

        String colorIds = carModelsDto.getColor_id();
        List<Long> ids = Arrays.stream(colorIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<Long> makeIds = Arrays.stream(carModels.getMakes_id().split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        // Process model colors
        List<ModelColors> modelColors = new ArrayList<>();
        List<String> colors = new ArrayList<>();
        for (Long id : ids) {
            ModelColors modelColor = modelColorsRepo.findById(id)
                    .orElseThrow(() -> {
                        logger.error("ModelColor not found for ID: {}", id);
                        return new ResourceNotFoundException("Color", "Id", id);
                    });
            colors.add(modelColor.getColor());
            modelColors.add(modelColor);
        }

        // Process model makes
        List<ModelMakes> modelMakes = new ArrayList<>();
        if (makeIds.size() > 1) {
            logger.error("The size of the makeIds is more than one. Size: {}", makeIds.size());
            throw new SizeLimitExceededException("MakesIds", "Makes_id", makeIds.size());
        }
        for (Long id : makeIds) {
            ModelMakes modelMake = modelMakesRepo.findById(id)
                    .orElseThrow(() -> {
                        logger.error("ModelMake not found for ID: {}", id);
                        return new ResourceNotFoundException("Makes", "Id", id);
                    });
            modelMakes.add(modelMake);
        }

        // Save car model
        CarModels carModelsSaved = carModelsRepo.save(carModels);
        logger.info("CarModel saved with ID: {}", carModelsSaved.getModel_id());

        // Map to response DTO
        CarModelResponseDto carModelResponseDto = Mapper.mapToCarModelResponseDto(carModelsSaved);
        carModelResponseDto.setColors(colors);
        carModelResponseDto.setMakes_name(modelMakes.get(0).getMakes());

        logger.info("Returning CarModelResponseDto: {}", carModelResponseDto);
        return carModelResponseDto;
    }

    @Override
    public List<CarModelResponseDto> getAllCarModels() {
        logger.info("Entering getAllCarModels");

        List<CarModels> carModels = carModelsRepo.findAll();
        List<CarModelResponseDto> carModelsDtos = new ArrayList<>();

        for (CarModels carModel : carModels) {
            CarModelResponseDto carModelResponseDto = Mapper.mapToCarModelResponseDto(carModel);
            String colorIds = carModel.getColor_id();
            List<Long> ids = Arrays.stream(colorIds.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            List<Long> makeIds = Arrays.stream(carModel.getMakes_id().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            // Process colors
            List<ModelColors> modelColors = new ArrayList<>();
            List<String> colors = new ArrayList<>();
            for (Long id : ids) {
                ModelColors modelColor = modelColorsRepo.findById(id)
                        .orElseThrow(() -> {
                            logger.error("ModelColor not found for ID: {}", id);
                            return new ResourceNotFoundException("Color", "Id", id);
                        });
                colors.add(modelColor.getColor());
                modelColors.add(modelColor);
            }

            // Process makes
            List<ModelMakes> modelMakes = new ArrayList<>();
            if (makeIds.size() > 1) {
                logger.error("The size of the makeIds is more than one in size");
                throw new SizeLimitExceededException("MakesIds", "Makes_id", makeIds.size());
            }
            for (Long id : makeIds) {
                ModelMakes modelMake = modelMakesRepo.findById(id)
                        .orElseThrow(() -> {
                            logger.error("ModelMake not found for ID: {}", id);
                            return new ResourceNotFoundException("Makes", "Id", id);
                        });
                modelMakes.add(modelMake);
            }

            // Set colors and makes in the response DTO
            carModelResponseDto.setColors(colors);
            carModelResponseDto.setMakes_name(modelMakes.get(0).getMakes());
            carModelsDtos.add(carModelResponseDto);
        }

        logger.info("Returning {} car model response DTOs", carModelsDtos.size());
        return carModelsDtos;
    }

    @Override
    public CarModelResponseDto getCarModelsById(Long models_id) {
        logger.info("Entering getCarModelsById with ID: {}", models_id);

        CarModels carModels = carModelsRepo.findById(models_id)
                .orElseThrow(() -> {
                    logger.error("CarModel not found for ID: {}", models_id);
                    return new ResourceNotFoundException("CarModel", "models_id", models_id);
                });

        CarModelResponseDto carModelResponseDto = Mapper.mapToCarModelResponseDto(carModels);

        String colorIds = carModels.getColor_id();
        List<Long> ids = Arrays.stream(colorIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        List<Long> makeIds = Arrays.stream(carModels.getMakes_id().split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        // Process colors
        List<ModelColors> modelColors = new ArrayList<>();
        List<String> colors = new ArrayList<>();
        for (Long id : ids) {
            ModelColors modelColor = modelColorsRepo.findById(id)
                    .orElseThrow(() -> {
                        logger.error("ModelColor not found for ID: {}", id);
                        return new ResourceNotFoundException("Color", "Id", id);
                    });
            colors.add(modelColor.getColor());
            modelColors.add(modelColor);
        }

        // Process makes
        List<ModelMakes> modelMakes = new ArrayList<>();
        if (makeIds.size() > 1) {
            logger.error("The size of the makeIds is more than one in get by ID");
            throw new SizeLimitExceededException("MakesIds", "Makes_id", makeIds.size());
        }
        for (Long id : makeIds) {
            ModelMakes modelMake = modelMakesRepo.findById(id)
                    .orElseThrow(() -> {
                        logger.error("ModelMake not found for ID: {}", id);
                        return new ResourceNotFoundException("Makes", "Id", id);
                    });
            modelMakes.add(modelMake);
        }

        carModelResponseDto.setColors(colors);
        carModelResponseDto.setMakes_name(modelMakes.get(0).getMakes());

        logger.info("Returning CarModelResponseDto: {}", carModelResponseDto);
        return carModelResponseDto;
    }

    @Override
    public CarModelResponseDto updateCarModelsById(Long models_id, CarModelsDto carModelsDto) {
        logger.info("Entering updateCarModelsById with ID: {} and carModelsDto: {}", models_id, carModelsDto);

        CarModels carModels = carModelsRepo.findById(models_id)
                .orElseThrow(() -> {
                    logger.error("CarModel not found for ID: {}", models_id);
                    return new ResourceNotFoundException("CarModel", "models_id", models_id);
                });

        String colorIds = carModelsDto.getColor_id();
        List<Long> ids = Arrays.stream(colorIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<Long> makeIds = Arrays.stream(carModelsDto.getMakes_id().split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        // Process colors
        List<ModelColors> modelColors = new ArrayList<>();
        List<String> colors = new ArrayList<>();
        for (Long id : ids) {
            ModelColors modelColor = modelColorsRepo.findById(id)
                    .orElseThrow(() -> {
                        logger.error("ModelColor not found for ID: {}", id);
                        return new ResourceNotFoundException("Color", "Id", id);
                    });
            colors.add(modelColor.getColor());
            modelColors.add(modelColor);
        }

        // Process makes
        List<ModelMakes> modelMakes = new ArrayList<>();
        if (makeIds.size() > 1) {
            logger.error("The size of the makeIds is more than one in update");
            throw new SizeLimitExceededException("MakesIds", "Makes_id", makeIds.size());
        }
        for (Long id : makeIds) {
            ModelMakes modelMake = modelMakesRepo.findById(id)
                    .orElseThrow(() -> {
                        logger.error("ModelMake not found for ID: {}", id);
                        return new ResourceNotFoundException("Makes", "Id", id);
                    });
            modelMakes.add(modelMake);
        }

        carModels.setModel_name(carModelsDto.getModel_name());
        carModels.setMakes_id(carModelsDto.getMakes_id());
        carModels.setYear(carModelsDto.getYear());
        carModels.setColor_id(carModelsDto.getColor_id());

        CarModels carModelsSaved = carModelsRepo.save(carModels);
        logger.info("CarModel updated with ID: {}", carModelsSaved.getModel_id());

        CarModelResponseDto carModelResponseDto = Mapper.mapToCarModelResponseDto(carModelsSaved);
        carModelResponseDto.setColors(colors);
        carModelResponseDto.setMakes_name(modelMakes.get(0).getMakes());

        logger.info("Returning CarModelResponseDto: {}", carModelResponseDto);
        return carModelResponseDto;
    }

    @Override
    public void deleteCarModelsById(Long models_id) {
        logger.info("Entering deleteCarModelsById with ID: {}", models_id);

        CarModels carModels = carModelsRepo.findById(models_id)
                .orElseThrow(() -> {
                    logger.error("CarModel not found for ID: {}", models_id);
                    return new ResourceNotFoundException("CarModel", "models_id", models_id);
                });

        carModelsRepo.delete(carModels);
        logger.info("CarModel with ID: {} deleted", models_id);
    }

    @Override
    public List<CarModelResponseDto> getCarModelsByYear(Integer year) {
        logger.info("Entering getCarModelsByYear with year: {}", year);

        List<CarModels> carModels1 = carModelsRepo.findByYear(year);

        if (carModels1 == null || carModels1.isEmpty()) {
            logger.error("No car models found for year: {}", year);
//            throw new ResourceNotFoundException("Cars", "Year", year);
            return new ArrayList<>();
        }


        List<CarModelResponseDto> carModelsDtos = new ArrayList<>();
        for (CarModels carModels : carModels1) {
            CarModelResponseDto carModelResponseDto = Mapper.mapToCarModelResponseDto(carModels);

            String colorIds = carModels.getColor_id();
            List<Long> ids = Arrays.stream(colorIds.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            List<Long> makeIds = Arrays.stream(carModels.getMakes_id().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            // Process colors
            List<ModelColors> modelColors = new ArrayList<>();
            List<String> colors = new ArrayList<>();
            for (Long id : ids) {
                ModelColors modelColor = modelColorsRepo.findById(id)
                        .orElseThrow(() -> {
                            logger.error("ModelColor not found for ID: {}", id);
                            return new ResourceNotFoundException("Color", "Id", id);
                        });
                colors.add(modelColor.getColor());
                modelColors.add(modelColor);
            }

            // Process makes
            List<ModelMakes> modelMakes = new ArrayList<>();
            if (makeIds.size() > 1) {
                logger.error("The size of the makeIds is more than one in size in get by year");
                throw new SizeLimitExceededException("MakesIds", "Makes_id", makeIds.size());
            }
            for (Long id : makeIds) {
                ModelMakes modelMake = modelMakesRepo.findById(id)
                        .orElseThrow(() -> {
                            logger.error("ModelMake not found for ID: {}", id);
                            return new ResourceNotFoundException("Makes", "Id", id);
                        });
                modelMakes.add(modelMake);
            }

            carModelResponseDto.setColors(colors);
            carModelResponseDto.setMakes_name(modelMakes.get(0).getMakes());
            carModelsDtos.add(carModelResponseDto);
        }

        logger.info("Returning {} car model response DTOs", carModelsDtos.size());
        return carModelsDtos;
    }


    @Override
    public List<CarModelResponseDto> getCarModelsByYearAndMakes(Integer year, String makes_id) {
        List<CarModels> carModels2=carModelsRepo.findByYear(year);
        logger.info("Entering getCarModelsByYearAndMakes with year: {} and makes: {}", year, makes_id);

        if(carModels2==null || carModels2.size()==0){
            logger.error("Cars not found");
//            throw new ResourceNotFoundException("Cars","Year",year);
            return new ArrayList<>();
        }

        List<CarModels> carModels1=carModelsRepo.findByYearAndMakesId(year,makes_id);
        if(carModels1==null || carModels1.size()==0){
            logger.error("Cars not found");
//            throw new ResourceNotFoundException("Cars","Year And MakesId",makes_id);
            return new ArrayList<>();
        }

        List<CarModelResponseDto>carModelsDtos=new ArrayList<>();
        for(int i=0;i<carModels1.size();i++){
            CarModels carModels=carModels1.get(i);
            CarModelResponseDto carModelResponseDto=Mapper.mapToCarModelResponseDto(carModels);
            String colorIds=carModels.getColor_id();
            List<Long>ids=
                    Arrays.stream(colorIds.split(","))
                            .map(Long::parseLong)
                            .collect(Collectors.toList());
            List<Long>makeIds= Arrays.stream(carModels.getMakes_id().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            List<ModelColors>modelColors=new ArrayList<>();
            List<String>colors=new ArrayList<>();
            for(int j=0;j<ids.size();j++){
                Long i1=ids.get(j);
                ModelColors modelColors1=modelColorsRepo.findById(ids.get(j)).orElseThrow(()->new ResourceNotFoundException("Color","Id",i1));
                colors.add(modelColors1.getColor());
                modelColors.add(modelColors1);

            }


            List<ModelMakes>modelMakes=new ArrayList<>();
            if(makeIds.size()>1){
                logger.error("The size of the makeIds are more than one in size in year and Make");
                throw new SizeLimitExceededException("MakesIds","Makes_id", makeIds.size());
            }
            for(int j=0;j<makeIds.size();j++){
                Long id1= makeIds.get(j);
                ModelMakes modelMakes1=modelMakesRepo.findById(makeIds.get(j)).orElseThrow(()->new ResourceNotFoundException("Makes","Id",makeIds.get(0)));
                modelMakes.add(modelMakes1);
            }
            carModelResponseDto.setColors(colors);
            carModelResponseDto.setMakes_name(modelMakes.get(0).getMakes());
            carModelsDtos.add(carModelResponseDto);
        }
        return carModelsDtos;

    }

    @Override
    public List<ModelColorsDto> getModelColorsByYearAndMakesAnsModel(Integer year, String makes_id, String model_name) {
        logger.info("Entering getCarModelsByYearAndMakes with year: {} and makes: {} and model:{}", year, makes_id,model_name);
        List<CarModels> carModels1=carModelsRepo.findByYear(year);

        if(carModels1==null || carModels1.size()==0){
            return new ArrayList<>();
//            throw new ResourceNotFoundException("Cars","Year",year);
        }
        List<CarModels> carModels2=carModelsRepo.findByYearAndMakesId(year,makes_id);
        if(carModels2==null || carModels2.size()==0){
            return new ArrayList<>();
//            throw new ResourceNotFoundException("Cars","makesId",makes_id);
        }

        CarModels carModels=carModelsRepo.findByYearAndMakesIdAndModelName(year,makes_id,model_name);
        if(carModels==null){
            logger.error("We have not found any Car with the given Model makes id and model name");
//            throw new ResourceNotFoundException("Cars","Model Makes Id and Model Name",model_name);
            return new ArrayList<>();
        }
        String colorIds=carModels.getColor_id();
        List<Long>ids=
                Arrays.stream(colorIds.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());

        List<ModelColors>modelColors=modelColorsRepo.findAllById(ids);
        List<ModelColorsDto>modelColorsDtos=new ArrayList<>();
        for(int i=0;i<modelColors.size();i++){
            modelColorsDtos.add(Mapper.mapToModelColorsDto(modelColors.get(i)));
        }

        return modelColorsDtos;
    }

}
