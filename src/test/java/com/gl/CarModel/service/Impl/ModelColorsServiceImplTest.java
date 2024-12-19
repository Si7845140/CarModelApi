package com.gl.CarModel.service.Impl;

import com.gl.CarModel.dto.ModelColorsDto;
import com.gl.CarModel.dto.ModelColorsResponseDto;
import com.gl.CarModel.entity.ModelColors;
import com.gl.CarModel.exception.ResourceNotFoundException;
import com.gl.CarModel.repository.ModelColorsRepo;
import com.gl.CarModel.service.ModelColorsService;
import com.gl.CarModel.utils.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class ModelColorsServiceImplTest {

    @Mock
    private ModelColorsRepo modelColorsRepo; // Mocking the repository

    @InjectMocks
    private ModelColorsServiceImpl modelColorsService; // Service to be tested

    private ModelColorsDto modelColorsDto;
    private ModelColors modelColors;
    private ModelColorsResponseDto modelColorsResponseDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        modelColorsDto = new ModelColorsDto();
        modelColorsDto.setColor("Red");

        modelColors = new ModelColors();
        modelColors.setColor_id(1L);
        modelColors.setColor("RED");
        modelColors.setCreatedAt(LocalDateTime.now());
        modelColors.setUpdatedAt(LocalDateTime.now());

        modelColorsResponseDto = new ModelColorsResponseDto();
        modelColorsResponseDto.setColor_id(1L);
        modelColorsResponseDto.setColor("RED");

    }

    @Test
    public void testAddModelColors() {

        when(modelColorsRepo.save(any(ModelColors.class))).thenReturn(modelColors);

        ModelColorsResponseDto responseDto = modelColorsService.addModelColors(modelColorsDto);

        assertNotNull(responseDto);
        assertEquals("RED", responseDto.getColor());
        assertEquals(1L, responseDto.getColor_id());
//        assertEquals(modelColors.getCreatedAt(),responseDto.getCreatedAt());
//        assertEquals(modelColors.getUpdatedAt(),responseDto.getUpdatedAt());
    }

    @Test
    public void testGetModelColorsById() {

        when(modelColorsRepo.findById(1L)).thenReturn(Optional.of(modelColors));

        ModelColorsResponseDto responseDto = modelColorsService.getModelColorsById(1L);


        assertNotNull(responseDto);
        assertEquals("RED", responseDto.getColor());
        assertEquals(1L, responseDto.getColor_id());
//        assertEquals(modelColors.getCreatedAt(),responseDto.getCreatedAt());
//        assertEquals(modelColors.getUpdatedAt(),responseDto.getUpdatedAt());
    }

    @Test
    public void testGetModelColorsById_ThrowsException() {
        when(modelColorsRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            modelColorsService.getModelColorsById(1L);
        });
    }

    @Test
    public void testGetAllModelColors() {
        when(modelColorsRepo.findAll()).thenReturn(java.util.Collections.singletonList(modelColors));

        var result = modelColorsService.getAllModelColors();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteModelColorsById() {

        when(modelColorsRepo.findById(1L)).thenReturn(Optional.of(modelColors));
        doNothing().when(modelColorsRepo).delete(any(ModelColors.class)); // Mock the delete behavior

        modelColorsService.deleteModelColorsById(1L);

        verify(modelColorsRepo, times(1)).delete(any(ModelColors.class));
    }

    @Test
    public void testDeleteModelColorsById_ThrowsException() {

        when(modelColorsRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            modelColorsService.deleteModelColorsById(1L);
        });
    }
}

