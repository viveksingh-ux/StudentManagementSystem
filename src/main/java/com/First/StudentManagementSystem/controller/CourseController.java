package com.First.StudentManagementSystem.controller;

import com.First.StudentManagementSystem.Dto.CourseRequestDto;
import com.First.StudentManagementSystem.Dto.CourseResponseDto;
import com.First.StudentManagementSystem.Payload.ApiResponse;
import com.First.StudentManagementSystem.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponseDto>> createCourse(@Valid @RequestBody CourseRequestDto dto){
        CourseResponseDto response=courseService.createCourse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        true,
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value(),
                        "course is successfully created",
                        response
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponseDto>>> getAllCourse(){
        List<CourseResponseDto> course=courseService.getAllCourse();
        ApiResponse<List<CourseResponseDto>> response=new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Get all Course found sucessfully",
                course
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDto>> getCourseById(@PathVariable Long id){
        CourseResponseDto course=courseService.getCourseById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Course found successfully",
                        course
                )
        );
    }
}
