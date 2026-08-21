package com.platformcommons.sms.dto;

import com.platformcommons.sms.entity.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressDto {
    @NotNull
    private AddressType type;
    @NotBlank
    private String addressLine;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
