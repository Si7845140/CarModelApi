package com.gl.CarModel.controller;

import com.gl.CarModel.dto.ModelMakesDto;
import com.gl.CarModel.dto.ModelMakesResponseDto;
import com.gl.CarModel.exception.ResourceNotFoundException;
import com.gl.CarModel.service.Impl.ModelMakesServiceImpl;
import com.gl.CarModel.utils.StringFormatUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ModelMakesControllerTest {

    @Mock
    private ModelMakesServiceImpl modelMakesService;

    @InjectMocks
    private ModelMakesController modelMakesController;

    @Autowired
    private MockMvc mockMvc;

    private ModelMakesDto modelMakesDto;
    private ModelMakesResponseDto modelMakesResponseDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(modelMakesController).build();

        modelMakesDto = new ModelMakesDto();
        modelMakesDto.setMakes("Toyota");

        modelMakesResponseDto = new ModelMakesResponseDto();
        modelMakesResponseDto.setMakes_id(1L);
        modelMakesResponseDto.setMakes("TOYOTA");
    }

    @Test
    public void testAddModelMakes() throws Exception {
        when(modelMakesService.addModelMakes(any(ModelMakesDto.class))).thenReturn(modelMakesResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/makes")
                        .contentType("application/json")
                        .content("{\"makes\": \"Toyota\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.makes").value("TOYOTA"))
                .andExpect(jsonPath("$.makes_id").value(1));
    }

    @Test
    public void testGetAllModelMakes() throws Exception {
        when(modelMakesService.getAllModelMakes()).thenReturn(Arrays.asList(modelMakesResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/makes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].makes").value("TOYOTA"))
                .andExpect(jsonPath("$[0].makes_id").value(1));
    }

    @Test
    public void testGetModelMakesById() throws Exception {
        when(modelMakesService.getModelMakesById(1L)).thenReturn(modelMakesResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/makes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.makes").value("TOYOTA"))
                .andExpect(jsonPath("$.makes_id").value(1));
    }


    @Test
    public void testDeleteModelMakesById() throws Exception {
        doNothing().when(modelMakesService).deleteModelMakesById(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/makes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("You have successfully deleted Makes with: 1"));
    }


}
