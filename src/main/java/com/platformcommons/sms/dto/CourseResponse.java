package com.platformcommons.sms.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseResponse {
    private Long id;
    private String name;
    private String description;
    private String courseType;
    private String duration;
    private List<String> topics;
}
