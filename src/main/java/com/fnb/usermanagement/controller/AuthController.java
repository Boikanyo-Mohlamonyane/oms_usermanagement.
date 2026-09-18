package com.fnb.usermanagement.controller;

import com.fnb.usermanagement.dto.LoginRequest;
import com.fnb.usermanagement.dto.LoginResponse;
import com.fnb.usermanagement.dto.RegisterRequest;
import com.fnb.usermanagement.dto.RegisterResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fnb.usermanagement.service.AuthServiceImpl;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor

public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")

    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request){


        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));


    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
