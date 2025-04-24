package edu.miu.cs489.mapper;

import edu.miu.cs489.dto.request.PatientRequestDto;
import edu.miu.cs489.dto.response.PatientResponseDto;
import edu.miu.cs489.model.Patient;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-24T22:41:41+0545",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.4.1 (Eclipse Adoptium)"
)
@Component
public class PatientMapperImpl implements PatientMapper {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Patient patientRequestDtoToPatient(PatientRequestDto patientRequestDto) {
        if ( patientRequestDto == null ) {
            return null;
        }

        Patient patient = new Patient();

        patient.setPatNo( patientRequestDto.getPatNo() );
        patient.setPatName( patientRequestDto.getPatName() );
        patient.setAddress( addressMapper.addressRequestDtoToAddress( patientRequestDto.getAddress() ) );

        return patient;
    }

    @Override
    public PatientRequestDto patientToPatientRequestDto(Patient patient) {
        if ( patient == null ) {
            return null;
        }

        PatientRequestDto patientRequestDto = new PatientRequestDto();

        patientRequestDto.setPatNo( patient.getPatNo() );
        patientRequestDto.setPatName( patient.getPatName() );
        patientRequestDto.setAddress( addressMapper.addressToAddressRequestDto( patient.getAddress() ) );

        return patientRequestDto;
    }

    @Override
    public PatientResponseDto patientToPatientResponseDto(Patient patient) {
        if ( patient == null ) {
            return null;
        }

        PatientResponseDto patientResponseDto = new PatientResponseDto();

        patientResponseDto.setId( patient.getId() );
        patientResponseDto.setPatNo( patient.getPatNo() );
        patientResponseDto.setPatName( patient.getPatName() );
        patientResponseDto.setAddress( addressMapper.addressToAddressResponseDto( patient.getAddress() ) );

        return patientResponseDto;
    }

    @Override
    public List<PatientResponseDto> patientsToPatientResponseDtos(List<Patient> patients) {
        if ( patients == null ) {
            return null;
        }

        List<PatientResponseDto> list = new ArrayList<PatientResponseDto>( patients.size() );
        for ( Patient patient : patients ) {
            list.add( patientToPatientResponseDto( patient ) );
        }

        return list;
    }
}
