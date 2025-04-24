package edu.miu.cs489.service;


import edu.miu.cs489.dto.request.PatientRequestDto;
import edu.miu.cs489.dto.response.PatientResponseDto;

import java.util.List;

public interface PatientService {
    PatientResponseDto createPatient(PatientRequestDto patientRequestDto);

    PatientResponseDto getPatientById(Long id);

    PatientResponseDto getPatientByPatNo(String patNo);

    List<PatientResponseDto> getAllPatients();

    PatientResponseDto updatePatient(Long id, PatientRequestDto patientRequestDto);

    void deletePatient(Long id);
}
