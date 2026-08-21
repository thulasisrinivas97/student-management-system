package com.platformcommons.sms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * Students authenticate with studentCode + dateOfBirth instead of a password,
 * per the assignment's "Student Validation" requirement.
 */
@Data
public class StudentValidateRequest {
    @NotBlank
    private String studentCode;
    @NotNull
    private LocalDate dateOfBirth;
}
