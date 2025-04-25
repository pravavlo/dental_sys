package edu.miu.cs489.mapper;

import edu.miu.cs489.dto.request.DentistRequestDto;
import edu.miu.cs489.dto.response.DentistResponseDto;
import edu.miu.cs489.model.Dentist;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-25T21:45:51+0545",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Eclipse Adoptium)"
)
@Component
public class DentistMapperImpl implements DentistMapper {

    @Override
    public Dentist dentistRequestDtoToDentist(DentistRequestDto dentistRequestDto) {
        if ( dentistRequestDto == null ) {
            return null;
        }

        Dentist dentist = new Dentist();

        dentist.setDentistName( dentistRequestDto.getDentistName() );
        dentist.setDentistNumber( dentistRequestDto.getDentistNumber() );
        dentist.setSpecialization( dentistRequestDto.getSpecialization() );

        return dentist;
    }

    @Override
    public DentistRequestDto dentistToDentistRequestDto(Dentist dentist) {
        if ( dentist == null ) {
            return null;
        }

        DentistRequestDto dentistRequestDto = new DentistRequestDto();

        dentistRequestDto.setDentistName( dentist.getDentistName() );
        dentistRequestDto.setDentistNumber( dentist.getDentistNumber() );
        dentistRequestDto.setSpecialization( dentist.getSpecialization() );

        return dentistRequestDto;
    }

    @Override
    public DentistResponseDto dentistToDentistResponseDto(Dentist dentist) {
        if ( dentist == null ) {
            return null;
        }

        DentistResponseDto dentistResponseDto = new DentistResponseDto();

        dentistResponseDto.setId( dentist.getId() );
        dentistResponseDto.setDentistName( dentist.getDentistName() );
        dentistResponseDto.setDentistNumber( dentist.getDentistNumber() );
        dentistResponseDto.setSpecialization( dentist.getSpecialization() );

        return dentistResponseDto;
    }

    @Override
    public List<DentistResponseDto> dentistsToDentistResponseDtos(List<Dentist> dentists) {
        if ( dentists == null ) {
            return null;
        }

        List<DentistResponseDto> list = new ArrayList<DentistResponseDto>( dentists.size() );
        for ( Dentist dentist : dentists ) {
            list.add( dentistToDentistResponseDto( dentist ) );
        }

        return list;
    }
}
