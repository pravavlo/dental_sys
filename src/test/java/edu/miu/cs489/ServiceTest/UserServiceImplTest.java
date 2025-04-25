package edu.miu.cs489.ServiceTest;

import edu.miu.cs489.dto.request.UserRequestDto;
import edu.miu.cs489.dto.response.UserResponseDto;
import edu.miu.cs489.exception.ResourceNotFoundException;
import edu.miu.cs489.mapper.UserMapper;
import edu.miu.cs489.model.User;
import edu.miu.cs489.repository.UserRepository;
import edu.miu.cs489.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        UserRequestDto requestDto = new UserRequestDto("testuser", "password123", null);
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("testuser");
        UserResponseDto responseDto = new UserResponseDto(1L, "testuser", null);

        when(userMapper.toEntity(requestDto)).thenReturn(user);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.userToUserResponseDto(savedUser)).thenReturn(responseDto);

        UserResponseDto result = userService.createUser(requestDto);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_Success() {
        Long id = 1L;
        UserRequestDto requestDto = new UserRequestDto("updatedUser", "newPassword", null);
        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setUsername("oldUser");

        User savedUser = new User();
        savedUser.setId(id);
        savedUser.setUsername("updatedUser");
        savedUser.setPassword("newPassword");

        UserResponseDto responseDto = new UserResponseDto(id, "updatedUser", null);

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.userToUserResponseDto(savedUser)).thenReturn(responseDto);

        UserResponseDto result = userService.updateUser(id, requestDto);
        assertEquals("updatedUser", result.getUsername());
    }

    @Test
    void testGetUserById_Success() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        UserResponseDto responseDto = new UserResponseDto(1L, "testuser", null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponseDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.getUserById(1L);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testGetUserByUsername_Success() {
        User user = new User();
        user.setUsername("testuser");
        UserResponseDto responseDto = new UserResponseDto(1L, "testuser", null);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponseDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.getUserByUsername("testuser");
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testGetAllUsers_Success() {
        List<User> users = Arrays.asList(new User(1L, "user1", "pass1", null), new User(2L, "user2", "pass2", null));
        List<UserResponseDto> responseDtos = Arrays.asList(
                new UserResponseDto(1L, "user1", null),
                new UserResponseDto(2L, "user2", null)
        );

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.userToUserResponseDto(users.get(0))).thenReturn(responseDtos.get(0));
        when(userMapper.userToUserResponseDto(users.get(1))).thenReturn(responseDtos.get(1));

        List<UserResponseDto> results = userService.getAllUsers();
        assertEquals(2, results.size());
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
    }
}
