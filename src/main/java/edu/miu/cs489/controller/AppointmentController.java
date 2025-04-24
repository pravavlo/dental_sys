package edu.miu.cs489.controller;


import edu.miu.cs489.dto.request.AppointmentRequestDto;
import edu.miu.cs489.dto.response.AppointmentResponseDto;
import edu.miu.cs489.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> createAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto) {
        return new ResponseEntity<>(appointmentService.createAppointment(appointmentRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping("/dentist/{dentistId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByDentistId(@PathVariable Long dentistId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDentistId(dentistId));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatientId(patientId));
    }

    @GetMapping("/surgery/{surgeryId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsBySurgeryId(@PathVariable Long surgeryId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsBySurgeryId(surgeryId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDate(date));
    }

}
