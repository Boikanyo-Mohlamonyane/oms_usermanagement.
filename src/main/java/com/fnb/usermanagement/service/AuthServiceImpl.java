package com.fnb.usermanagement.service;

import com.fnb.usermanagement.dto.RegisterRequest;
import com.fnb.usermanagement.dto.RegisterResponse;
import com.fnb.usermanagement.dto.LoginRequest;
import com.fnb.usermanagement.dto.LoginResponse;
import com.fnb.usermanagement.enums.Role;
import com.fnb.usermanagement.security.JwtService;
import jakarta.transaction.Transactional;
import com.fnb.usermanagement.model.Credentials;
import com.fnb.usermanagement.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fnb.usermanagement.repository.UserCredentialRepository;
import com.fnb.usermanagement.repository.UserRepository;
import com.fnb.usermanagement.security.securityImpl.JwtServiceImpl;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final UserCredentialRepository userCredentialsRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

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

        Credentials userCredential = Credentials.builder()
                .user(user)
                .password_hash(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        userCredentialsRepository.save(userCredential);

        return toUserResponse(user);

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        User user =  userRepository.findByEmail(loginRequest.getEmail());

        String token =  jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .customerId(user.getCustomerId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }


    private RegisterResponse toUserResponse(User user){
        return RegisterResponse.builder()
                .customerId(user.getCustomerId())
                .firstName(user.getFirst_name())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}