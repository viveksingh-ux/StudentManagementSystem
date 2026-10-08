package com.First.StudentManagementSystem.service;

import com.First.StudentManagementSystem.Dto.CourseResponseDto;
import com.First.StudentManagementSystem.Dto.DepartmentRequestDto;
import com.First.StudentManagementSystem.Dto.DepartmentResponseDto;
import com.First.StudentManagementSystem.Dto.StudentResponseDto;
import com.First.StudentManagementSystem.Exception.ResourceNotFoundException;
import com.First.StudentManagementSystem.entity.Department;
import com.First.StudentManagementSystem.repositery.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {
        Department department=new Department();
        department.setDepartmentName(requestDto.getDepartmentName());
        Department savedDepartment=departmentRepository.save(department);
        return mapToResponse(savedDepartment);
    }

    @Override
    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse
                ).toList();
    }

    @Override
    public DepartmentResponseDto getDepartmentById(Long id) {
        Department department=departmentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Department not found with id :"+id));
        return mapToResponse(department);
    }

    public DepartmentResponseDto mapToResponse(Department department){
        DepartmentResponseDto dto=new DepartmentResponseDto();
        dto.setId(department.getId());
        dto.setDepartmentName(department.getDepartmentName());
        List<StudentResponseDto> students=department.getStudents()
                .stream()
                .map(
                        student ->{
                            StudentResponseDto studentDto=new StudentResponseDto();
                            studentDto.setId(student.getId());
                            studentDto.setName(student.getName());
                            studentDto.setEmail(student.getEmail());
                            studentDto.setCourses(student.getCourses().stream().map(
                                    course -> {
                                        CourseResponseDto responseDto=new CourseResponseDto();
                                        responseDto.setCourseName(course.getCourseName());
                                        responseDto.setId(course.getId());
                                        return responseDto;
                                    } ).toList()
                                    );
                            return studentDto;
                        }).toList();
                dto.setStudents(students);
        return dto;
    }
}
