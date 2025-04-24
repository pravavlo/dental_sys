package edu.miu.cs489.service.impl;

//
//import edu.miu.cs489.dto.request.AppointmentRequestDto;
//import edu.miu.cs489.dto.response.AppointmentResponseDto;
//import edu.miu.cs489.exception.ResourceNotFoundException;
//import edu.miu.cs489.mapper.AppointmentMapper;
//import edu.miu.cs489.model.Appointment;
//import edu.miu.cs489.model.Dentist;
//import edu.miu.cs489.model.Patient;
//import edu.miu.cs489.model.Surgery;
//import edu.miu.cs489.repository.AppointmentRepository;
//import edu.miu.cs489.repository.DentistRepository;
//import edu.miu.cs489.repository.PatientRepository;
//import edu.miu.cs489.repository.SurgeryRepository;
//import edu.miu.cs489.service.AppointmentService;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class AppointmentServiceImpl implements AppointmentService {
//
//    private final AppointmentRepository appointmentRepository;
//    private final AppointmentMapper appointmentMapper;
//    private final PatientRepository patientRepository;
//    private final DentistRepository dentistRepository;
//    private final SurgeryRepository surgeryRepository;
//
//    @Override
//    @Transactional
//    public AppointmentResponseDto createAppointment(AppointmentRequestDto appointmentRequestDto) {
//        Appointment appointment = new Appointment();
//
//        appointment.setAppointmentDate(appointmentRequestDto.getAppointmentDate());
//        appointment.setAppointmentTime(appointmentRequestDto.getAppointmentTime());
//
//        // Set patient
//        Patient patient = patientRepository.findById(appointmentRequestDto.getPatientId())
//                                           .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + appointmentRequestDto.getPatientId()));
//        appointment.setPatient(patient);
//
//        // Set dentist
//        Dentist dentist = dentistRepository.findById(appointmentRequestDto.getDentistId())
//                                           .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + appointmentRequestDto.getDentistId()));
//        appointment.setDentist(dentist);
//
//        // Set surgery
//        Surgery surgery;
//        if (appointmentRequestDto.getSurgeryRequestDto().getSurgeryNo() != null) {
//            surgery = surgeryRepository.findBySurgeryNo(appointmentRequestDto.getSurgeryRequestDto().getSurgeryNo())
//                                       .orElseGet(() -> {
//                                           Surgery newSurgery = appointmentMapper.appointmentRequestDtoToAppointment(appointmentRequestDto).getSurgery();
//                                           return surgeryRepository.save(newSurgery);
//                                       });
//        } else {
//            throw new IllegalArgumentException("Surgery number is required");
//        }
//        appointment.setSurgery(surgery);
//
//        Appointment savedAppointment = appointmentRepository.save(appointment);
//        return appointmentMapper.appointmentToAppointmentResponseDto(savedAppointment);
//    }
//
//    @Override
//    public AppointmentResponseDto getAppointmentById(Long id) {
//        Appointment appointment = appointmentRepository.findById(id)
//                                                       .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
//        return appointmentMapper.appointmentToAppointmentResponseDto(appointment);
//    }
//
//    @Override
//    public List<AppointmentResponseDto> getAppointmentsByDentistId(Long dentistId) {
//        Dentist dentist = dentistRepository.findById(dentistId)
//                                           .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + dentistId));
//        List<Appointment> appointments = appointmentRepository.findByDentist(dentist);
//        return appointmentMapper.appointmentsToAppointmentResponseDtos(appointments);
//    }
//
//    @Override
//    public List<AppointmentResponseDto> getAppointmentsByPatientId(Long patientId) {
//        Patient patient = patientRepository.findById(patientId)
//                                           .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
//        List<Appointment> appointments = appointmentRepository.findByPatient(patient);
//        return appointmentMapper.appointmentsToAppointmentResponseDtos(appointments);
//    }
//
//    @Override
//    public List<AppointmentResponseDto> getAppointmentsBySurgeryId(Long surgeryId) {
//        Surgery surgery = surgeryRepository.findById(surgeryId)
//                                           .orElseThrow(() -> new ResourceNotFoundException("Surgery not found with id: " + surgeryId));
//        List<Appointment> appointments = appointmentRepository.findBySurgery(surgery);
//        return appointmentMapper.appointmentsToAppointmentResponseDtos(appointments);
//    }
//
//    @Override
//    public List<AppointmentResponseDto> getAppointmentsByDate(LocalDate date) {
//        List<Appointment> appointments = appointmentRepository.findByAppointmentDate(date);
//        return appointmentMapper.appointmentsToAppointmentResponseDtos(appointments);
//    }
//
//    @Override
//    public List<AppointmentResponseDto> getAllAppointments() {
//        List<Appointment> appointments = appointmentRepository.findAll();
//        return appointmentMapper.appointmentsToAppointmentResponseDtos(appointments);
//    }
//
//    @Override
//    @Transactional
//    public AppointmentResponseDto updateAppointment(Long id, AppointmentRequestDto appointmentRequestDto) {
//        Appointment existingAppointment = appointmentRepository.findById(id)
//                                                               .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
//
//        existingAppointment.setAppointmentDate(appointmentRequestDto.getAppointmentDate());
//        existingAppointment.setAppointmentTime(appointmentRequestDto.getAppointmentTime());
//
//        // Update patient
//        Patient patient = patientRepository.findById(appointmentRequestDto.getPatientId())
//                                           .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + appointmentRequestDto.getPatientId()));
//        existingAppointment.setPatient(patient);
//
//        // Update dentist
//        Dentist dentist = dentistRepository.findById(appointmentRequestDto.getDentistId())
//                                           .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + appointmentRequestDto.getDentistId()));
//        existingAppointment.setDentist(dentist);
//
//        // Update surgery
//        Surgery surgery;
//        if (appointmentRequestDto.getSurgeryRequestDto().getSurgeryNo() != null) {
//            surgery = surgeryRepository.findBySurgeryNo(appointmentRequestDto.getSurgeryRequestDto().getSurgeryNo())
//                                       .orElseGet(() -> {
//                                           Surgery newSurgery = appointmentMapper.appointmentRequestDtoToAppointment(appointmentRequestDto).getSurgery();
//                                           return surgeryRepository.save(newSurgery);
//                                       });
//        } else {
//            throw new IllegalArgumentException("Surgery number is required");
//        }
//        existingAppointment.setSurgery(surgery);
//
//        Appointment savedAppointment = appointmentRepository.save(existingAppointment);
//        return appointmentMapper.appointmentToAppointmentResponseDto(savedAppointment);
//    }
//
//    @Override
//    public void deleteAppointment(Long id) {
//        if (!appointmentRepository.existsById(id)) {
//            throw new ResourceNotFoundException("Appointment not found with id: " + id);
//        }
//        appointmentRepository.deleteById(id);
//    }
//}

