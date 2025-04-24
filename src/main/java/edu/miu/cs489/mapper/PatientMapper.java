package edu.miu.cs489.mapper;


import edu.miu.cs489.dto.request.PatientRequestDto;
import edu.miu.cs489.dto.response.PatientResponseDto;
import edu.miu.cs489.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AddressMapper.class})
public interface PatientMapper {
    Patient patientRequestDtoToPatient(PatientRequestDto patientRequestDto);

    PatientRequestDto patientToPatientRequestDto(Patient patient);

    PatientResponseDto patientToPatientResponseDto(Patient patient);

    List<PatientResponseDto> patientsToPatientResponseDtos(List<Patient> patients);
}
