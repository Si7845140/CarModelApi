package com.gl.CarModel.controller;

import com.gl.CarModel.dto.ModelColorsDto;
import com.gl.CarModel.dto.ModelColorsResponseDto;
import com.gl.CarModel.exception.ResourceNotFoundException;
import com.gl.CarModel.service.Impl.ModelColorsServiceImpl;
import com.gl.CarModel.utils.StringFormatUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

public class ModelColorsControllerTest {

    @Mock
    private ModelColorsServiceImpl modelColorsService;

    @InjectMocks
    private ModelColorsController modelColorsController;

    @Autowired
    private MockMvc mockMvc;

    private ModelColorsDto modelColorsDto;
    private ModelColorsResponseDto modelColorsResponseDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(modelColorsController).build();

        modelColorsDto = new ModelColorsDto();
        modelColorsDto.setColor("Red");

        modelColorsResponseDto = new ModelColorsResponseDto();
        modelColorsResponseDto.setColor_id(1L);
        modelColorsResponseDto.setColor("RED");
    }

    @Test
    public void testAddModelColor() throws Exception {
        when(modelColorsService.addModelColors(any(ModelColorsDto.class))).thenReturn(modelColorsResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/colors")
                        .contentType("application/json")
                        .content("{\"color\": \"Red\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.color").value("RED"))
                .andExpect(jsonPath("$.color_id").value(1));
    }

    @Test
    public void testGetAllModelColors() throws Exception {
        when(modelColorsService.getAllModelColors()).thenReturn(Arrays.asList(modelColorsResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/colors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value("RED"))
                .andExpect(jsonPath("$[0].color_id").value(1));
    }

    @Test
    public void testGetModelColorsById() throws Exception {
        when(modelColorsService.getModelColorsById(1L)).thenReturn(modelColorsResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/colors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("RED"))
                .andExpect(jsonPath("$.color_id").value(1));
    }


    @Test
    public void testDeleteModelColorsById() throws Exception {
        doNothing().when(modelColorsService).deleteModelColorsById(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/colors/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("You have successfully deleted Color with: 1"));
    }


}
