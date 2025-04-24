//package edu.miu.cs489.mapper;
//
//
//import edu.miu.cs489.dto.request.AppointmentRequestDto;
//import edu.miu.cs489.dto.response.AppointmentResponseDto;
//import edu.miu.cs489.model.Appointment;
//import edu.miu.cs489.model.Dentist;
//import edu.miu.cs489.model.Patient;
//import edu.miu.cs489.repository.DentistRepository;
//import edu.miu.cs489.repository.PatientRepository;
//import edu.miu.cs489.repository.SurgeryRepository;
//import org.mapstruct.*;
//
//import java.util.List;
//
//@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
//        uses = {SurgeryMapper.class, PatientMapper.class, DentistMapper.class})
//public abstract class AppointmentMapper {
//
//    protected final PatientRepository patientRepository;
//    protected final DentistRepository dentistRepository;
//    protected final SurgeryRepository surgeryRepository;
//
//    public AppointmentMapper(PatientRepository patientRepository, DentistRepository dentistRepository, SurgeryRepository surgeryRepository) {
//        this.patientRepository = patientRepository;
//        this.dentistRepository = dentistRepository;
//        this.surgeryRepository = surgeryRepository;
//    }
//
//    // Entity to Response DTO
//    @Mapping(source = "surgery", target = "surgeryResponseDto")
//    public abstract AppointmentResponseDto toResponseDto(Appointment appointment);
//
//    // Request DTO to Entity
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "patient", source = "patientId", qualifiedByName = "patientIdToPatient")
//    @Mapping(target = "dentist", source = "dentistId", qualifiedByName = "dentistIdToDentist")
//    @Mapping(target = "surgery", source = "appointmentRequestDto.surgeryRequestDto")
//    public abstract Appointment toEntity(AppointmentRequestDto appointmentRequestDto);
//
//    // Update existing appointment from DTO
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "patient", source = "patientId", qualifiedByName = "patientIdToPatient")
//    @Mapping(target = "dentist", source = "dentistId", qualifiedByName = "dentistIdToDentist")
//    @Mapping(target = "surgery", source = "appointmentRequestDto.surgeryRequestDto")
//    public abstract void updateAppointmentFromDto(AppointmentRequestDto appointmentRequestDto, @MappingTarget Appointment appointment);
//
//    // List mapping
//    public abstract List<AppointmentResponseDto> toResponseDtoList(List<Appointment> appointments);
//
//    // Helper methods for mapping IDs to entities
//    @Named("patientIdToPatient")
//    protected Patient patientIdToPatient(Long patientId) {
//        if (patientId == null) {
//            return null;
//        }
//        return patientRepository.findById(patientId)
//                                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
//    }
//
//    @Named("dentistIdToDentist")
//    protected Dentist dentistIdToDentist(Long dentistId) {
//        if (dentistId == null) {
//            return null;
//        }
//        return dentistRepository.findById(dentistId)
//                                .orElseThrow(() -> new RuntimeException("Dentist not found with ID: " + dentistId));
//    }
//}