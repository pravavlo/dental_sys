package edu.miu.cs489.service;


import edu.miu.cs489.dto.request.SurgeryRequestDto;
import edu.miu.cs489.dto.response.SurgeryResponseDto;

import java.util.List;

public interface SurgeryService {
    SurgeryResponseDto createSurgery(SurgeryRequestDto surgeryRequestDto);

    SurgeryResponseDto getSurgeryById(Long id);

    SurgeryResponseDto getSurgeryBySurgeryNo(String surgeryNo);

    List<SurgeryResponseDto> getAllSurgeries();

    SurgeryResponseDto updateSurgery(Long id, SurgeryRequestDto surgeryRequestDto);

    void deleteSurgery(Long id);
}
