package edu.miu.cs489.ServiceTest;


import edu.miu.cs489.dto.request.SurgeryRequestDto;
import edu.miu.cs489.dto.response.SurgeryResponseDto;
import edu.miu.cs489.exception.ResourceNotFoundException;
import edu.miu.cs489.mapper.SurgeryMapper;
import edu.miu.cs489.model.Surgery;
import edu.miu.cs489.repository.SurgeryRepository;
import edu.miu.cs489.service.impl.SurgeryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SurgeryServiceImplTest {

    private SurgeryRepository surgeryRepository;
    private SurgeryMapper surgeryMapper;
    private SurgeryServiceImpl surgeryService;

    @BeforeEach
    void setUp() {
        surgeryRepository = mock(SurgeryRepository.class);
        surgeryMapper = mock(SurgeryMapper.class);
        surgeryService = new SurgeryServiceImpl(surgeryRepository, surgeryMapper);
    }

    @Test
    void testCreateSurgery_Success() {
        SurgeryRequestDto requestDto = new SurgeryRequestDto();
        Surgery surgery = new Surgery();
        Surgery savedSurgery = new Surgery();
        SurgeryResponseDto responseDto = new SurgeryResponseDto();

        when(surgeryMapper.surgeryRequestDtoToSurgery(requestDto)).thenReturn(surgery);
        when(surgeryRepository.save(surgery)).thenReturn(savedSurgery);
        when(surgeryMapper.surgeryToSurgeryResponseDto(savedSurgery)).thenReturn(responseDto);

        SurgeryResponseDto result = surgeryService.createSurgery(requestDto);

        assertNotNull(result);
        verify(surgeryRepository).save(surgery);
    }

    @Test
    void testGetSurgeryById_Success() {
        Long id = 1L;
        Surgery surgery = new Surgery();
        SurgeryResponseDto responseDto = new SurgeryResponseDto();

        when(surgeryRepository.findById(id)).thenReturn(Optional.of(surgery));
        when(surgeryMapper.surgeryToSurgeryResponseDto(surgery)).thenReturn(responseDto);

        SurgeryResponseDto result = surgeryService.getSurgeryById(id);

        assertNotNull(result);
        verify(surgeryRepository).findById(id);
    }

    @Test
    void testGetSurgeryById_NotFound() {
        Long id = 1L;
        when(surgeryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> surgeryService.getSurgeryById(id));
    }

    @Test
    void testGetSurgeryBySurgeryNo_Success() {
        String surgeryNo = "S123";
        Surgery surgery = new Surgery();
        SurgeryResponseDto responseDto = new SurgeryResponseDto();

        when(surgeryRepository.findBySurgeryNo(surgeryNo)).thenReturn(Optional.of(surgery));
        when(surgeryMapper.surgeryToSurgeryResponseDto(surgery)).thenReturn(responseDto);

        SurgeryResponseDto result = surgeryService.getSurgeryBySurgeryNo(surgeryNo);

        assertNotNull(result);
        verify(surgeryRepository).findBySurgeryNo(surgeryNo);
    }

    @Test
    void testGetSurgeryBySurgeryNo_NotFound() {
        String surgeryNo = "S123";
        when(surgeryRepository.findBySurgeryNo(surgeryNo)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> surgeryService.getSurgeryBySurgeryNo(surgeryNo));
    }

    @Test
    void testGetAllSurgeries_Success() {
        List<Surgery> surgeries = Arrays.asList(new Surgery(), new Surgery());
        List<SurgeryResponseDto> responseDtos = Arrays.asList(new SurgeryResponseDto(), new SurgeryResponseDto());

        when(surgeryRepository.findAll()).thenReturn(surgeries);
        when(surgeryMapper.surgeriesToSurgeryResponseDtos(surgeries)).thenReturn(responseDtos);

        List<SurgeryResponseDto> result = surgeryService.getAllSurgeries();

        assertEquals(2, result.size());
        verify(surgeryRepository).findAll();
    }

    @Test
    void testUpdateSurgery_Success() {
        Long id = 1L;
        SurgeryRequestDto requestDto = new SurgeryRequestDto();
        Surgery existingSurgery = new Surgery();
        Surgery updatedSurgery = new Surgery();
        Surgery savedSurgery = new Surgery();
        SurgeryResponseDto responseDto = new SurgeryResponseDto();

        when(surgeryRepository.findById(id)).thenReturn(Optional.of(existingSurgery));
        when(surgeryMapper.surgeryRequestDtoToSurgery(requestDto)).thenReturn(updatedSurgery);
        when(surgeryRepository.save(updatedSurgery)).thenReturn(savedSurgery);
        when(surgeryMapper.surgeryToSurgeryResponseDto(savedSurgery)).thenReturn(responseDto);

        SurgeryResponseDto result = surgeryService.updateSurgery(id, requestDto);

        assertNotNull(result);
        verify(surgeryRepository).save(updatedSurgery);
    }

    @Test
    void testUpdateSurgery_NotFound() {
        Long id = 1L;
        SurgeryRequestDto requestDto = new SurgeryRequestDto();

        when(surgeryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> surgeryService.updateSurgery(id, requestDto));
    }

    @Test
    void testDeleteSurgery_Success() {
        Long id = 1L;
        when(surgeryRepository.existsById(id)).thenReturn(true);

        surgeryService.deleteSurgery(id);

        verify(surgeryRepository).deleteById(id);
    }

    @Test
    void testDeleteSurgery_NotFound() {
        Long id = 1L;
        when(surgeryRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> surgeryService.deleteSurgery(id));
    }
}
