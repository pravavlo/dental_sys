package edu.miu.cs489.mapper;


import edu.miu.cs489.dto.request.UserRequestDto;
import edu.miu.cs489.dto.response.UserResponseDto;
import edu.miu.cs489.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Entity to DTO conversion
    UserResponseDto userToUserResponseDto(User user);

    // DTO to Entity conversion for creating new user
    User toEntity(UserRequestDto userRequestDto);

    // Update existing user from DTO
    void updateUserFromDto(UserRequestDto userRequestDto, @MappingTarget User user);

    // List mapping
    List<UserResponseDto> toResponseDtoList(List<User> users);
}

