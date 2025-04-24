package edu.miu.cs489.mapper;


import edu.miu.cs489.dto.request.SurgeryRequestDto;
import edu.miu.cs489.dto.response.SurgeryResponseDto;
import edu.miu.cs489.model.Surgery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AddressMapper.class})
public interface SurgeryMapper {
    Surgery surgeryRequestDtoToSurgery(SurgeryRequestDto surgeryRequestDto);

    SurgeryRequestDto surgeryToSurgeryRequestDto(Surgery surgery);

    SurgeryResponseDto surgeryToSurgeryResponseDto(Surgery surgery);

    List<SurgeryResponseDto> surgeriesToSurgeryResponseDtos(List<Surgery> surgeries);
}
