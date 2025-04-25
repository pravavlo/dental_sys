package edu.miu.cs489.controllerTest;

import edu.miu.cs489.controller.SurgeryController;
import edu.miu.cs489.dto.request.AddressRequestDto;
import edu.miu.cs489.dto.request.SurgeryRequestDto;
import edu.miu.cs489.dto.response.AddressResponseDto;
import edu.miu.cs489.dto.response.SurgeryResponseDto;
import edu.miu.cs489.service.SurgeryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SurgeryControllerTest {

    private SurgeryService surgeryService;
    private SurgeryController surgeryController;
    private AddressResponseDto addressResponseDto;

    @BeforeEach
    void setUp() {
        surgeryService = Mockito.mock(SurgeryService.class);
        surgeryController = new SurgeryController(surgeryService);
    }

    private SurgeryRequestDto createSampleRequestDto() {
        return new SurgeryRequestDto(
                "SURG-001",
                new AddressRequestDto("123 Main St", "Fairfield", "IA", "52556", "USA")
        );
    }

    private SurgeryResponseDto createSampleResponseDto() {
        return new SurgeryResponseDto(
                1L,
                "SURG-001",
                addressResponseDto
        );
    }

    @Test
    void testCreateSurgery() {
        var requestDto = createSampleRequestDto();
        var responseDto = createSampleResponseDto();

        when(surgeryService.createSurgery(requestDto)).thenReturn(responseDto);

        ResponseEntity<SurgeryResponseDto> response = surgeryController.createSurgery(requestDto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testGetSurgeryById() {
        var responseDto = createSampleResponseDto();

        when(surgeryService.getSurgeryById(1L)).thenReturn(responseDto);

        var response = surgeryController.getSurgeryById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testGetSurgeryBySurgeryNo() {
        var responseDto = createSampleResponseDto();

        when(surgeryService.getSurgeryBySurgeryNo("SURG-001")).thenReturn(responseDto);

        var response = surgeryController.getSurgeryBySurgeryNo("SURG-001");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testGetAllSurgeries() {
        List<SurgeryResponseDto> list = List.of(createSampleResponseDto());

        when(surgeryService.getAllSurgeries()).thenReturn(list);

        var response = surgeryController.getAllSurgeries();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(list, response.getBody());
    }

    @Test
    void testUpdateSurgery() {
        var requestDto = createSampleRequestDto();
        var responseDto = createSampleResponseDto();

        when(surgeryService.updateSurgery(1L, requestDto)).thenReturn(responseDto);

        var response = surgeryController.updateSurgery(1L, requestDto);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testDeleteSurgery() {
        doNothing().when(surgeryService).deleteSurgery(1L);

        var response = surgeryController.deleteSurgery(1L);
        assertEquals(204, response.getStatusCodeValue());
        verify(surgeryService, times(1)).deleteSurgery(1L);
    }
}