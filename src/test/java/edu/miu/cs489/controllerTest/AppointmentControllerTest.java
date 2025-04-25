package edu.miu.cs489.controllerTest;


import edu.miu.cs489.controller.AppointmentController;
import edu.miu.cs489.dto.request.AppointmentRequestDto;
import edu.miu.cs489.dto.response.*;
import edu.miu.cs489.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppointmentControllerTest {

    private AppointmentService appointmentService;
    private AppointmentController appointmentController;
    AddressResponseDto AddressResponseDto;
    @BeforeEach
    void setUp() {
        appointmentService = Mockito.mock(AppointmentService.class);
        appointmentController = new AppointmentController(appointmentService);
    }

    private AppointmentRequestDto createSampleRequestDto() {
        return new AppointmentRequestDto(
                LocalDate.of(2025, 4, 25),
                LocalTime.of(10, 30),
                1L, // patientId
                2L, // dentistId
                3L, // surgeryId
                false
        );
    }

    private AppointmentResponseDto createSampleResponseDto() {
        PatientResponseDto patient;

        patient = new PatientResponseDto(1L, "John", "Doe", AddressResponseDto);
        DentistResponseDto dentist = new DentistResponseDto();
        SurgeryResponseDto surgery = new SurgeryResponseDto(3L, "Smile Dental Surgery", AddressResponseDto);

        return new AppointmentResponseDto(
                100L,
                LocalDate.of(2025, 4, 25),
                LocalTime.of(10, 30),
                patient,
                dentist,
                surgery
        );
    }

    @Test
    void testCreateAppointment() {
        AppointmentRequestDto requestDto = createSampleRequestDto();
        AppointmentResponseDto responseDto = createSampleResponseDto();

        when(appointmentService.createAppointment(requestDto)).thenReturn(responseDto);

        ResponseEntity<AppointmentResponseDto> response = appointmentController.createAppointment(requestDto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testGetAppointments() {
        List<AppointmentResponseDto> expectedList = List.of(createSampleResponseDto());

        when(appointmentService.getAllAppointments()).thenReturn(expectedList);

        var response = appointmentController.getAppointments();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedList.size(), response.getBody().size());
    }

    @Test
    void testGetAppointmentsByDentistId() {
        when(appointmentService.getAppointmentsByDentistId(2L)).thenReturn(List.of(createSampleResponseDto()));

        var response = appointmentController.getAppointmentsByDentistId(2L);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetAppointmentsByPatientId() {
        when(appointmentService.getAppointmentsByPatientId(1L)).thenReturn(List.of(createSampleResponseDto()));

        var response = appointmentController.getAppointmentsByPatientId(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAppointmentsBySurgeryId() {
        when(appointmentService.getAppointmentsBySurgeryId(3L)).thenReturn(List.of(createSampleResponseDto()));

        var response = appointmentController.getAppointmentsBySurgeryId(3L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAppointmentsByDate() {
        LocalDate date = LocalDate.of(2025, 4, 25);
        when(appointmentService.getAppointmentsByDate(date)).thenReturn(List.of(createSampleResponseDto()));

        var response = appointmentController.getAppointmentsByDate(date);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteAppointment() {
        doNothing().when(appointmentService).deleteAppointment(100L);

        var response = appointmentController.deleteAppointment(100L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testMarkAppointmentAsPaid() {
        doNothing().when(appointmentService).markAppointmentAsPaid(100L);

        var response = appointmentController.markAppointmentAsPaid(100L);
        assertEquals(204, response.getStatusCodeValue());
    }
}