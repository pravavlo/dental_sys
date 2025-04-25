package edu.miu.cs489.ServiceTest;


import edu.miu.cs489.dto.request.AddressRequestDto;
import edu.miu.cs489.dto.response.AddressResponseDto;
import edu.miu.cs489.exception.ResourceNotFoundException;
import edu.miu.cs489.mapper.AddressMapper;
import edu.miu.cs489.model.Address;
import edu.miu.cs489.repository.AddressRepository;
import edu.miu.cs489.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAddress() {
        // Given
        AddressRequestDto requestDto = new AddressRequestDto();
        requestDto.setCity("Kathmandu");

        Address address = new Address();
        address.setCity("Kathmandu");

        Address savedAddress = new Address();
        savedAddress.setId(1L);
        savedAddress.setCity("Kathmandu");

        AddressResponseDto responseDto = new AddressResponseDto();
        responseDto.setId(1L);
        responseDto.setCity("Kathmandu");

        // When
        when(addressMapper.addressRequestDtoToAddress(requestDto)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(savedAddress);
        when(addressMapper.addressToAddressResponseDto(savedAddress)).thenReturn(responseDto);

        AddressResponseDto result = addressService.createAddress(requestDto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Kathmandu", result.getCity());

        verify(addressRepository).save(address);
        verify(addressMapper).addressRequestDtoToAddress(requestDto);
        verify(addressMapper).addressToAddressResponseDto(savedAddress);
    }

    @Test
    public void testGetAddressById_Found() {
        Address address = new Address();
        address.setId(1L);
        address.setCity("Lalitpur");

        AddressResponseDto responseDto = new AddressResponseDto();
        responseDto.setId(1L);
        responseDto.setCity("Lalitpur");

        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressMapper.addressToAddressResponseDto(address)).thenReturn(responseDto);

        AddressResponseDto result = addressService.getAddressById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Lalitpur", result.getCity());
    }

    @Test
    public void testGetAddressById_NotFound() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.getAddressById(1L));
    }

    @Test
    public void testGetAllAddresses() {
        Address a1 = new Address();
        Address a2 = new Address();
        List<Address> addressList = Arrays.asList(a1, a2);

        AddressResponseDto r1 = new AddressResponseDto();
        AddressResponseDto r2 = new AddressResponseDto();
        List<AddressResponseDto> dtoList = Arrays.asList(r1, r2);

        when(addressRepository.findAll()).thenReturn(addressList);
        when(addressMapper.addressesToAddressResponseDtos(addressList)).thenReturn(dtoList);

        List<AddressResponseDto> result = addressService.getAllAddresses();
        assertEquals(2, result.size());
    }

    @Test
    public void testUpdateAddress_Found() {
        AddressRequestDto requestDto = new AddressRequestDto();
        requestDto.setCity("Pokhara");

        Address existing = new Address();
        existing.setId(1L);

        Address toUpdate = new Address();
        toUpdate.setCity("Pokhara");

        Address saved = new Address();
        saved.setId(1L);
        saved.setCity("Pokhara");

        AddressResponseDto responseDto = new AddressResponseDto();
        responseDto.setId(1L);
        responseDto.setCity("Pokhara");

        when(addressRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(addressMapper.addressRequestDtoToAddress(requestDto)).thenReturn(toUpdate);
        when(addressRepository.save(toUpdate)).thenReturn(saved);
        when(addressMapper.addressToAddressResponseDto(saved)).thenReturn(responseDto);

        AddressResponseDto result = addressService.updateAddress(1L, requestDto);
        assertEquals("Pokhara", result.getCity());
    }

    @Test
    public void testUpdateAddress_NotFound() {
        AddressRequestDto requestDto = new AddressRequestDto();
        when(addressRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.updateAddress(99L, requestDto));
    }

    @Test
    public void testDeleteAddress_Found() {
        when(addressRepository.existsById(1L)).thenReturn(true);

        addressService.deleteAddress(1L);

        verify(addressRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAddress_NotFound() {
        when(addressRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> addressService.deleteAddress(1L));
    }
}
