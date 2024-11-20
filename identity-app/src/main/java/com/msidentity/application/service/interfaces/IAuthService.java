package com.msidentity.application.service.interfaces;

import com.msidentity.application.dto.AuthRequest;
import com.msidentity.application.dto.AuthResponse;
import com.msidentity.application.dto.CreateUserRequest;

public interface IAuthService {
    AuthResponse login(AuthRequest authRequest);
    AuthResponse signup(CreateUserRequest createUserRequest);
    String generateToken(String username);
}
