package com.gl.CarModel.service.Impl;

import com.gl.CarModel.dto.ModelMakesDto;
import com.gl.CarModel.dto.ModelMakesResponseDto;
import com.gl.CarModel.entity.ModelMakes;
import com.gl.CarModel.exception.ResourceNotFoundException;
import com.gl.CarModel.repository.ModelMakesRepo;
import com.gl.CarModel.service.ModelMakesService;
import com.gl.CarModel.utils.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class ModelMakesServiceImplTest {

    @Mock
    private ModelMakesRepo modelMakesRepo; // Mocking the repository

    @InjectMocks
    private ModelMakesServiceImpl modelMakesService; // Service to be tested

    private ModelMakesDto modelMakesDto;
    private ModelMakes modelMakes;
    private ModelMakesResponseDto modelMakesResponseDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        modelMakesDto = new ModelMakesDto();
        modelMakesDto.setMakes("Test Make");

        modelMakes = new ModelMakes();
        modelMakes.setMakes_id(1L);
        modelMakes.setMakes("TEST MAKE");
        modelMakes.setCreatedAt(LocalDateTime.now());
        modelMakes.setUpdatedAt(LocalDateTime.now());

        modelMakesResponseDto = new ModelMakesResponseDto();
        modelMakesResponseDto.setMakes_id(1L);
        modelMakesResponseDto.setMakes("TEST MAKE");

    }

    @Test
    public void testAddModelMakes() {
        when(modelMakesRepo.save(any(ModelMakes.class))).thenReturn(modelMakes);

        ModelMakesResponseDto responseDto = modelMakesService.addModelMakes(modelMakesDto);

        assertNotNull(responseDto);
        assertEquals("TEST MAKE", responseDto.getMakes());
        assertEquals(1L, responseDto.getMakes_id());

    }

    @Test
    public void testGetModelMakesById() {

        when(modelMakesRepo.findById(1L)).thenReturn(Optional.of(modelMakes));

        ModelMakesResponseDto responseDto = modelMakesService.getModelMakesById(1L);


        assertNotNull(responseDto);
        assertEquals("TEST MAKE", responseDto.getMakes());
        assertEquals(1L, responseDto.getMakes_id());


    }

    @Test
    public void testGetModelMakesById_ThrowsException() {

        when(modelMakesRepo.findById(1L)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> {
            modelMakesService.getModelMakesById(1L);
        });
    }

    @Test
    public void testGetAllModelMakes() {

        when(modelMakesRepo.findAll()).thenReturn(java.util.Collections.singletonList(modelMakes));


        var result = modelMakesService.getAllModelMakes();


        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteModelMakesById() {

        when(modelMakesRepo.findById(1L)).thenReturn(Optional.of(modelMakes));
        doNothing().when(modelMakesRepo).delete(any(ModelMakes.class)); // Mock the delete behavior


        modelMakesService.deleteModelMakesById(1L);


        verify(modelMakesRepo, times(1)).delete(any(ModelMakes.class));
    }

    @Test
    public void testDeleteModelMakesById_ThrowsException() {
        when(modelMakesRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            modelMakesService.deleteModelMakesById(1L);
        });
    }
}

