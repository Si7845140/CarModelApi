package com.gl.CarModel.controller;
import com.gl.CarModel.controller.CarModelsController;
import com.gl.CarModel.dto.CarModelResponseDto;
import com.gl.CarModel.dto.CarModelsDto;
import com.gl.CarModel.dto.ModelColorsDto;
import com.gl.CarModel.service.Impl.CarModelsServiceImpl;
import com.gl.CarModel.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CarModelsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarModelsServiceImpl carModelsService;

    @InjectMocks
    private CarModelsController carModelsController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carModelsController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddCarModels() throws Exception {

        CarModelsDto carModelsDto = new CarModelsDto();
        carModelsDto.setMakes_id("1");
        carModelsDto.setModel_name("Model A");
        carModelsDto.setColor_id("1,2");
        carModelsDto.setYear(2023);

        CarModelResponseDto carModelResponseDto = new CarModelResponseDto();
        carModelResponseDto.setModel_id(1L);
        carModelResponseDto.setMakes_name("Make A");
        carModelResponseDto.setModel_name("Model A");
        carModelResponseDto.setColors(Arrays.asList("Red", "Blue"));
        carModelResponseDto.setYear(2023);


        when(carModelsService.addCarModels(any(CarModelsDto.class))).thenReturn(carModelResponseDto);


        mockMvc.perform(post("/v1/api/car/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carModelsDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.model_id").value(1L))
                .andExpect(jsonPath("$.makes_name").value("Make A"))
                .andExpect(jsonPath("$.model_name").value("Model A"))
                .andExpect(jsonPath("$.colors[0]").value("Red"))
                .andExpect(jsonPath("$.colors[1]").value("Blue"))
                .andExpect(jsonPath("$.year").value(2023));


        verify(carModelsService, times(1)).addCarModels(any(CarModelsDto.class));
    }

    @Test
    public void testGetAllCarModels() throws Exception {

        CarModelResponseDto carModelResponseDto1 = new CarModelResponseDto();
        carModelResponseDto1.setModel_id(1L);
        carModelResponseDto1.setMakes_name("Make A");
        carModelResponseDto1.setModel_name("Model A");
        carModelResponseDto1.setColors(Arrays.asList("Red", "Blue"));
        carModelResponseDto1.setYear(2023);

        CarModelResponseDto carModelResponseDto2 = new CarModelResponseDto();
        carModelResponseDto2.setModel_id(2L);
        carModelResponseDto2.setMakes_name("Make B");
        carModelResponseDto2.setModel_name("Model B");
        carModelResponseDto2.setColors(Arrays.asList("Green", "Yellow"));
        carModelResponseDto2.setYear(2024);

        List<CarModelResponseDto> carModelResponseDtos = Arrays.asList(carModelResponseDto1, carModelResponseDto2);


        when(carModelsService.getAllCarModels()).thenReturn(carModelResponseDtos);


        mockMvc.perform(get("/v1/api/car/models"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))  // Verify two car models are returned
                .andExpect(jsonPath("$[0].model_id").value(1L))
                .andExpect(jsonPath("$[0].makes_name").value("Make A"))
                .andExpect(jsonPath("$[0].model_name").value("Model A"))
                .andExpect(jsonPath("$[1].model_id").value(2L))
                .andExpect(jsonPath("$[1].makes_name").value("Make B"))
                .andExpect(jsonPath("$[1].model_name").value("Model B"));


        verify(carModelsService, times(1)).getAllCarModels();
    }

    @Test
    public void testGetCarModelsById() throws Exception {

        CarModelResponseDto carModelResponseDto = new CarModelResponseDto();
        carModelResponseDto.setModel_id(1L);
        carModelResponseDto.setMakes_name("Make A");
        carModelResponseDto.setModel_name("Model A");
        carModelResponseDto.setColors(Arrays.asList("Red", "Blue"));
        carModelResponseDto.setYear(2023);


        when(carModelsService.getCarModelsById(1L)).thenReturn(carModelResponseDto);

        mockMvc.perform(get("/v1/api/car/models/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model_id").value(1L))
                .andExpect(jsonPath("$.makes_name").value("Make A"))
                .andExpect(jsonPath("$.model_name").value("Model A"))
                .andExpect(jsonPath("$.colors[0]").value("Red"))
                .andExpect(jsonPath("$.colors[1]").value("Blue"))
                .andExpect(jsonPath("$.year").value(2023));


        verify(carModelsService, times(1)).getCarModelsById(1L);
    }

    @Test
    public void testDeleteCarModelsById() throws Exception {
        Long modelId = 1L;

        doNothing().when(carModelsService).deleteCarModelsById(modelId);

        mockMvc.perform(delete("/v1/api/car/models/{id}", modelId))
                .andExpect(status().isOk())
                .andExpect(content().string("You have successfully deleted CarModel with: 1"));

        verify(carModelsService, times(1)).deleteCarModelsById(modelId);
    }

    @Test
    public void testUpdateCarModelsById() throws Exception {
        Long modelId = 1L;

        CarModelsDto carModelsDto = new CarModelsDto();
        carModelsDto.setMakes_id("1");
        carModelsDto.setModel_name("Updated Model A");
        carModelsDto.setColor_id("1,2");
        carModelsDto.setYear(2023);

        CarModelResponseDto carModelResponseDto = new CarModelResponseDto();
        carModelResponseDto.setModel_id(1L);
        carModelResponseDto.setMakes_name("Make A");
        carModelResponseDto.setModel_name("Updated Model A");
        carModelResponseDto.setColors(Arrays.asList("Red", "Blue"));
        carModelResponseDto.setYear(2023);

        when(carModelsService.updateCarModelsById(eq(modelId), any(CarModelsDto.class))).thenReturn(carModelResponseDto);

        mockMvc.perform(put("/v1/api/cars/models/{id}", modelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carModelsDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.model_id").value(1L))
                .andExpect(jsonPath("$.makes_name").value("Make A"))
                .andExpect(jsonPath("$.model_name").value("Updated Model A"))
                .andExpect(jsonPath("$.colors[0]").value("Red"))
                .andExpect(jsonPath("$.colors[1]").value("Blue"))
                .andExpect(jsonPath("$.year").value(2023));

        verify(carModelsService, times(1)).updateCarModelsById(eq(modelId), any(CarModelsDto.class));
    }

    @Test
    public void testGetAllCarModelsByYear() throws Exception {

        CarModelResponseDto carModelResponseDto1 = new CarModelResponseDto();
        carModelResponseDto1.setModel_id(1L);
        carModelResponseDto1.setMakes_name("Make A");
        carModelResponseDto1.setModel_name("Model A");
        carModelResponseDto1.setColors(Arrays.asList("Red", "Blue"));
        carModelResponseDto1.setYear(2023);

        CarModelResponseDto carModelResponseDto2 = new CarModelResponseDto();
        carModelResponseDto2.setModel_id(2L);
        carModelResponseDto2.setMakes_name("Make B");
        carModelResponseDto2.setModel_name("Model B");
        carModelResponseDto2.setColors(Arrays.asList("Green", "Yellow"));
        carModelResponseDto2.setYear(2023);

        List<CarModelResponseDto> carModelResponseDtos = Arrays.asList(carModelResponseDto1, carModelResponseDto2);

        when(carModelsService.getCarModelsByYear(2023)).thenReturn(carModelResponseDtos);

        mockMvc.perform(get("/v1/api/cars/models/{year}", 2023))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))  // Verify two car models are returned
                .andExpect(jsonPath("$[0].model_id").value(1L))
                .andExpect(jsonPath("$[0].makes_name").value("Make A"))
                .andExpect(jsonPath("$[0].model_name").value("Model A"))
                .andExpect(jsonPath("$[1].model_id").value(2L))
                .andExpect(jsonPath("$[1].makes_name").value("Make B"))
                .andExpect(jsonPath("$[1].model_name").value("Model B"));

        verify(carModelsService, times(1)).getCarModelsByYear(2023);
    }

    @Test
    public void testGetAllCarModelsByYearAndMakes() throws Exception {
        CarModelResponseDto carModelResponseDto1 = new CarModelResponseDto();
        carModelResponseDto1.setModel_id(1L);
        carModelResponseDto1.setMakes_name("Make A");
        carModelResponseDto1.setModel_name("Model A");
        carModelResponseDto1.setColors(Arrays.asList("Red", "Blue"));
        carModelResponseDto1.setYear(2023);

        CarModelResponseDto carModelResponseDto2 = new CarModelResponseDto();
        carModelResponseDto2.setModel_id(2L);
        carModelResponseDto2.setMakes_name("Make A");
        carModelResponseDto2.setModel_name("Model B");
        carModelResponseDto2.setColors(Arrays.asList("Green", "Yellow"));
        carModelResponseDto2.setYear(2023);

        List<CarModelResponseDto> carModelResponseDtos = Arrays.asList(carModelResponseDto1, carModelResponseDto2);

        when(carModelsService.getCarModelsByYearAndMakes(2023, "Make A")).thenReturn(carModelResponseDtos);

        mockMvc.perform(get("/v1/api/cars/models/{year}/{makes_id}", 2023, "Make A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))  // Verify two car models are returned
                .andExpect(jsonPath("$[0].makes_name").value("Make A"))
                .andExpect(jsonPath("$[1].makes_name").value("Make A"));

        verify(carModelsService, times(1)).getCarModelsByYearAndMakes(2023, "Make A");
    }


    @Test
    public void testGetAllCarModelsByYearAndMakesAndName() throws Exception {
        ModelColorsDto modelColorsDto1 = new ModelColorsDto();
        modelColorsDto1.setColor("RED");

        ModelColorsDto modelColorsDto2 = new ModelColorsDto();
        modelColorsDto2.setColor("BLUE");

        List<ModelColorsDto> modelColorsDtos = Arrays.asList(modelColorsDto1, modelColorsDto2);

        when(carModelsService.getModelColorsByYearAndMakesAnsModel(2023, "Make A", "Model A")).thenReturn(modelColorsDtos);

        mockMvc.perform(get("/v1/api/cars/colors/{year}/{makes_id}/{name}", 2023, "Make A", "Model A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))  // Verify two colors are returned
                .andExpect(jsonPath("$[0].color").value("RED"))
                .andExpect(jsonPath("$[1].color").value("BLUE"));
        verify(carModelsService, times(1)).getModelColorsByYearAndMakesAnsModel(2023, "Make A", "Model A");
    }








}
