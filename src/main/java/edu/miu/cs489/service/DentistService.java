package edu.miu.cs489.service;


import edu.miu.cs489.dto.request.DentistRequestDto;
import edu.miu.cs489.dto.response.DentistResponseDto;

import java.util.List;

public interface DentistService {
    DentistResponseDto createDentist(DentistRequestDto dentistRequestDto);

    DentistResponseDto getDentistById(Long id);

    DentistResponseDto getDentistByName(String name);

    List<DentistResponseDto> getAllDentists();

    DentistResponseDto updateDentist(Long id, DentistRequestDto dentistRequestDto);

    void deleteDentist(Long id);
}
