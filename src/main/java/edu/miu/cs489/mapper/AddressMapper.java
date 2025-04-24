package edu.miu.cs489.mapper;


import edu.miu.cs489.dto.request.AddressRequestDto;
import edu.miu.cs489.dto.response.AddressResponseDto;
import edu.miu.cs489.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
    Address addressRequestDtoToAddress(AddressRequestDto addressRequestDto);

    AddressRequestDto addressToAddressRequestDto(Address address);

    AddressResponseDto addressToAddressResponseDto(Address address);

    List<AddressResponseDto> addressesToAddressResponseDtos(List<Address> addresses);
}
