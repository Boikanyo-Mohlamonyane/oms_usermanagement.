package service;

import dto.RegisterRequest;
import dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
}
