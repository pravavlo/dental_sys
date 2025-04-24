package edu.miu.cs489.mapper;


import edu.miu.cs489.dto.request.DentistRequestDto;
import edu.miu.cs489.dto.response.DentistResponseDto;
import edu.miu.cs489.model.Dentist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DentistMapper {
    Dentist dentistRequestDtoToDentist(DentistRequestDto dentistRequestDto);

    DentistRequestDto dentistToDentistRequestDto(Dentist dentist);

    DentistResponseDto dentistToDentistResponseDto(Dentist dentist);

    List<DentistResponseDto> dentistsToDentistResponseDtos(List<Dentist> dentists);
}
