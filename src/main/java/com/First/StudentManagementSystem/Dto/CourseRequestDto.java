package com.First.StudentManagementSystem.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDto {
    @NotBlank(message = "course name required")
    private String courseName;
    @NotBlank(message = "duration is required")
    private String duration;
    @NotNull(message = "Field is required")
    private double fees;
    @NotBlank(message = "Instructor is required")
    private String instructorName;
}
