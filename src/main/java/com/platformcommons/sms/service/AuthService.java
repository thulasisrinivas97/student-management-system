package com.platformcommons.sms.service;

import com.platformcommons.sms.dto.AdminLoginRequest;
import com.platformcommons.sms.dto.AuthResponse;
import com.platformcommons.sms.dto.StudentValidateRequest;
import com.platformcommons.sms.entity.Admin;
import com.platformcommons.sms.entity.Student;
import com.platformcommons.sms.exception.ResourceNotFoundException;
import com.platformcommons.sms.repository.AdminRepository;
import com.platformcommons.sms.repository.StudentRepository;
import com.platformcommons.sms.security.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AdminRepository adminRepository,
                        StudentRepository studentRepository,
                        PasswordEncoder passwordEncoder,
                        JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse loginAdmin(AdminLoginRequest request) {
        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(admin.getUsername(), "ADMIN", Map.of());
        return new AuthResponse(token, "ADMIN");
    }

    public AuthResponse validateStudent(StudentValidateRequest request) {
        Student student = studentRepository
                .findByStudentCodeAndDateOfBirth(request.getStudentCode(), request.getDateOfBirth())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No student found matching that student code and date of birth"));

        String token = jwtUtil.generateToken(
                student.getStudentCode(),
                "STUDENT",
                Map.of("studentId", student.getId())
        );
        return new AuthResponse(token, "STUDENT");
    }
}
