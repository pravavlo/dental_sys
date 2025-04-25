package edu.miu.cs489.controllerTest;


import edu.miu.cs489.controller.AuthController;
import edu.miu.cs489.dto.request.UserRequestDto;
import edu.miu.cs489.dto.response.UserResponseDto;
import edu.miu.cs489.model.Role;
import edu.miu.cs489.security.JwtUtil;
import edu.miu.cs489.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authenticationManager = mock(AuthenticationManager.class);
        jwtUtil = mock(JwtUtil.class);

        authController = new AuthController(userService, passwordEncoder, authenticationManager, jwtUtil);
    }

    private UserRequestDto createSignupDto() {
        return new UserRequestDto("john_doe", "password123", Role.PATIENT);
    }

    private UserResponseDto createUserResponseDto() {
        return new UserResponseDto(1L, "john_doe", Role.PATIENT);
    }

    @Test
    void testSignup_returnsCreatedUser() {
        UserRequestDto requestDto = createSignupDto();
        UserResponseDto expectedResponse = createUserResponseDto();

        when(userService.createUser(requestDto)).thenReturn(expectedResponse);

        ResponseEntity<UserResponseDto> response = authController.signup(requestDto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testLogin_returnsJwtToken() {
        UserRequestDto loginDto = new UserRequestDto("john_doe", "password123", Role.PATIENT);

        UserDetails mockUserDetails = mock(UserDetails.class);
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(mockUserDetails);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);

        when(jwtUtil.generateToken(mockUserDetails)).thenReturn("mock-jwt-token");

        String jwt = authController.login(loginDto);

        assertEquals("mock-jwt-token", jwt);
    }
}