package com.platformcommons.sms.controller;

import com.platformcommons.sms.dto.AdminLoginRequest;
import com.platformcommons.sms.dto.AuthResponse;
import com.platformcommons.sms.dto.StudentValidateRequest;
import com.platformcommons.sms.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/admin/login")
    public ResponseEntity<AuthResponse> adminLogin(@Valid @RequestBody AdminLoginRequest request) {
        return ResponseEntity.ok(authService.loginAdmin(request));
    }

    @PostMapping("/student/validate")
    public ResponseEntity<AuthResponse> validateStudent(@Valid @RequestBody StudentValidateRequest request) {
        return ResponseEntity.ok(authService.validateStudent(request));
    }
}
