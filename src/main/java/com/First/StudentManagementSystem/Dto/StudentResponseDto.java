package com.First.StudentManagementSystem.Dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudentResponseDto {

    private Long id;
    private String name;
    private String email;
    private AddressResponseDto address;
    private String departmentName;
    private List<CourseResponseDto> courses;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String lastModifiedBy;
    private Long version;
//    private String password;

}
