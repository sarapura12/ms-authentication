package com.msidentity.application.service.impl;

import com.msidentity.application.dto.AuthRequest;
import com.msidentity.application.dto.AuthResponse;
import com.msidentity.application.dto.CreateUserRequest;
import com.msidentity.application.entity.ApplicationUser;
import com.msidentity.application.repository.ApplicationUserRepository;
import com.msidentity.application.service.interfaces.IAuthService;
import com.msidentity.application.utils.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (!authenticate.isAuthenticated())
            throw new AuthenticationServiceException("invalid access");

        var token = generateToken(authRequest.getUsername());
        return new AuthResponse(token, authRequest.getUsername());
    }

    @Override
    public AuthResponse signup(CreateUserRequest createUserRequest) {
        var applicationUser = new ApplicationUser();
        applicationUser.setName(createUserRequest.getName());
        applicationUser.setEmail(createUserRequest.getEmail());
        applicationUser.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        applicationUserRepository.save(applicationUser);

        return new AuthResponse(generateToken(applicationUser.getEmail()), applicationUser.getEmail());
    }

    @Override
    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

}
