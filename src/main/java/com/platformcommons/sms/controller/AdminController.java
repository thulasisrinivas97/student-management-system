package com.platformcommons.sms.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.platformcommons.sms.dto.CourseRequest;
import com.platformcommons.sms.dto.CourseResponse;
import com.platformcommons.sms.dto.StudentRequest;
import com.platformcommons.sms.dto.StudentResponse;
import com.platformcommons.sms.service.CourseService;
import com.platformcommons.sms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * All endpoints here require a valid JWT with role ADMIN
 * (see SecurityConfig: /api/admin/** -> hasRole("ADMIN")).
 */
@RestController
@RequestMapping("/api/admin")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final StudentService studentService;
    private final CourseService courseService;

    public AdminController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @PostMapping("/students")
    public ResponseEntity<StudentResponse> admitStudent(@Valid @RequestBody StudentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.admitStudent(request));
    }

    @GetMapping("/students/search")
    public ResponseEntity<List<StudentResponse>> searchStudentsByName(@RequestParam String name) {
        return ResponseEntity.ok(studentService.searchStudentsByName(name));
    }

    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<StudentResponse>> getStudentsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(studentService.getStudentsByCourse(courseId));
    }

    @PostMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<StudentResponse> assignCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        return ResponseEntity.ok(studentService.assignCourseToStudent(studentId, courseId));
    }

    @PostMapping("/courses")
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(request));
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }
}
