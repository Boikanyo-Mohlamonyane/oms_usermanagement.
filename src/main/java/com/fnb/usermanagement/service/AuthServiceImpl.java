package com.fnb.usermanagement.service;

import com.fnb.usermanagement.dto.RegisterRequest;
import com.fnb.usermanagement.dto.RegisterResponse;
import com.fnb.usermanagement.dto.LoginRequest;
import com.fnb.usermanagement.dto.LoginResponse;
import com.fnb.usermanagement.enums.Role;
import jakarta.transaction.Transactional;
import com.fnb.usermanagement.model.Credentials;
import com.fnb.usermanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fnb.usermanagement.repository.UserCredentialRepository;
import com.fnb.usermanagement.repository.UserRepository;
import com.fnb.usermanagement.security.securityImpl.JwtServiceImpl;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtServiceImpl jwtService;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .first_name(registerRequest.getFirst_name())
                .surname(registerRequest.getSurname())
                .email(registerRequest.getEmail())
                .role(Role.CUSTOMER)
                .build();

        user = userRepository.save(user);

        Credentials credentials = Credentials.builder()
                .user(user)
                .password_hash(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        userCredentialRepository.save(credentials);

        return toResponse(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        // Authenticate user credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        // Load user from DB
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate JWT token
        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .customerId(user.getCustomer_id().getMostSignificantBits()) // adjust if UUID
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    private RegisterResponse toResponse(User user) {
        return RegisterResponse.builder()
                .customerId(user.getCustomer_id())
                .firstName(user.getFirst_name())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
