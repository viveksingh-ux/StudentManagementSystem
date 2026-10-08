package com.First.StudentManagementSystem.controller;

import com.First.StudentManagementSystem.Dto.DepartmentRequestDto;
import com.First.StudentManagementSystem.Dto.DepartmentResponseDto;
import com.First.StudentManagementSystem.Payload.ApiResponse;
import com.First.StudentManagementSystem.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponseDto>> createDepartment(@Valid @RequestBody DepartmentRequestDto requestDto){
        DepartmentResponseDto response=departmentService.createDepartment(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        true,
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value(),
                        "Department successfully created",
                        response

                        )
        );
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponseDto>>> getAllDepartments(){
        List<DepartmentResponseDto> departments=departmentService.getAllDepartments();
        ApiResponse<List<DepartmentResponseDto>> response=new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Departments fetched successfully",
                departments
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponseDto>> getDepartmentById(@PathVariable Long id){
        DepartmentResponseDto department=departmentService.getDepartmentById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "department fetched successfully",
                        department
                )
        );
    }
}