import edu.miu.cs489.dto.request.AppointmentRequestDto;
import edu.miu.cs489.dto.response.AppointmentResponseDto;
import edu.miu.cs489.dto.response.DentistResponseDto;

import edu.miu.cs489.exception.ResourceNotFoundException;
import edu.miu.cs489.mapper.AddressMapper;
import edu.miu.cs489.mapper.PatientMapper;
import edu.miu.cs489.mapper.SurgeryMapper;
import edu.miu.cs489.model.Appointment;
import edu.miu.cs489.model.Dentist;
import edu.miu.cs489.model.Patient;
import edu.miu.cs489.model.Surgery;
import edu.miu.cs489.repository.AppointmentRepository;
import edu.miu.cs489.repository.DentistRepository;
import edu.miu.cs489.repository.PatientRepository;
import edu.miu.cs489.repository.SurgeryRepository;
import edu.miu.cs489.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;
    private final SurgeryRepository surgeryRepository;
    private final AddressMapper addressMapper;
    private final SurgeryMapper surgeryMapper;
    private final PatientMapper patientMapper;

    private AppointmentResponseDto convertToResponseDto(Appointment appointment) {
        if (appointment == null) {
            return null;
        }
        return new AppointmentResponseDto(
                appointment.getId(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                patientMapper.patientToPatientResponseDto(appointment.getPatient()), // Use PatientMapper
                convertToDentistResponseDto(appointment.getDentist()),
                surgeryMapper.surgeryToSurgeryResponseDto(appointment.getSurgery()) // Use SurgeryMapper
        );
    }



    private DentistResponseDto convertToDentistResponseDto(Dentist dentist) {
        if (dentist == null) {
            return null;
        }
        return new DentistResponseDto(
                dentist.getId(),
                dentist.getDentistName(),
                dentist.getSpecialization()
        );
    }

    private Surgery convertToSurgeryEntity(AppointmentRequestDto appointmentRequestDto) {
        return surgeryMapper.surgeryRequestDtoToSurgery(appointmentRequestDto.getSurgeryRequestDto());
    }

    private Appointment convertToEntity(AppointmentRequestDto appointmentRequestDto, Patient patient, Dentist dentist, Surgery surgery) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentRequestDto.getAppointmentDate());
        appointment.setAppointmentTime(appointmentRequestDto.getAppointmentTime());
        appointment.setPatient(patient);
        appointment.setDentist(dentist);
        appointment.setSurgery(surgery);
        return appointment;
    }


    @Override
    @Transactional
    public AppointmentResponseDto createAppointment(AppointmentRequestDto appointmentRequestDto) {
        // Set patient
        Patient patient = patientRepository.findById(appointmentRequestDto.getPatientId())
                                           .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + appointmentRequestDto.getPatientId()));

        // Set dentist
        Dentist dentist = dentistRepository.findById(appointmentRequestDto.getDentistId())
                                           .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + appointmentRequestDto.getDentistId()));

        // Set surgery
        Surgery surgery;
        if (appointmentRequestDto.getSurgeryRequestDto().getSurgeryNo() != null) {
            surgery = surgeryRepository.findBySurgeryNo(appointmentRequestDto.getSurgeryRequestDto().getSurgeryNo())
                                       .orElseGet(() -> surgeryRepository.save(convertToSurgeryEntity(appointmentRequestDto)));
        } else {
            throw new IllegalArgumentException("Surgery number is required");
        }

        Appointment appointment = convertToEntity(appointmentRequestDto, patient, dentist, surgery);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return convertToResponseDto(savedAppointment);
    }

    @Override
    public AppointmentResponseDto getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                                                       .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        return convertToResponseDto(appointment);
    }

    @Override
    public List<AppointmentResponseDto> getAppointmentsByDentistId(Long dentistId) {
        Dentist dentist = dentistRepository.findById(dentistId)
                                           .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + dentistId));
        List<Appointment> appointments = appointmentRepository.findByDentist(dentist);
        return appointments.stream()
                           .map(this::convertToResponseDto)
                           .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponseDto> getAppointmentsByPatientId(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                                           .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
        List<Appointment> appointments = appointmentRepository.findByPatient(patient);
        return appointments.stream()
                           .map(this::convertToResponseDto)
                           .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponseDto> getAppointmentsBySurgeryId(Long surgeryId) {
        Surgery surgery = surgeryRepository.findById(surgeryId)
                                           .orElseThrow(() -> new ResourceNotFoundException("Surgery not found with id: " + surgeryId));
        List<Appointment> appointments = appointmentRepository.findBySurgery(surgery);
        return appointments.stream()
                           .map(this::convertToResponseDto)
                           .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponseDto> getAppointmentsByDate(LocalDate date) {
        List<Appointment> appointments = appointmentRepository.findByAppointmentDate(date);
        return appointments.stream()
                           .map(this::convertToResponseDto)
                           .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponseDto> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream()
                           .map(this::convertToResponseDto)
                           .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AppointmentResponseDto updateAppointment(Long id, AppointmentRequestDto appointmentRequestDto) {
        Appointment existingAppointment = appointmentRepository.findById(id)
                                                               .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        existingAppointment.setAppointmentDate(appointmentRequestDto.getAppointmentDate());
        existingAppointment.setAppointmentTime(appointmentRequestDto.getAppointmentTime());

        // Update patient
        Patient patient = patientRepository.findById(appointmentRequestDto.getPatientId())
                                           .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + appointmentRequestDto.getPatientId()));
        existingAppointment.setPatient(patient);

        // Update dentist
        Dentist dentist = dentistRepository.findById(appointmentRequestDto.getDentistId())
                                           .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + appointmentRequestDto.getDentistId()));
        existingAppointment.setDentist(dentist);

        // Update surgery
        Surgery surgery;
        if (appointmentRequestDto.getSurgeryRequestDto().getSurgeryNo() != null) {
            surgery = surgeryRepository.findBySurgeryNo(appointmentRequestDto.getSurgeryRequestDto().getSurgeryNo())
                                       .orElseGet(() -> surgeryRepository.save(convertToSurgeryEntity(appointmentRequestDto)));
        } else {
            throw new IllegalArgumentException("Surgery number is required");
        }
        existingAppointment.setSurgery(surgery);

        Appointment savedAppointment = appointmentRepository.save(existingAppointment);
        return convertToResponseDto(savedAppointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }
}
