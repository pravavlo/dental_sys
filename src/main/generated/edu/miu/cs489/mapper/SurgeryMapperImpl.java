package edu.miu.cs489.mapper;

import edu.miu.cs489.dto.request.SurgeryRequestDto;
import edu.miu.cs489.dto.response.SurgeryResponseDto;
import edu.miu.cs489.model.Surgery;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-24T13:58:27+0545",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Eclipse Adoptium)"
)
@Component
public class SurgeryMapperImpl implements SurgeryMapper {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Surgery surgeryRequestDtoToSurgery(SurgeryRequestDto surgeryRequestDto) {
        if ( surgeryRequestDto == null ) {
            return null;
        }

        Surgery surgery = new Surgery();

        surgery.setSurgeryNo( surgeryRequestDto.getSurgeryNo() );
        surgery.setAddress( addressMapper.addressRequestDtoToAddress( surgeryRequestDto.getAddress() ) );

        return surgery;
    }

    @Override
    public SurgeryResponseDto surgeryToSurgeryResponseDto(Surgery surgery) {
        if ( surgery == null ) {
            return null;
        }

        SurgeryResponseDto surgeryResponseDto = new SurgeryResponseDto();

        surgeryResponseDto.setId( surgery.getId() );
        surgeryResponseDto.setSurgeryNo( surgery.getSurgeryNo() );
        surgeryResponseDto.setAddress( addressMapper.addressToAddressResponseDto( surgery.getAddress() ) );

        return surgeryResponseDto;
    }

    @Override
    public List<SurgeryResponseDto> surgeriesToSurgeryResponseDtos(List<Surgery> surgeries) {
        if ( surgeries == null ) {
            return null;
        }

        List<SurgeryResponseDto> list = new ArrayList<SurgeryResponseDto>( surgeries.size() );
        for ( Surgery surgery : surgeries ) {
            list.add( surgeryToSurgeryResponseDto( surgery ) );
        }

        return list;
    }
}
