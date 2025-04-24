package edu.miu.cs489.service;


import edu.miu.cs489.dto.request.AddressRequestDto;
import edu.miu.cs489.dto.response.AddressResponseDto;

import java.util.List;

public interface AddressService {
    AddressResponseDto createAddress(AddressRequestDto addressRequestDto);

    AddressResponseDto getAddressById(Long id);

    List<AddressResponseDto> getAllAddresses();

    AddressResponseDto updateAddress(Long id, AddressRequestDto addressRequestDto);

    void deleteAddress(Long id);
}
