package com.quickStart.quickStart.service;

import com.quickStart.quickStart.DTOs.LoginRequest;
import com.quickStart.quickStart.DTOs.LoginResponse;
import com.quickStart.quickStart.DTOs.SignupRequest;
import com.quickStart.quickStart.entities.Employee;
import com.quickStart.quickStart.entities.UserAccount;
import com.quickStart.quickStart.jwtSecurity.JwtService;
import com.quickStart.quickStart.repositories.EmployeeRepo;
import com.quickStart.quickStart.repositories.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UserAccountRepo userAccountRepo;
    private final EmployeeRepo employeeRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public AuthService(UserAccountRepo userAccountRepo,
                       EmployeeRepo employeeRepo,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.userAccountRepo = userAccountRepo;
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public UserAccount signup(SignupRequest request) {
        if (userAccountRepo.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username '" + request.username() + "' is already taken.");
        }

        Employee employee = employeeRepo.findById(request.employeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + request.employeeId()));

        UserAccount account = new UserAccount();
        account.setUsername(request.username());
        account.setPassword(passwordEncoder.encode(request.password()));
        account.setEmployee(employee);

        return userAccountRepo.save(account);
    }

    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        UserAccount account = userAccountRepo.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + request.username()));

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", account.getRole());
//        return jwtService.generateToken(account.getUsername());
        return new LoginResponse(account.getId(), jwtService.generateToken(claims, account.getUsername()), account.getRole());
    }
}
