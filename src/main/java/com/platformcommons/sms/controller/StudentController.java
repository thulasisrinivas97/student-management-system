package com.platformcommons.sms.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.platformcommons.sms.dto.StudentProfileUpdateRequest;
import com.platformcommons.sms.dto.StudentResponse;
import com.platformcommons.sms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * All endpoints here require a valid JWT with role STUDENT
 * (see SecurityConfig: /api/students/me/** -> hasRole("STUDENT")).
 * The student's identity (studentCode) comes from the JWT subject set
 * during /api/auth/student/validate - never from a path/query parameter,
 * so a student can only ever act on their own record.
 */
@RestController
@RequestMapping("/api/students/me")
@SecurityRequirement(name = "bearerAuth")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/profile")
    public ResponseEntity<StudentResponse> getOwnProfile(Authentication authentication) {
        String studentCode = authentication.getName();
        return ResponseEntity.ok(studentService.getOwnProfile(studentCode));
    }

    @PutMapping("/profile")
    public ResponseEntity<StudentResponse> updateOwnProfile(Authentication authentication,
                                                              @Valid @RequestBody StudentProfileUpdateRequest request) {
        String studentCode = authentication.getName();
        return ResponseEntity.ok(studentService.updateOwnProfile(studentCode, request));
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<StudentResponse> leaveCourse(Authentication authentication, @PathVariable Long courseId) {
        String studentCode = authentication.getName();
        return ResponseEntity.ok(studentService.leaveCourse(studentCode, courseId));
    }
}
