package edu.miu.cs489.service.impl;


import edu.miu.cs489.dto.request.UserRequestDto;
import edu.miu.cs489.dto.response.UserResponseDto;
import edu.miu.cs489.exception.ResourceNotFoundException;
import edu.miu.cs489.mapper.UserMapper;
import edu.miu.cs489.model.Role;
//import edu.miu.cs489.model.RoleType;
import edu.miu.cs489.model.User;
//import edu.miu.cs489.repository.RoleRepository;
import edu.miu.cs489.repository.UserRepository;
import edu.miu.cs489.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.userToUserResponseDto(savedUser);
    }

//    @Override
//    @Transactional
//    public UserResponseDto createUser(UserRequestDto userRequestDto) {
//        User user = userMapper.toEntity(userRequestDto);
//
////        if (userRequestDto.getRole() != null && !userRequestDto.getRole().isEmpty()) {
////            RoleType roleType;
////            try {
////                roleType = RoleType.valueOf(userRequestDto.getRole());
////            } catch (IllegalArgumentException e) {
////                throw new ResourceNotFoundException("Invalid role: " + userRequestDto.getRole());
////            }
////
////            Role role = roleRepository.findByName(roleType)
////                                      .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleType.name()));
////            user.setRole(role);
////        } else {
////            throw new ResourceNotFoundException("Role must be provided");
////        }
//
//        User savedUser = userRepository.save(user);
//        return userMapper.userToUserResponseDto(savedUser);
//    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User existingUser = userRepository.findById(id)
                                          .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        existingUser.setUsername(userRequestDto.getUsername());

        if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isEmpty()) {
            existingUser.setPassword(userRequestDto.getPassword());
        }

//        if (userRequestDto.getRole() != null && !userRequestDto.getRole().isEmpty()) {
//            RoleType roleType;
//            try {
//                roleType = RoleType.valueOf(userRequestDto.getRole());
//            } catch (IllegalArgumentException e) {
//                throw new ResourceNotFoundException("Invalid role: " + userRequestDto.getRole());
//            }
//
//            Role role = roleRepository.findByName(roleType)
//                                      .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleType.name()));
//            existingUser.setRole(role);
//        }

        User savedUser = userRepository.save(existingUser);
        return userMapper.userToUserResponseDto(savedUser);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                             .stream()
                             .map(userMapper::userToUserResponseDto)
                             .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }


}
