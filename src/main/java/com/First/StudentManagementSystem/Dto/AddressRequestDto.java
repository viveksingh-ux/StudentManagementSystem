package com.First.StudentManagementSystem.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class AddressRequestDto {
    @NotBlank(message = "city is required")
    private String city;
    private String state;
    private String country;
}
