package edu.miu.cs489.service.impl;

import edu.miu.cs489.dto.request.DentistRequestDto;
import edu.miu.cs489.dto.response.DentistResponseDto;
import edu.miu.cs489.exception.ResourceNotFoundException;
import edu.miu.cs489.mapper.DentistMapper;
import edu.miu.cs489.model.Dentist;
import edu.miu.cs489.repository.DentistRepository;
import edu.miu.cs489.service.DentistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DentistServiceImpl implements DentistService {

    private final DentistRepository dentistRepository;
    private final DentistMapper dentistMapper;

    @Override
    public DentistResponseDto createDentist(DentistRequestDto dentistRequestDto) {
        Dentist dentist = dentistMapper.dentistRequestDtoToDentist(dentistRequestDto);
        Dentist savedDentist = dentistRepository.save(dentist);
        return dentistMapper.dentistToDentistResponseDto(savedDentist);
    }

    @Override
    public DentistResponseDto getDentistById(Long id) {
        Dentist dentist = dentistRepository.findById(id)
                                           .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + id));
        return dentistMapper.dentistToDentistResponseDto(dentist);
    }

    @Override
    public DentistResponseDto getDentistByName(String name) {
        Dentist dentist = dentistRepository.findByDentistName(name)
                                           .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with name: " + name));
        return dentistMapper.dentistToDentistResponseDto(dentist);
    }

    @Override
    public List<DentistResponseDto> getAllDentists() {
        List<Dentist> dentists = dentistRepository.findAll();
        return dentistMapper.dentistsToDentistResponseDtos(dentists);
    }

    @Override
    public DentistResponseDto updateDentist(Long id, DentistRequestDto dentistRequestDto) {
        Dentist existingDentist = dentistRepository.findById(id)
                                                   .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + id));

        Dentist updatedDentist = dentistMapper.dentistRequestDtoToDentist(dentistRequestDto);
        updatedDentist.setId(existingDentist.getId());

        Dentist savedDentist = dentistRepository.save(updatedDentist);
        return dentistMapper.dentistToDentistResponseDto(savedDentist);
    }

    @Override
    public void deleteDentist(Long id) {
        if (!dentistRepository.existsById(id)) {
            throw new ResourceNotFoundException("Dentist not found with id: " + id);
        }
        dentistRepository.deleteById(id);
    }
}
