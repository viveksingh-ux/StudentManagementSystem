package com.First.StudentManagementSystem.Exception;


import com.First.StudentManagementSystem.Payload.ApiResponse;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> HandleStudentNotFoundException(StudentNotFoundException msg, HttpServletRequest request) {
       log.warn("Student not found: {} ",msg.getMessage());
        ApiResponse<Object> Response = new ApiResponse<>(
                false,
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                msg.getMessage(),
                null
        );
        return new ResponseEntity<>(Response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<Object>> HandleDuplicateEmail(DuplicateEmailException msg, HttpServletRequest request){
        ApiResponse<Object> Response = new ApiResponse<>(
                false,
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                msg.getMessage(),
                null
        );
        return new ResponseEntity<>(Response,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> validException(MethodArgumentNotValidException ex, HttpServletRequest req){

        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError->{
                    errors.put(
                            fieldError.getField(),
                            fieldError.getDefaultMessage()
                    );
                });

        ApiResponse<Object> Response=new ApiResponse<>(

                false,
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "validation faield",
                errors

        );
        return new ResponseEntity<>(Response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ApiResponse<Object>> optimisticLockException(OptimisticLockException ex, HttpServletRequest req){
        ApiResponse<Object> response=new ApiResponse<>(
                false,
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Student was already updated by another user",
                null
        );
        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }
}
