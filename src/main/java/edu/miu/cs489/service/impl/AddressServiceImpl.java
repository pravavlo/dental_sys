package edu.miu.cs489.service.impl;


import edu.miu.cs489.dto.request.AddressRequestDto;
import edu.miu.cs489.dto.response.AddressResponseDto;
import edu.miu.cs489.exception.ResourceNotFoundException;
import edu.miu.cs489.mapper.AddressMapper;
import edu.miu.cs489.model.Address;
import edu.miu.cs489.repository.AddressRepository;
import edu.miu.cs489.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public AddressResponseDto createAddress(AddressRequestDto addressRequestDto) {
        Address address = addressMapper.addressRequestDtoToAddress(addressRequestDto);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.addressToAddressResponseDto(savedAddress);
    }

    @Override
    public AddressResponseDto getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                                           .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
        return addressMapper.addressToAddressResponseDto(address);
    }

    @Override
    public List<AddressResponseDto> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addressMapper.addressesToAddressResponseDtos(addresses);
    }

    @Override
    public AddressResponseDto updateAddress(Long id, AddressRequestDto addressRequestDto) {
        Address existingAddress = addressRepository.findById(id)
                                                   .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));

        Address updatedAddress = addressMapper.addressRequestDtoToAddress(addressRequestDto);
        updatedAddress.setId(existingAddress.getId());

        Address savedAddress = addressRepository.save(updatedAddress);
        return addressMapper.addressToAddressResponseDto(savedAddress);
    }

    @Override
    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
        addressRepository.deleteById(id);
    }
}
