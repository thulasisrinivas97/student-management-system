package com.platformcommons.sms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CourseRequest {
    @NotBlank
    private String name;
    private String description;
    private String courseType;
    private String duration;
    private List<String> topics;
}
