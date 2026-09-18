package com.fnb.usermanagement.service;

import com.fnb.usermanagement.dto.RegisterRequest;
import com.fnb.usermanagement.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
}
