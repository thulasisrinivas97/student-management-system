package com.platformcommons.sms.dto;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

/**
 * Fields a STUDENT (not an admin) is allowed to self-update.
 */
@Data
public class StudentProfileUpdateRequest {
    private String email;
    private String mobileNumber;
    private String parentsNames;

    @Valid
    private List<AddressDto> addresses;
}
