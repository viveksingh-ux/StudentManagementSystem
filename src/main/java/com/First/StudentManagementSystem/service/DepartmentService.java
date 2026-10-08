package com.First.StudentManagementSystem.service;

import com.First.StudentManagementSystem.Dto.DepartmentRequestDto;
import com.First.StudentManagementSystem.Dto.DepartmentResponseDto;

import java.util.List;

public interface DepartmentService {

    DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto);
    List<DepartmentResponseDto> getAllDepartments();
    DepartmentResponseDto getDepartmentById(Long id);
}
