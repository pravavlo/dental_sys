package edu.miu.cs489.mapper;

import edu.miu.cs489.dto.request.UserRequestDto;
import edu.miu.cs489.dto.response.UserResponseDto;
import edu.miu.cs489.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-25T11:22:40+0545",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto userToUserResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId( user.getId() );
        userResponseDto.setUsername( user.getUsername() );
        userResponseDto.setRole( user.getRole() );

        return userResponseDto;
    }

    @Override
    public User toEntity(UserRequestDto userRequestDto) {
        if ( userRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userRequestDto.getUsername() );
        user.setPassword( userRequestDto.getPassword() );
        user.setRole( userRequestDto.getRole() );

        return user;
    }

    @Override
    public void updateUserFromDto(UserRequestDto userRequestDto, User user) {
        if ( userRequestDto == null ) {
            return;
        }

        user.setUsername( userRequestDto.getUsername() );
        user.setPassword( userRequestDto.getPassword() );
        user.setRole( userRequestDto.getRole() );
    }

    @Override
    public List<UserResponseDto> toResponseDtoList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserResponseDto> list = new ArrayList<UserResponseDto>( users.size() );
        for ( User user : users ) {
            list.add( userToUserResponseDto( user ) );
        }

        return list;
    }
}
