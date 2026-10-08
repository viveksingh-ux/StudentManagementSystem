package com.First.StudentManagementSystem.service;

import com.First.StudentManagementSystem.Dto.StudentProjectionDto;
import com.First.StudentManagementSystem.Dto.StudentRequestDto;
import com.First.StudentManagementSystem.Dto.StudentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface StudentService {
    List<StudentResponseDto> getAllStudent();

    StudentResponseDto getStudentById(Long id);

    StudentResponseDto saveStudent(StudentRequestDto dto);

    StudentResponseDto updateStudent(Long id, StudentRequestDto dto);

    void deleteStudent(Long id);

    StudentResponseDto changeStudent(Long id, StudentRequestDto dto);

    StudentResponseDto getStudentByEmail(String email);

    Long getStudentCountByCourse(String courseName);

    void deleteStudentByEmail(String email);

    List<StudentResponseDto> getStudentNameAndCourse(String name, String course);

    List<StudentResponseDto> findStudentByNameContaining(String name);

    StudentResponseDto getStudentByEmailNative(String email);

    List<StudentResponseDto> getAllStudentsSortedByName();

    Page<StudentResponseDto> getStudent(Pageable pageable);

    List<StudentProjectionDto> getStudentProjection();

    List<StudentResponseDto> searchStudents(String name,String email,String course);

    List<StudentResponseDto> getAllStudentCustom();

    List<StudentResponseDto> getStudentByDepartmentCustom(String departmentName);
}
