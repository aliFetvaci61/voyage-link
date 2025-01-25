package com.alifetvaci.voyagelink.authservice.service;


import com.alifetvaci.voyagelink.authservice.api.exception.ErrorCode;
import com.alifetvaci.voyagelink.authservice.api.exception.GenericException;
import com.alifetvaci.voyagelink.authservice.controller.request.LoginRequest;
import com.alifetvaci.voyagelink.authservice.controller.request.RegisterRequest;
import com.alifetvaci.voyagelink.authservice.controller.response.LoginResponse;
import com.alifetvaci.voyagelink.authservice.redis.model.Session;
import com.alifetvaci.voyagelink.authservice.repository.UserRepository;
import com.alifetvaci.voyagelink.authservice.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final SessionService sessionService;


    private final JwtService jwtService;

    public String createToken(User user) {
        // Generate a unique session ID
        String sessionId = UUID.randomUUID().toString();

        // Create a Session object with user information
        Session session = Session.builder()
                .identificationNumber(user.getIdentificationNumber())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build();

        // Store the session in Redis with a 5-minute expiration
        sessionService.putValue(sessionId, TimeUnit.MINUTES, 5, session);

        // Build the JWT token with the session ID as the subject
        return jwtService.buildToken(sessionId);

    }


    public void register(RegisterRequest request) {
        Optional<User> userOptional = userRepository.findByIdentificationNumber(request.getIdentificationNumber());
                if (userOptional.isPresent()) {
                    throw GenericException.builder()
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .logMessage(this.getClass().getName() + ".register user already exists with identification number {0}", request.getIdentificationNumber())
                            //.message(ErrorCode.USER_ALREADY_EXISTS)
                            .build();
                }
        User user = User.builder().identificationNumber(request.getIdentificationNumber()).firstname(request.getFirstName()).lastname(request.getLastName()).password(passwordEncoder.encode(request.getPassword())).build();
        userRepository.save(user);
    }


    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByIdentificationNumber(request.getIdentificationNumber()).orElseThrow(() ->GenericException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .logMessage(this.getClass().getName() + ".login user not found with identification number {0}", request.getIdentificationNumber() )
                //.message(ErrorCode.USER_NOT_FOUND)
                .build());
        passwordEncoder.matches(request.getPassword(),user.getPassword());
        return LoginResponse.builder().token(createToken(user)).build();
    }
}
