package edu.miu.cs489.service.impl;

import edu.miu.cs489.dto.request.DentistRequestDto;
import edu.miu.cs489.dto.response.DentistResponseDto;
import edu.miu.cs489.exception.DataValidationException;
import edu.miu.cs489.exception.ResourceNotFoundException;
import edu.miu.cs489.mapper.DentistMapper;
import edu.miu.cs489.model.Dentist;
import edu.miu.cs489.repository.DentistRepository;
import edu.miu.cs489.service.DentistService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DentistServiceImpl implements DentistService {


        private final DentistRepository dentistRepository;
        private final DentistMapper dentistMapper;

        @Override
        @Transactional
        public DentistResponseDto createDentist(DentistRequestDto dentistRequestDto) {
            System.out.println(dentistRequestDto);

            Dentist dentist = dentistMapper.dentistRequestDtoToDentist(dentistRequestDto);
            System.out.println(",,,,,,,,,,,,,,,,,"+dentist);

            try {
                Dentist savedDentist = dentistRepository.save(dentist);
                return dentistMapper.dentistToDentistResponseDto(savedDentist);
            } catch (Exception e) {
                throw new DataValidationException("Error creating dentist: " + e.getMessage());
            }
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
    public Page<DentistResponseDto> getAllDentists(Pageable pageable) {
        Page<Dentist> dentistsPage = dentistRepository.findAll(pageable);

        List<DentistResponseDto> dtoList = dentistMapper.dentistsToDentistResponseDtos(dentistsPage.getContent());

        return new PageImpl<>(dtoList, pageable, dentistsPage.getTotalElements());
    }



    @Override
        @Transactional // Added for consistency and data integrity
        public DentistResponseDto updateDentist(Long id, DentistRequestDto dentistRequestDto) {
            Dentist existingDentist = dentistRepository.findById(id)
                                                       .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + id));

            Dentist updatedDentist = dentistMapper.dentistRequestDtoToDentist(dentistRequestDto);
            updatedDentist.setId(existingDentist.getId());

            try {
                Dentist savedDentist = dentistRepository.save(updatedDentist);
                return dentistMapper.dentistToDentistResponseDto(savedDentist);
            } catch (Exception e) {
                throw new DataValidationException("Error updating dentist: " + e.getMessage());
            }
        }

        @Override
        @Transactional
        public void deleteDentist(Long id) {
            if (!dentistRepository.existsById(id)) {
                throw new ResourceNotFoundException("Dentist not found with id: " + id);
            }
            dentistRepository.deleteById(id);
        }

}
