package com.First.StudentManagementSystem.Dto;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;


@Data
public class StudentRequestDto {

    @NotBlank(message = "Name is required")
    @Size(min=3, max = 20, message = "Name size is between 3 to 20 character !")
    private String name;
    @NotBlank(message = "Email is required!")
    @Email(message = "Please enter a valid email")
    private String email;
    @NotBlank(message = "Password is required min 8 character")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&+_*])(?=.*\\d).{8,}$"
            ,message = "Password minimum contains 8 character"
    )
    private String password;
    private AddressRequestDto addresses;
    @NotNull(message = "Department id is required")
    private Long departmentId;
    @NotEmpty(message = "Course is required")
    private List<Long> courseIds;

    private Long version;


}
