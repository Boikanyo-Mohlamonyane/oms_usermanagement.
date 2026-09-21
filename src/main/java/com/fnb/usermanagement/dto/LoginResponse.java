package com.fnb.usermanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LoginResponse {

    private String token;
    private UUID customerId;
    private String email;
    private String role;
}