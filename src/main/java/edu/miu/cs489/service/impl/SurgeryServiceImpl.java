package edu.miu.cs489.service.impl;

import edu.miu.cs489.dto.request.SurgeryRequestDto;
import edu.miu.cs489.dto.response.SurgeryResponseDto;
import edu.miu.cs489.exception.ResourceNotFoundException;
import edu.miu.cs489.mapper.SurgeryMapper;
import edu.miu.cs489.model.Surgery;
import edu.miu.cs489.repository.SurgeryRepository;
import edu.miu.cs489.service.SurgeryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurgeryServiceImpl implements SurgeryService {

    private final SurgeryRepository surgeryRepository;
    private final SurgeryMapper surgeryMapper;

    @Override
    public SurgeryResponseDto createSurgery(SurgeryRequestDto surgeryRequestDto) {
        Surgery surgery = surgeryMapper.surgeryRequestDtoToSurgery(surgeryRequestDto);
        Surgery savedSurgery = surgeryRepository.save(surgery);
        return surgeryMapper.surgeryToSurgeryResponseDto(savedSurgery);
    }

    @Override
    public SurgeryResponseDto getSurgeryById(Long id) {
        Surgery surgery = surgeryRepository.findById(id)
                                           .orElseThrow(() -> new ResourceNotFoundException("Surgery not found with id: " + id));
        return surgeryMapper.surgeryToSurgeryResponseDto(surgery);
    }

    @Override
    public SurgeryResponseDto getSurgeryBySurgeryNo(String surgeryNo) {
        Surgery surgery = surgeryRepository.findBySurgeryNo(surgeryNo)
                                           .orElseThrow(() -> new ResourceNotFoundException("Surgery not found with surgery number: " + surgeryNo));
        return surgeryMapper.surgeryToSurgeryResponseDto(surgery);
    }

    @Override
    public List<SurgeryResponseDto> getAllSurgeries() {
        List<Surgery> surgeries = surgeryRepository.findAll();
        return surgeryMapper.surgeriesToSurgeryResponseDtos(surgeries);
    }

    @Override
    public SurgeryResponseDto updateSurgery(Long id, SurgeryRequestDto surgeryRequestDto) {
        Surgery existingSurgery = surgeryRepository.findById(id)
                                                   .orElseThrow(() -> new ResourceNotFoundException("Surgery not found with id: " + id));

        Surgery updatedSurgery = surgeryMapper.surgeryRequestDtoToSurgery(surgeryRequestDto);
        updatedSurgery.setId(existingSurgery.getId());

        Surgery savedSurgery = surgeryRepository.save(updatedSurgery);
        return surgeryMapper.surgeryToSurgeryResponseDto(savedSurgery);
    }

    @Override
    public void deleteSurgery(Long id) {
        if (!surgeryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Surgery not found with id: " + id);
        }
        surgeryRepository.deleteById(id);
    }
}
