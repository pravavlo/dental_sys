package edu.miu.cs489.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDto {
    private Long id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private PatientResponseDto patient;
    private DentistResponseDto dentist;
    private SurgeryResponseDto surgeryResponseDto;
}
