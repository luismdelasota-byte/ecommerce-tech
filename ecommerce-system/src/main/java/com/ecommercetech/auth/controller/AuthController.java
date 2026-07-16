package com.ecommercetech.auth.controller;

import com.ecommercetech.auth.dto.AuthResponseDTO;
import com.ecommercetech.auth.dto.LoginRequestDTO;
import com.ecommercetech.auth.dto.RegisterRequestDTO;
import com.ecommercetech.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Endpoint de login
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) {
        AuthResponseDTO response = authService.login(requestDTO);
        return ResponseEntity.ok(response);
    }

    // Endpoint de registro
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO) {
        AuthResponseDTO response = authService.register(requestDTO);
        return ResponseEntity.ok(response);
    }
}
