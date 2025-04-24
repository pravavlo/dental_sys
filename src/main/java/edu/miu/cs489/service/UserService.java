
package edu.miu.cs489.service;


import edu.miu.cs489.dto.request.UserRequestDto;
import edu.miu.cs489.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);
    UserResponseDto getUserById(Long id);
    UserResponseDto getUserByUsername(String username);
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);
    void deleteUser(Long id);
}
