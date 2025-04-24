package edu.miu.cs489.service.impl;


import edu.miu.cs489.dto.request.PatientRequestDto;
import edu.miu.cs489.dto.response.PatientResponseDto;
import edu.miu.cs489.exception.ResourceNotFoundException;
import edu.miu.cs489.mapper.PatientMapper;
import edu.miu.cs489.model.Patient;
import edu.miu.cs489.repository.PatientRepository;
import edu.miu.cs489.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {
        Patient patient = patientMapper.patientRequestDtoToPatient(patientRequestDto);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.patientToPatientResponseDto(savedPatient);
    }

    @Override
    public PatientResponseDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                                           .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return patientMapper.patientToPatientResponseDto(patient);
    }

    @Override
    public PatientResponseDto getPatientByPatNo(String patNo) {
        Patient patient = patientRepository.findByPatNo(patNo)
                                           .orElseThrow(() -> new ResourceNotFoundException("Patient not found with patient number: " + patNo));
        return patientMapper.patientToPatientResponseDto(patient);
    }

    @Override
    public List<PatientResponseDto> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patientMapper.patientsToPatientResponseDtos(patients);
    }

    @Override
    public PatientResponseDto updatePatient(Long id, PatientRequestDto patientRequestDto) {
        Patient existingPatient = patientRepository.findById(id)
                                                   .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        Patient updatedPatient = patientMapper.patientRequestDtoToPatient(patientRequestDto);
        updatedPatient.setId(existingPatient.getId());

        Patient savedPatient = patientRepository.save(updatedPatient);
        return patientMapper.patientToPatientResponseDto(savedPatient);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
}
