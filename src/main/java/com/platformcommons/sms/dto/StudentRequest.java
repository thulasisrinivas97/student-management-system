package com.platformcommons.sms.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StudentRequest {
    @NotBlank
    private String name;

    @NotNull
    @Past
    private LocalDate dateOfBirth;

    private String gender;

    @NotBlank
    private String studentCode;

    private String email;
    private String mobileNumber;
    private String parentsNames;

    @Valid
    private List<AddressDto> addresses;
}
