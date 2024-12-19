package com.gl.CarModel.service.Impl;

import com.gl.CarModel.dto.CarModelResponseDto;
import com.gl.CarModel.dto.CarModelsDto;
import com.gl.CarModel.dto.ModelColorsDto;
import com.gl.CarModel.entity.CarModels;
import com.gl.CarModel.entity.ModelColors;
import com.gl.CarModel.entity.ModelMakes;
import com.gl.CarModel.exception.ResourceNotFoundException;
import com.gl.CarModel.repository.CarModelsRepo;
import com.gl.CarModel.repository.ModelColorsRepo;
import com.gl.CarModel.repository.ModelMakesRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarModelsServiceImplTest {

    @Mock
    private CarModelsRepo carModelsRepo;

    @Mock
    private ModelColorsRepo modelColorsRepo;

    @Mock
    private ModelMakesRepo modelMakesRepo;

    @InjectMocks
    private CarModelsServiceImpl carModelsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCarModels_ValidInputs() {
        CarModelsDto carModelsDto = new CarModelsDto();
        carModelsDto.setMakes_id("1");
        carModelsDto.setModel_name("Camry");
        carModelsDto.setColor_id("1,2");
        carModelsDto.setYear(2024);

        ModelColors modelColor1 = new ModelColors();
        modelColor1.setColor("White");

        ModelColors modelColor2 = new ModelColors();
        modelColor2.setColor("Black");

        ModelMakes modelMakes = new ModelMakes();
        modelMakes.setMakes("Toyota");

        when(modelColorsRepo.findById(1L)).thenReturn(Optional.of(modelColor1));
        when(modelColorsRepo.findById(2L)).thenReturn(Optional.of(modelColor2));
        when(modelMakesRepo.findById(1L)).thenReturn(Optional.of(modelMakes));
        when(carModelsRepo.save(any(CarModels.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        CarModelResponseDto result = carModelsService.addCarModels(carModelsDto);

        assertNotNull(result);
        assertEquals("Toyota", result.getMakes_name());
        assertEquals("Camry", result.getModel_name());
        assertEquals(Arrays.asList("White","Black"), result.getColors());
        assertEquals(2024, result.getYear());
        verify(carModelsRepo, times(1)).save(any(CarModels.class));
        verify(modelColorsRepo, times(2)).findById(any(Long.class));
        verify(modelMakesRepo, times(1)).findById(any(Long.class));
    }

    @Test
    void addCarModels_InvalidColorId_ThrowsException() {
        CarModelsDto carModelsDto = new CarModelsDto();
        carModelsDto.setMakes_id("1");
        carModelsDto.setModel_name("Camry");
        carModelsDto.setColor_id("999");
        carModelsDto.setYear(2024);

        ModelMakes modelMakes = new ModelMakes();
        modelMakes.setMakes("Toyota");

        when(modelColorsRepo.findById(999L)).thenReturn(Optional.empty());
        when(modelMakesRepo.findById(1L)).thenReturn(Optional.of(modelMakes));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            carModelsService.addCarModels(carModelsDto);
        });

        assertEquals("Color not found with Id : '999'", exception.getMessage());
    }

    @Test
    void addCarModels_InvalidMakeId_ThrowsException() {
        CarModelsDto carModelsDto = new CarModelsDto();
        carModelsDto.setMakes_id("999");
        carModelsDto.setModel_name("Camry");
        carModelsDto.setColor_id("1");
        carModelsDto.setYear(2024);

        ModelColors modelColor1 = new ModelColors();
        modelColor1.setColor("White");

        when(modelColorsRepo.findById(1L)).thenReturn(Optional.of(modelColor1));
        when(modelMakesRepo.findById(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            carModelsService.addCarModels(carModelsDto);
        });

        assertEquals("Makes not found with Id : '999'", exception.getMessage());
    }

    @Test
    void getAllCarModels_NoData() {
        when(carModelsRepo.findAll()).thenReturn(Arrays.asList());

        List<CarModelResponseDto> result = carModelsService.getAllCarModels();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(carModelsRepo, times(1)).findAll();
        verify(modelColorsRepo, times(0)).findById(any());
        verify(modelMakesRepo, times(0)).findById(any());
    }

    @Test
    void getAllCarModels() {
        CarModels carModel1 = new CarModels();
        carModel1.setModel_id(1L);
        carModel1.setModel_name("Camry");
        carModel1.setMakes_id("1");
        carModel1.setColor_id("1");
        carModel1.setYear(2024);

        CarModels carModel2 = new CarModels();
        carModel2.setModel_id(2L);
        carModel2.setModel_name("Civic");
        carModel2.setMakes_id("2");
        carModel2.setColor_id("2,3");
        carModel2.setYear(2024);

        ModelColors color1 = new ModelColors();
        color1.setColor("White");

        ModelColors color2 = new ModelColors();
        color2.setColor("Black");

        ModelColors color3 = new ModelColors();
        color3.setColor("Red");

        ModelMakes make1 = new ModelMakes();
        make1.setMakes("Toyota");

        ModelMakes make2 = new ModelMakes();
        make2.setMakes("Honda");

        when(carModelsRepo.findAll()).thenReturn(Arrays.asList(carModel1, carModel2));
        when(modelColorsRepo.findById(1L)).thenReturn(java.util.Optional.of(color1));
        when(modelColorsRepo.findById(2L)).thenReturn(java.util.Optional.of(color2));
        when(modelColorsRepo.findById(3L)).thenReturn(java.util.Optional.of(color3));
        when(modelMakesRepo.findById(1L)).thenReturn(java.util.Optional.of(make1));
        when(modelMakesRepo.findById(2L)).thenReturn(java.util.Optional.of(make2));

        List<CarModelResponseDto> result = carModelsService.getAllCarModels();

        assertNotNull(result);
        assertEquals(2, result.size());

        CarModelResponseDto dto1 = result.get(0);
        assertEquals("Toyota", dto1.getMakes_name());
        assertEquals("Camry", dto1.getModel_name());
        assertEquals(Arrays.asList("White"), dto1.getColors());

        CarModelResponseDto dto2 = result.get(1);
        assertEquals("Honda", dto2.getMakes_name());
        assertEquals("Civic", dto2.getModel_name());
        assertEquals(Arrays.asList("Black","Red"), dto2.getColors());

        verify(carModelsRepo, times(1)).findAll();
        verify(modelColorsRepo, times(1)).findById(1L);
        verify(modelColorsRepo, times(1)).findById(2L);
        verify(modelColorsRepo, times(1)).findById(3L);
        verify(modelMakesRepo, times(1)).findById(1L);
        verify(modelMakesRepo, times(1)).findById(2L);
    }

    @Test
    void getAllCarModels_InvalidColorId_ThrowsException() {
        CarModels carModel1 = new CarModels();
        carModel1.setModel_id(1L);
        carModel1.setModel_name("Camry");
        carModel1.setMakes_id("1");
        carModel1.setColor_id("1,999");
        carModel1.setYear(2024);

        ModelMakes make1 = new ModelMakes();
        make1.setMakes("Toyota");

        when(carModelsRepo.findAll()).thenReturn(Arrays.asList(carModel1));
        when(modelColorsRepo.findById(1L)).thenReturn(java.util.Optional.of(new ModelColors()));
        when(modelColorsRepo.findById(999L)).thenReturn(java.util.Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            carModelsService.getAllCarModels();
        });

        assertEquals("Color not found with Id : '999'", exception.getMessage());
        verify(carModelsRepo, times(1)).findAll();
        verify(modelColorsRepo, times(1)).findById(1L);
        verify(modelColorsRepo, times(1)).findById(999L);
    }

    @Test
    void getAllCarModels_InvalidMakeId_ThrowsException() {
        CarModels carModel1 = new CarModels();
        carModel1.setModel_id(1L);
        carModel1.setModel_name("Camry");
        carModel1.setMakes_id("1");
        carModel1.setColor_id("1,2");
        carModel1.setYear(2024);

        ModelColors color1 = new ModelColors();
        color1.setColor("White");
        ModelColors color2 = new ModelColors();
        color2.setColor("Black");

        when(carModelsRepo.findAll()).thenReturn(Arrays.asList(carModel1));
        when(modelColorsRepo.findById(1L)).thenReturn(java.util.Optional.of(color1));
        when(modelColorsRepo.findById(2L)).thenReturn(java.util.Optional.of(color2));
        when(modelMakesRepo.findById(1L)).thenReturn(java.util.Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            carModelsService.getAllCarModels();
        });

        assertEquals("Makes not found with Id : '1'", exception.getMessage());
        verify(carModelsRepo, times(1)).findAll();
        verify(modelColorsRepo, times(1)).findById(1L);
        verify(modelColorsRepo, times(1)).findById(2L);
        verify(modelMakesRepo, times(1)).findById(1L);
    }

    @Test
    void getCarModelsById() {
        CarModels carModel = new CarModels();
        carModel.setModel_id(1L);
        carModel.setModel_name("Camry");
        carModel.setMakes_id("1");
        carModel.setColor_id("1");
        carModel.setYear(2024);

        ModelColors color1 = new ModelColors();
        color1.setColor("White");

        ModelMakes make1 = new ModelMakes();
        make1.setMakes("Toyota");

        when(carModelsRepo.findById(1L)).thenReturn(Optional.of(carModel));
        when(modelColorsRepo.findById(1L)).thenReturn(Optional.of(color1));
        when(modelMakesRepo.findById(1L)).thenReturn(Optional.of(make1));

        CarModelResponseDto result = carModelsService.getCarModelsById(1L);

        assertNotNull(result);
        assertEquals("Toyota", result.getMakes_name());
        assertEquals("Camry", result.getModel_name());
        assertEquals("White", result.getColors().get(0));

        verify(carModelsRepo, times(1)).findById(1L);
        verify(modelColorsRepo, times(1)).findById(1L);
        verify(modelMakesRepo, times(1)).findById(1L);
    }

    @Test
    void getCarModelsById_NotFound() {
        when(carModelsRepo.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            carModelsService.getCarModelsById(1L);
        });

        assertEquals("CarModel not found with models_id : '1'", exception.getMessage());
    }

    @Test
    void getCarModelsById_ColorNotFound() {
        CarModels carModel = new CarModels();
        carModel.setModel_id(1L);
        carModel.setModel_name("Camry");
        carModel.setMakes_id("1");
        carModel.setColor_id("1,999");
        carModel.setYear(2024);

        ModelMakes make1 = new ModelMakes();
        make1.setMakes("Toyota");

        when(carModelsRepo.findById(1L)).thenReturn(Optional.of(carModel));
        when(modelColorsRepo.findById(1L)).thenReturn(Optional.of(new ModelColors()));
        when(modelColorsRepo.findById(999L)).thenReturn(Optional.empty());
        when(modelMakesRepo.findById(1L)).thenReturn(Optional.of(make1));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            carModelsService.getCarModelsById(1L);
        });

        assertEquals("Color not found with Id : '999'", exception.getMessage());
    }

    @Test
    void getCarModelsById_MakeNotFound() {
        CarModels carModel = new CarModels();
        carModel.setModel_id(1L);
        carModel.setModel_name("Camry");
        carModel.setMakes_id("1");
        carModel.setColor_id("1,2");
        carModel.setYear(2024);

        ModelColors color1 = new ModelColors();
        color1.setColor("White");
        ModelColors color2 = new ModelColors();
        color2.setColor("Black");

        when(carModelsRepo.findById(1L)).thenReturn(Optional.of(carModel));
        when(modelColorsRepo.findById(1L)).thenReturn(Optional.of(color1));
        when(modelColorsRepo.findById(2L)).thenReturn(Optional.of(color2));
        when(modelMakesRepo.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            carModelsService.getCarModelsById(1L);
        });

        assertEquals("Makes not found with Id : '1'", exception.getMessage());
    }

    @Test
    void deleteCarModelsById_Success() {
        CarModels carModel = new CarModels();
        carModel.setModel_id(1L);
        carModel.setModel_name("Camry");

        when(carModelsRepo.findById(1L)).thenReturn(Optional.of(carModel));

        carModelsService.deleteCarModelsById(1L);

        verify(carModelsRepo, times(1)).delete(carModel);
    }

    @Test
    void deleteCarModelsById_NotFound() {
        when(carModelsRepo.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            carModelsService.deleteCarModelsById(1L);
        });

        assertEquals("CarModel not found with models_id : '1'", exception.getMessage());
    }

    @Test
    void updateCarModelsById_Success() {
        CarModelsDto carModelsDto = new CarModelsDto();
        carModelsDto.setModel_name("Camry");
        carModelsDto.setMakes_id("1");
        carModelsDto.setColor_id("1,2");
        carModelsDto.setYear(2025);

        CarModels carModel = new CarModels();
        carModel.setModel_id(1L);
        carModel.setModel_name("Corolla");
        carModel.setMakes_id("1");
        carModel.setColor_id("1");
        carModel.setYear(2024);

        ModelMakes modelMakes = new ModelMakes();
        modelMakes.setMakes("Toyota");

        ModelColors modelColor1 = new ModelColors();
        modelColor1.setColor("White");

        ModelColors modelColor2 = new ModelColors();
        modelColor2.setColor("Black");

        when(carModelsRepo.findById(1L)).thenReturn(Optional.of(carModel));
        when(modelColorsRepo.findById(1L)).thenReturn(Optional.of(modelColor1));
        when(modelColorsRepo.findById(2L)).thenReturn(Optional.of(modelColor2));
        when(modelMakesRepo.findById(1L)).thenReturn(Optional.of(modelMakes));
        when(carModelsRepo.save(any(CarModels.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        CarModelResponseDto result = carModelsService.updateCarModelsById(1L, carModelsDto);

        assertNotNull(result);
        assertEquals("Toyota", result.getMakes_name());
        assertEquals("Camry", result.getModel_name());
        assertEquals(Arrays.asList("White", "Black"), result.getColors());
        assertEquals(2025, result.getYear());

        verify(carModelsRepo, times(1)).save(any(CarModels.class));
    }

    @Test
    void updateCarModelsById_CarModelNotFound() {
        CarModelsDto carModelsDto = new CarModelsDto();
        carModelsDto.setModel_name("Camry");
        carModelsDto.setMakes_id("1");
        carModelsDto.setColor_id("1");
        carModelsDto.setYear(2025);

        when(carModelsRepo.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            carModelsService.updateCarModelsById(1L, carModelsDto);
        });

        assertEquals("CarModel not found with models_id : '1'", exception.getMessage());
    }

    @Test
    void updateCarModelsById_ColorNotFound() {
        CarModelsDto carModelsDto = new CarModelsDto();
        carModelsDto.setModel_name("Camry");
        carModelsDto.setMakes_id("1");
        carModelsDto.setColor_id("1,999");
        carModelsDto.setYear(2025);

        CarModels carModel = new CarModels();
        carModel.setModel_id(1L);
        carModel.setModel_name("Corolla");
        carModel.setMakes_id("1");
        carModel.setColor_id("1");
        carModel.setYear(2024);

        ModelMakes modelMakes = new ModelMakes();
        modelMakes.setMakes("Toyota");

        when(carModelsRepo.findById(1L)).thenReturn(Optional.of(carModel));
        when(modelColorsRepo.findById(999L)).thenReturn(Optional.empty());
        when(modelMakesRepo.findById(1L)).thenReturn(Optional.of(modelMakes));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            carModelsService.updateCarModelsById(1L, carModelsDto);
        });

        assertEquals("Color not found with Id : '1'", exception.getMessage());
    }

    @Test
    void updateCarModelsById_MakesNotFound() {
        CarModelsDto carModelsDto = new CarModelsDto();
        carModelsDto.setModel_name("Camry");
        carModelsDto.setMakes_id("999");
        carModelsDto.setColor_id("1");
        carModelsDto.setYear(2025);

        CarModels carModel = new CarModels();
        carModel.setModel_id(1L);
        carModel.setModel_name("Corolla");
        carModel.setMakes_id("1");
        carModel.setColor_id("1");
        carModel.setYear(2024);

        ModelColors modelColor1 = new ModelColors();
        modelColor1.setColor("White");

        when(carModelsRepo.findById(1L)).thenReturn(Optional.of(carModel));
        when(modelColorsRepo.findById(1L)).thenReturn(Optional.of(modelColor1));
        when(modelMakesRepo.findById(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            carModelsService.updateCarModelsById(1L, carModelsDto);
        });

        assertEquals("Makes not found with Id : '999'", exception.getMessage());
    }

    @Test
    void getCarModelsByYear_Success() {

        List<CarModels> carModels = new ArrayList<>();
        CarModels carModel = new CarModels();
        carModel.setModel_id(1L);
        carModel.setModel_name("Camry");
        carModel.setYear(2024);
        carModel.setMakes_id("1");
        carModel.setColor_id("1");

        carModels.add(carModel);

        // Mocking the repository calls
        when(carModelsRepo.findByYear(2024)).thenReturn(carModels);
        when(modelColorsRepo.findById(1L)).thenReturn(Optional.of(new ModelColors()));
        when(modelMakesRepo.findById(1L)).thenReturn(Optional.of(new ModelMakes()));


        List<CarModelResponseDto> result = carModelsService.getCarModelsByYear(2024);


        assertNotNull(result);
        assertFalse(result.isEmpty());


        assertTrue(result.get(0).getColors() != null);
        assertEquals(1, result.get(0).getColors().size()); // Assuming the colorIds split into a list

    }



    @Test
    void getCarModelsByYear_NoModelsFound() {
        when(carModelsRepo.findByYear(2024)).thenReturn(new ArrayList<>());

        List<CarModelResponseDto> result = carModelsService.getCarModelsByYear(2024);

        assertTrue(result.isEmpty());
    }

    @Test
    void getCarModelsByYearAndMakes_Success() {
        List<CarModels> carModels = new ArrayList<>();
        CarModels carModel = new CarModels();
        carModel.setModel_id(1L);
        carModel.setModel_name("Camry");
        carModel.setYear(2024);
        carModel.setMakes_id("1");
        carModel.setColor_id("1");
        carModels.add(carModel);

        when(carModelsRepo.findByYear(2024)).thenReturn(carModels);
        when(carModelsRepo.findByYearAndMakesId(2024, "1")).thenReturn(carModels);
        when(modelColorsRepo.findById(1L)).thenReturn(Optional.of(new ModelColors()));
        when(modelMakesRepo.findById(1L)).thenReturn(Optional.of(new ModelMakes()));

        List<CarModelResponseDto> result = carModelsService.getCarModelsByYearAndMakes(2024, "1");

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void getCarModelsByYearAndMakes_NoModelsFound() {
        when(carModelsRepo.findByYearAndMakesId(2024, "1")).thenReturn(new ArrayList<>());

        List<CarModelResponseDto> result = carModelsService.getCarModelsByYearAndMakes(2024, "1");

        assertTrue(result.isEmpty());
    }

    @Test
    void getModelColorsByYearAndMakesAnsModel_Success() {
        CarModels carModel = new CarModels();
        carModel.setModel_id(1L);
        carModel.setModel_name("Camry");
        carModel.setColor_id("1,2");
        carModel.setMakes_id("1");

        when(carModelsRepo.findByYearAndMakesIdAndModelName(2024, "1", "Camry")).thenReturn(carModel);
        when(modelColorsRepo.findById(1L)).thenReturn(Optional.of(new ModelColors()));
        when(modelColorsRepo.findById(2L)).thenReturn(Optional.of(new ModelColors()));
        when(modelMakesRepo.findById(1L)).thenReturn(Optional.of(new ModelMakes()));

        List<ModelColorsDto> result = carModelsService.getModelColorsByYearAndMakesAnsModel(2024, "1", "Camry");

        assertNotNull(result);
//        assertEquals(2, result.size());
    }

    @Test
    void getModelColorsByYearAndMakesAnsModel_NoModelFound() {
        when(carModelsRepo.findByYearAndMakesIdAndModelName(2024, "1", "Camry")).thenReturn(null);

        List<ModelColorsDto> result = carModelsService.getModelColorsByYearAndMakesAnsModel(2024, "1", "Camry");

        assertTrue(result.isEmpty());
    }

    @Test
    void getModelColorsByYearAndMakesAnsModel_NoColorsFound() {
        CarModels carModel = new CarModels();
        carModel.setModel_id(1L);
        carModel.setModel_name("Camry");
        carModel.setColor_id("999");

        when(carModelsRepo.findByYearAndMakesIdAndModelName(2024, "1", "Camry")).thenReturn(carModel);
        when(modelColorsRepo.findById(999L)).thenReturn(Optional.empty());

        List<ModelColorsDto> result = carModelsService.getModelColorsByYearAndMakesAnsModel(2024, "1", "Camry");

        assertTrue(result.isEmpty());
    }

    @Test
    void getModelColorsByYearAndMakesAnsModel_EmptyResult() {
        when(carModelsRepo.findByYearAndMakesIdAndModelName(2024, "1", "Camry")).thenReturn(null);
        List<ModelColorsDto> result = carModelsService.getModelColorsByYearAndMakesAnsModel(2024, "1", "Camry");

        assertTrue(result.isEmpty());
    }
}
