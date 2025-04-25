package edu.miu.cs489.controller;


import edu.miu.cs489.dto.request.AppointmentRequestDto;
import edu.miu.cs489.dto.response.AppointmentResponseDto;
import edu.miu.cs489.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    @PreAuthorize("hasRole('OFFICE_MANAGER')")

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> createAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto) {
        return new ResponseEntity<>(appointmentService.createAppointment(appointmentRequestDto), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('OFFICE_MANAGER')")

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> getAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @PreAuthorize("hasRole('DENTIST')")

    @GetMapping("/dentist/{dentistId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByDentistId(@PathVariable Long dentistId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDentistId(dentistId));
    }

    @PreAuthorize("hasRole('PATIENT')")

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatientId(patientId));
    }

    @PreAuthorize("hasRole('OFFICE_MANAGER')")

    @GetMapping("/surgery/{surgeryId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsBySurgeryId(@PathVariable Long surgeryId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsBySurgeryId(surgeryId));
    }

    @PreAuthorize("hasRole('OFFICE_MANAGER')")

    @GetMapping("/date/{date}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDate(date));
    }

    @PreAuthorize("hasRole('OFFICE_MANAGER')")

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OFFICE_MANAGER')")

    @PatchMapping("/{id}/mark-paid")
    public ResponseEntity<Void> markAppointmentAsPaid(@PathVariable Long id) {
        appointmentService.markAppointmentAsPaid(id);
        return ResponseEntity.noContent().build();
    }


}
