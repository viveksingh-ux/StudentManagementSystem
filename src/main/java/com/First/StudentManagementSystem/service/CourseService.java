package com.First.StudentManagementSystem.service;

import com.First.StudentManagementSystem.Dto.CourseRequestDto;
import com.First.StudentManagementSystem.Dto.CourseResponseDto;

import java.util.List;

public interface CourseService {

    CourseResponseDto createCourse(CourseRequestDto requestDto);
    List<CourseResponseDto> getAllCourse();
    CourseResponseDto getCourseById(Long id);
}
