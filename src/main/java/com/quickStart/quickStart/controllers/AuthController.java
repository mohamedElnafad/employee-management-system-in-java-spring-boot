package com.quickStart.quickStart.controllers;

import com.quickStart.quickStart.DTOs.LoginRequest;
import com.quickStart.quickStart.DTOs.LoginResponse;
import com.quickStart.quickStart.DTOs.SignupRequest;
import com.quickStart.quickStart.entities.UserAccount;
import com.quickStart.quickStart.exceptions.GlobalResponse;
import com.quickStart.quickStart.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<GlobalResponse<UserAccount>> signup(@Valid @RequestBody SignupRequest request) {
        UserAccount userAccount = authService.signup(request);
        return new ResponseEntity<>(new GlobalResponse<>(userAccount), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return new ResponseEntity<>(new GlobalResponse<>(response), HttpStatus.CREATED);
    }
}

