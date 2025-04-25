package edu.miu.cs489.service;


import edu.miu.cs489.dto.request.DentistRequestDto;
import edu.miu.cs489.dto.response.DentistResponseDto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface DentistService {
    DentistResponseDto createDentist(DentistRequestDto dentistRequestDto);

    DentistResponseDto getDentistById(Long id);

    DentistResponseDto getDentistByName(String name);

    Page<DentistResponseDto> getAllDentists(Pageable pageable);

    DentistResponseDto updateDentist(Long id, DentistRequestDto dentistRequestDto);

    void deleteDentist(Long id);
}
