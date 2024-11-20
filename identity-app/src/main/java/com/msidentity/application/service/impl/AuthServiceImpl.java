package com.msidentity.application.service.impl;

import com.msidentity.application.dto.AuthRequest;
import com.msidentity.application.dto.AuthResponse;
import com.msidentity.application.dto.CreateUserRequest;
import com.msidentity.application.entity.ApplicationUser;
import com.msidentity.application.repository.ApplicationUserRepository;
import com.msidentity.application.service.interfaces.IAuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        var user = new ApplicationUser();
        user.setName(authRequest.getUsername());
        user.setEmail(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        applicationUserRepository.save(user);
        return null;
    }

    @Override
    public AuthResponse signup(CreateUserRequest createUserRequest) {
        return null;
    }
}
