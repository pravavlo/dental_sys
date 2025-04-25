package edu.miu.cs489.ServiceTest;
import edu.miu.cs489.dto.request.DentistRequestDto;
import edu.miu.cs489.dto.response.DentistResponseDto;
import edu.miu.cs489.exception.DataValidationException;
import edu.miu.cs489.exception.ResourceNotFoundException;
import edu.miu.cs489.mapper.DentistMapper;
import edu.miu.cs489.model.*;
import edu.miu.cs489.repository.*;

import edu.miu.cs489.service.impl.DentistServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class DentistServiceImplTest {
    @Mock
    private DentistRepository dentistRepository;

    @Mock
    private DentistMapper dentistMapper;

    @InjectMocks
    private DentistServiceImpl dentistService;

    private Dentist dentist;
    private DentistRequestDto requestDto;
    private DentistResponseDto responseDto;

    @BeforeEach
    void setUp() {
        dentist = new Dentist();
        dentist.setId(1L);
        dentist.setDentistName("Dr. Strange");

        requestDto = new DentistRequestDto();
        // populate fields accordingly

        responseDto = new DentistResponseDto();
        responseDto.setId(1L);
        responseDto.setDentistName("Dr. Strange");
    }

    @Test
    void testCreateDentist_Success() {
        when(dentistMapper.dentistRequestDtoToDentist(requestDto)).thenReturn(dentist);
        when(dentistRepository.save(dentist)).thenReturn(dentist);
        when(dentistMapper.dentistToDentistResponseDto(dentist)).thenReturn(responseDto);

        DentistResponseDto result = dentistService.createDentist(requestDto);

        assertNotNull(result);
        assertEquals(responseDto.getId(), result.getId());
        verify(dentistRepository).save(dentist);
    }

    @Test
    void testCreateDentist_Failure() {
        when(dentistMapper.dentistRequestDtoToDentist(requestDto)).thenReturn(dentist);
        when(dentistRepository.save(dentist)).thenThrow(new RuntimeException("DB down"));

        DataValidationException ex = assertThrows(DataValidationException.class, () -> dentistService.createDentist(requestDto));
        assertTrue(ex.getMessage().contains("Error creating dentist"));
    }

    @Test
    void testGetDentistById_Success() {
        when(dentistRepository.findById(1L)).thenReturn(Optional.of(dentist));
        when(dentistMapper.dentistToDentistResponseDto(dentist)).thenReturn(responseDto);

        DentistResponseDto result = dentistService.getDentistById(1L);

        assertEquals("Dr. Strange", result.getDentistName());
        verify(dentistRepository).findById(1L);
    }

    @Test
    void testGetDentistById_NotFound() {
        when(dentistRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> dentistService.getDentistById(1L));
    }

    @Test
    void testGetDentistByName_Success() {
        when(dentistRepository.findByDentistName("Dr. Strange")).thenReturn(Optional.of(dentist));
        when(dentistMapper.dentistToDentistResponseDto(dentist)).thenReturn(responseDto);

        DentistResponseDto result = dentistService.getDentistByName("Dr. Strange");

        assertEquals("Dr. Strange", result.getDentistName());
        verify(dentistRepository).findByDentistName("Dr. Strange");
    }

    @Test
    void testGetDentistByName_NotFound() {
        when(dentistRepository.findByDentistName("Ghost")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> dentistService.getDentistByName("Ghost"));
    }

//    @Test
//    void testGetAllDentists() {
//        List<Dentist> dentists = List.of(dentist);
//        List<DentistResponseDto> dtos = List.of(responseDto);
//
//        when(dentistRepository.findAll()).thenReturn(dentists);
//        when(dentistMapper.dentistsToDentistResponseDtos(dentists)).thenReturn(dtos);
//
//        List<DentistResponseDto> result = dentistService.getAllDentists();
//
//        assertEquals(1, result.size());
//        verify(dentistRepository).findAll();
//    }

    @Test
    void testUpdateDentist_Success() {
        Dentist updatedDentist = new Dentist();
        updatedDentist.setId(1L);
        updatedDentist.setDentistName("Updated");

        when(dentistRepository.findById(1L)).thenReturn(Optional.of(dentist));
        when(dentistMapper.dentistRequestDtoToDentist(requestDto)).thenReturn(updatedDentist);
        when(dentistRepository.save(updatedDentist)).thenReturn(updatedDentist);
        when(dentistMapper.dentistToDentistResponseDto(updatedDentist)).thenReturn(responseDto);

        DentistResponseDto result = dentistService.updateDentist(1L, requestDto);

        assertNotNull(result);
        verify(dentistRepository).save(updatedDentist);
    }

    @Test
    void testUpdateDentist_NotFound() {
        when(dentistRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> dentistService.updateDentist(1L, requestDto));
    }

    @Test
    void testUpdateDentist_FailureOnSave() {
        when(dentistRepository.findById(1L)).thenReturn(Optional.of(dentist));
        when(dentistMapper.dentistRequestDtoToDentist(requestDto)).thenReturn(dentist);
        when(dentistRepository.save(any())).thenThrow(new RuntimeException("DB fail"));

        assertThrows(DataValidationException.class, () -> dentistService.updateDentist(1L, requestDto));
    }

    @Test
    void testDeleteDentist_Success() {
        when(dentistRepository.existsById(1L)).thenReturn(true);

        dentistService.deleteDentist(1L);

        verify(dentistRepository).deleteById(1L);
    }

    @Test
    void testDeleteDentist_NotFound() {
        when(dentistRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> dentistService.deleteDentist(1L));
    }
}

