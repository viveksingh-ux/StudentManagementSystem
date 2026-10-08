package com.First.StudentManagementSystem.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDto {

    private Long id;
    private String courseName;
    private String duration;
    private Double fees;
    private String instructorName;
}
