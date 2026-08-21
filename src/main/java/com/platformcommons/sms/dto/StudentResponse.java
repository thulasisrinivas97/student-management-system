package com.platformcommons.sms.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StudentResponse {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String studentCode;
    private String email;
    private String mobileNumber;
    private String parentsNames;
    private List<AddressDto> addresses;
    private List<String> enrolledCourseNames;
}
