package edu.miu.cs489.mapper;

import edu.miu.cs489.dto.request.AddressRequestDto;
import edu.miu.cs489.dto.response.AddressResponseDto;
import edu.miu.cs489.model.Address;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-24T13:58:27+0545",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Eclipse Adoptium)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address addressRequestDtoToAddress(AddressRequestDto addressRequestDto) {
        if ( addressRequestDto == null ) {
            return null;
        }

        Address address = new Address();

        address.setStreet( addressRequestDto.getStreet() );
        address.setCity( addressRequestDto.getCity() );
        address.setState( addressRequestDto.getState() );
        address.setZipCode( addressRequestDto.getZipCode() );
        address.setCountry( addressRequestDto.getCountry() );

        return address;
    }

    @Override
    public AddressRequestDto addressToAddressRequestDto(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressRequestDto addressRequestDto = new AddressRequestDto();

        addressRequestDto.setStreet( address.getStreet() );
        addressRequestDto.setCity( address.getCity() );
        addressRequestDto.setState( address.getState() );
        addressRequestDto.setZipCode( address.getZipCode() );
        addressRequestDto.setCountry( address.getCountry() );

        return addressRequestDto;
    }

    @Override
    public AddressResponseDto addressToAddressResponseDto(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressResponseDto addressResponseDto = new AddressResponseDto();

        addressResponseDto.setId( address.getId() );
        addressResponseDto.setStreet( address.getStreet() );
        addressResponseDto.setCity( address.getCity() );
        addressResponseDto.setState( address.getState() );
        addressResponseDto.setZipCode( address.getZipCode() );
        addressResponseDto.setCountry( address.getCountry() );

        return addressResponseDto;
    }

    @Override
    public List<AddressResponseDto> addressesToAddressResponseDtos(List<Address> addresses) {
        if ( addresses == null ) {
            return null;
        }

        List<AddressResponseDto> list = new ArrayList<AddressResponseDto>( addresses.size() );
        for ( Address address : addresses ) {
            list.add( addressToAddressResponseDto( address ) );
        }

        return list;
    }
}
