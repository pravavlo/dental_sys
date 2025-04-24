package edu.miu.cs489.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDto {
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private Long patientId;
    private Long dentistId;
    private Long surgeryId ;
    private boolean paid;
}

