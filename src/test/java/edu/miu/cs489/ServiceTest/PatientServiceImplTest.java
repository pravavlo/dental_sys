package edu.miu.cs489.ServiceTest;

import edu.miu.cs489.dto.request.PatientRequestDto;
import edu.miu.cs489.dto.response.PatientResponseDto;
import edu.miu.cs489.exception.DataValidationException;
import edu.miu.cs489.exception.ResourceNotFoundException;
import edu.miu.cs489.mapper.PatientMapper;
import edu.miu.cs489.model.Patient;
import edu.miu.cs489.repository.PatientRepository;
import edu.miu.cs489.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patient;
    private PatientRequestDto requestDto;
    private PatientResponseDto responseDto;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setPatNo("P001");

        requestDto = new PatientRequestDto();
        responseDto = new PatientResponseDto();
    }

    @Test
    void testCreatePatient_Success() {
        when(patientMapper.patientRequestDtoToPatient(requestDto)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.patientToPatientResponseDto(patient)).thenReturn(responseDto);

        PatientResponseDto result = patientService.createPatient(requestDto);

        assertNotNull(result);
        verify(patientRepository).save(patient);
    }

    @Test
    void testCreatePatient_Exception() {
        when(patientMapper.patientRequestDtoToPatient(requestDto)).thenReturn(patient);
        when(patientRepository.save(patient)).thenThrow(new RuntimeException("DB error"));

        DataValidationException ex = assertThrows(DataValidationException.class,
                () -> patientService.createPatient(requestDto));
        assertTrue(ex.getMessage().contains("Error creating patient"));
    }

    @Test
    void testGetPatientById_Success() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientMapper.patientToPatientResponseDto(patient)).thenReturn(responseDto);

        PatientResponseDto result = patientService.getPatientById(1L);

        assertNotNull(result);
        verify(patientRepository).findById(1L);
    }

    @Test
    void testGetPatientById_NotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.getPatientById(1L));
    }

    @Test
    void testGetPatientByPatNo_Success() {
        when(patientRepository.findByPatNo("P001")).thenReturn(Optional.of(patient));
        when(patientMapper.patientToPatientResponseDto(patient)).thenReturn(responseDto);

        PatientResponseDto result = patientService.getPatientByPatNo("P001");

        assertNotNull(result);
        verify(patientRepository).findByPatNo("P001");
    }

    @Test
    void testGetPatientByPatNo_NotFound() {
        when(patientRepository.findByPatNo("P001")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.getPatientByPatNo("P001"));
    }

    @Test
    void testGetAllPatients_Success() {
        List<Patient> patients = Arrays.asList(patient);
        List<PatientResponseDto> responses = Arrays.asList(responseDto);

        when(patientRepository.findAll()).thenReturn(patients);
        when(patientMapper.patientsToPatientResponseDtos(patients)).thenReturn(responses);

        List<PatientResponseDto> result = patientService.getAllPatients();

        assertEquals(1, result.size());
        verify(patientRepository).findAll();
    }

    @Test
    void testUpdatePatient_Success() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientMapper.patientRequestDtoToPatient(requestDto)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.patientToPatientResponseDto(patient)).thenReturn(responseDto);

        PatientResponseDto result = patientService.updatePatient(1L, requestDto);

        assertNotNull(result);
        verify(patientRepository).save(patient);
    }

    @Test
    void testUpdatePatient_NotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.updatePatient(1L, requestDto));
    }

    @Test
    void testDeletePatient_Success() {
        when(patientRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> patientService.deletePatient(1L));
        verify(patientRepository).deleteById(1L);
    }

    @Test
    void testDeletePatient_NotFound() {
        when(patientRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> patientService.deletePatient(1L));
    }
}

