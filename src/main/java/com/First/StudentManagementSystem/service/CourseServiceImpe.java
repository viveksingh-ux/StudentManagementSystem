package com.First.StudentManagementSystem.service;

import com.First.StudentManagementSystem.Dto.CourseRequestDto;
import com.First.StudentManagementSystem.Dto.CourseResponseDto;
import com.First.StudentManagementSystem.Exception.ResourceNotFoundException;
import com.First.StudentManagementSystem.entity.Course;
import com.First.StudentManagementSystem.repositery.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpe implements CourseService{

    private final CourseRepository courseRepository;

    @Override
    public CourseResponseDto createCourse(CourseRequestDto requestDto) {
        Course course=new Course();
        course.setCourseName(requestDto.getCourseName());
        course.setDuration(requestDto.getDuration());
        course.setFees(requestDto.getFees());
        course.setInstructor(requestDto.getInstructorName());
        Course savedCourse=courseRepository.save(course);
        return mapToResponse(savedCourse);
    }

    @Override
    public List<CourseResponseDto> getAllCourse() {
        return courseRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public CourseResponseDto getCourseById(Long id) {
        Course course=courseRepository.findById(id).orElseThrow(()->
               new ResourceNotFoundException("course is not found with id"+id));
        return mapToResponse(course);
    }

    private CourseResponseDto mapToResponse(Course course){
        CourseResponseDto responseDto=new CourseResponseDto();
        responseDto.setId(course.getId());
        responseDto.setCourseName(course.getCourseName());
        responseDto.setDuration(course.getDuration());
        responseDto.setFees(course.getFees());
        responseDto.setInstructorName(course.getInstructor());
        return responseDto;
    }
}
