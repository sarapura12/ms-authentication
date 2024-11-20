package com.msidentity.application.controller;

import com.msidentity.application.dto.AuthRequest;
import com.msidentity.application.dto.AuthResponse;
import com.msidentity.application.dto.CreateUserRequest;
import com.msidentity.application.service.impl.AuthServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> token(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(authService.signup(createUserRequest));
    }
}
