package edu.miu.cs489.controllerTest;


import edu.miu.cs489.controller.DentistController;
import edu.miu.cs489.dto.request.DentistRequestDto;
import edu.miu.cs489.dto.response.DentistResponseDto;
import edu.miu.cs489.service.DentistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DentistControllerTest {

    private DentistService dentistService;
    private DentistController dentistController;

    @BeforeEach
    void setUp() {
        dentistService = mock(DentistService.class);
        dentistController = new DentistController(dentistService);
    }

    private DentistRequestDto getRequestDto() {
        return new DentistRequestDto("Dr. Jane Doe", "D567", "Pediatric Dentistry");
    }

    private DentistResponseDto getResponseDto() {
        return new DentistResponseDto(1L, "Dr. Jane Doe", "D567", "Pediatric Dentistry");
    }

    @Test
    void testCreateDentist() {
        DentistRequestDto requestDto = getRequestDto();
        DentistResponseDto expectedResponse = getResponseDto();

        when(dentistService.createDentist(requestDto)).thenReturn(expectedResponse);

        ResponseEntity<DentistResponseDto> response = dentistController.createDentist(requestDto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testGetDentistById() {
        DentistResponseDto expectedResponse = getResponseDto();

        when(dentistService.getDentistById(1L)).thenReturn(expectedResponse);

        ResponseEntity<DentistResponseDto> response = dentistController.getDentistById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testGetDentistByName() {
        DentistResponseDto expectedResponse = getResponseDto();

        when(dentistService.getDentistByName("Dr. Jane Doe")).thenReturn(expectedResponse);

        ResponseEntity<DentistResponseDto> response = dentistController.getDentistByName("Dr. Jane Doe");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

//    @Test
//    void testGetAllDentists() {
//        List<DentistResponseDto> expectedList = List.of(getResponseDto());
//
//        when(dentistService.getAllDentists()).thenReturn(expectedList);
//
//        ResponseEntity<List<DentistResponseDto>> response = dentistController.getAllDentists();
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(expectedList, response.getBody());
//    }

    @Test
    void testUpdateDentist() {
        DentistRequestDto requestDto = getRequestDto();
        DentistResponseDto expectedResponse = getResponseDto();

        when(dentistService.updateDentist(1L, requestDto)).thenReturn(expectedResponse);

        ResponseEntity<DentistResponseDto> response = dentistController.updateDentist(1L, requestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testDeleteDentist() {
        doNothing().when(dentistService).deleteDentist(1L);

        ResponseEntity<Void> response = dentistController.deleteDentist(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(dentistService, times(1)).deleteDentist(1L);
    }
}

