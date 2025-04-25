package edu.miu.cs489.controllerTest;
import edu.miu.cs489.controller.PatientController;
import edu.miu.cs489.dto.request.AddressRequestDto;
import edu.miu.cs489.dto.request.PatientRequestDto;
import edu.miu.cs489.dto.response.AddressResponseDto;
import edu.miu.cs489.dto.response.PatientResponseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.miu.cs489.service.PatientService;


import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    private PatientService patientService;
    private PatientController patientController;
    private   AddressResponseDto addressResponseDto;
    @BeforeEach
    void setUp() {
        patientService = mock(PatientService.class);
        patientController = new PatientController(patientService);
    }

    private PatientRequestDto createRequestDto() {
        AddressRequestDto address = new AddressRequestDto();
        return new PatientRequestDto("P100", "John Doe", address);
    }

    private PatientResponseDto createResponseDto() {
        AddressResponseDto address = new AddressResponseDto();
        return new PatientResponseDto(1L, "P100", "John Doe", address);
    }

    @Test
    void testCreatePatient() {
        PatientRequestDto requestDto = createRequestDto();
        PatientResponseDto expectedResponse = createResponseDto();

        when(patientService.createPatient(requestDto)).thenReturn(expectedResponse);

        ResponseEntity<PatientResponseDto> response = patientController.createPatient(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testGetPatientById() {
        PatientResponseDto expected = createResponseDto();

        when(patientService.getPatientById(1L)).thenReturn(expected);

        ResponseEntity<PatientResponseDto> response = patientController.getPatientById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    void testGetAllPatients() {
        List<PatientResponseDto> expected = List.of(createResponseDto());

        when(patientService.getAllPatients()).thenReturn(expected);

        ResponseEntity<List<PatientResponseDto>> response = patientController.getAllPatients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    void testUpdatePatient() {
        PatientRequestDto requestDto = createRequestDto();
        PatientResponseDto expected = createResponseDto();

        when(patientService.updatePatient(1L, requestDto)).thenReturn(expected);

        ResponseEntity<PatientResponseDto> response = patientController.updatePatient(1L, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    void testDeletePatient() {
        doNothing().when(patientService).deletePatient(1L);

        ResponseEntity<Void> response = patientController.deletePatient(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(patientService, times(1)).deletePatient(1L);
    }
}
