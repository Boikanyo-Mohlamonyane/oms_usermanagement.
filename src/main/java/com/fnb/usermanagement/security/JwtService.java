package com.fnb.usermanagement.security;


import com.fnb.usermanagement.model.User;

public interface JwtService {

    String generateToken(User user);

    boolean validateToken(String token,String email);

    String extractEmailFromToken(String token);
}
