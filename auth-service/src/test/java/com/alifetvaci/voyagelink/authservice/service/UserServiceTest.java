package com.alifetvaci.voyagelink.authservice.service;

import com.alifetvaci.voyagelink.authservice.controller.request.LoginRequest;
import com.alifetvaci.voyagelink.authservice.controller.request.RegisterRequest;
import com.alifetvaci.voyagelink.authservice.controller.response.LoginResponse;
import com.alifetvaci.voyagelink.authservice.repository.UserRepository;
import com.alifetvaci.voyagelink.authservice.repository.model.User;
import com.alifetvaci.voyagelink.authservice.api.exception.GenericException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        registerRequest = new RegisterRequest("12345", "John", "Doe", "password123");
        loginRequest = new LoginRequest("12345", "password123");
        user = new User("12345", "John", "Doe", "encodedpassword");
    }

    @Test
    void testRegister_UserAlreadyExists() {
        when(userRepository.findByIdentificationNumber(registerRequest.getIdentificationNumber()))
                .thenReturn(Optional.of(user));

        GenericException exception = org.junit.jupiter.api.Assertions.assertThrows(GenericException.class, () -> {
            userService.register(registerRequest);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testRegister_UserSavedSuccessfully() {
        when(userRepository.findByIdentificationNumber(registerRequest.getIdentificationNumber()))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedpassword");

        userService.register(registerRequest);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testLogin_UserNotFound() {
        when(userRepository.findByIdentificationNumber(loginRequest.getIdentificationNumber()))
                .thenReturn(Optional.empty());

        GenericException exception = org.junit.jupiter.api.Assertions.assertThrows(GenericException.class, () -> {
            userService.login(loginRequest);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testLogin_Success() {
        when(userRepository.findByIdentificationNumber(loginRequest.getIdentificationNumber()))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq(loginRequest.getPassword()), eq(user.getPassword()))).thenReturn(true);
        when(jwtService.buildToken(any())).thenReturn("generatedToken");

        LoginResponse response = userService.login(loginRequest);

        assertNotNull(response);
        assertEquals("generatedToken", response.getToken());
    }

}
