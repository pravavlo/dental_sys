package edu.miu.cs489.service;


import edu.miu.cs489.dto.request.AppointmentRequestDto;
import edu.miu.cs489.dto.response.AppointmentResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    AppointmentResponseDto createAppointment(AppointmentRequestDto appointmentRequestDto);

    AppointmentResponseDto getAppointmentById(Long id);

    List<AppointmentResponseDto> getAppointmentsByDentistId(Long dentistId);

    List<AppointmentResponseDto> getAppointmentsByPatientId(Long patientId);

    List<AppointmentResponseDto> getAppointmentsBySurgeryId(Long surgeryId);

    List<AppointmentResponseDto> getAppointmentsByDate(LocalDate date);

    List<AppointmentResponseDto> getAllAppointments();

    AppointmentResponseDto updateAppointment(Long id, AppointmentRequestDto appointmentRequestDto);

    void deleteAppointment(Long id);
    void markAppointmentAsPaid(Long id);
    }
