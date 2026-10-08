package com.First.StudentManagementSystem.controller;

import com.First.StudentManagementSystem.Dto.StudentProjectionDto;
import com.First.StudentManagementSystem.Dto.StudentRequestDto;
import com.First.StudentManagementSystem.Dto.StudentResponseDto;
import com.First.StudentManagementSystem.Payload.ApiResponse;
import com.First.StudentManagementSystem.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/student")

public class StudentController {

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    public StudentService studentService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> getAllStudent(){
        List<StudentResponseDto> student=studentService.getAllStudent();
        ApiResponse<List<StudentResponseDto>> response=new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Student fetched successfully",
                student
        );
        return ResponseEntity.ok(response);
    }
     @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> getStudentById(@PathVariable Long id){
        StudentResponseDto student=studentService.getStudentById(id);
        if(student==null){
            return ResponseEntity.noContent().build();
        }
        ApiResponse<StudentResponseDto> response=new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "student fatched successfully",
                student
        );

        return  ResponseEntity.ok(response);
     }

     @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDto>> saveStudent(@Valid @RequestBody StudentRequestDto dto){
        StudentResponseDto savedStudent=studentService.saveStudent(dto);
        ApiResponse<StudentResponseDto> response=new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "student saved successfully",
                savedStudent
        );
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);

     }
     @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequestDto dto){
        StudentResponseDto student=studentService.updateStudent(id,dto);
        if(student==null){
            return ResponseEntity.noContent().build();
        }
        ApiResponse<StudentResponseDto> response=new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "student updated successfully",
                student
        );
        return ResponseEntity.ok(response);
     }

     @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteStudent(@PathVariable Long id){
         studentService.deleteStudent(id);
         ApiResponse<Object> response=new ApiResponse<>(
                 true,
                 LocalDateTime.now(),
                 HttpStatus.OK.value(),
                 "student deleted successfully",
                 null

         );
       return ResponseEntity.ok(response);
     }

     @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> changeStudent(@PathVariable Long id, @RequestBody StudentRequestDto dto){
        StudentResponseDto student=studentService.changeStudent(id,dto);
        if(student==null){
            return  ResponseEntity.noContent().build();
        }
         ApiResponse<StudentResponseDto> response=new ApiResponse<>(
                 true,
                 LocalDateTime.now(),
                 HttpStatus.OK.value(),
                 "student updated successfully",
                 student
         );
       return ResponseEntity.ok(response);

     }
     @GetMapping("/email/{email}")
     public ResponseEntity<StudentResponseDto> getStudentByEmail(@PathVariable String email){
        return ResponseEntity.ok(studentService.getStudentByEmail(email));
     }

     @GetMapping("/count")
     public ResponseEntity<ApiResponse<Long>> getStudentCountByCourse(@RequestParam String courseName){
        Long count=studentService.getStudentCountByCourse(courseName);
        return ResponseEntity.ok(
               new ApiResponse<>(
                       true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "successfully get student by course",
                count
                 )
        );
     }

     @DeleteMapping("/email/{email}")
     public ResponseEntity<ApiResponse<String>> deleteStudentByEmail(@PathVariable String email){
        studentService.deleteStudentByEmail(email);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "student deleted by email",
                        null
                )
        );
     }

     @GetMapping("/search")
     public ResponseEntity<List<StudentResponseDto>> getStudentByNameAndCourse(@RequestParam String name
             ,@RequestParam String Course) {
         return ResponseEntity.ok(
                 studentService.getStudentNameAndCourse(name, Course)
         );
     }
         @GetMapping("/search/name")
         public ResponseEntity<List<StudentResponseDto>> findStudentByNameContaining(@RequestParam String name){
             return ResponseEntity.ok(
                     studentService.findStudentByNameContaining(name)
             );



         }
         @GetMapping("/native/email/{email}")
         public ResponseEntity<StudentResponseDto> getStudentByEmailNative(@PathVariable String email){
        return ResponseEntity.ok(
                studentService.getStudentByEmailNative(email)
        );
         }
         @GetMapping("/sort")
     public ResponseEntity<List<StudentResponseDto>> getSortedStudents(){
        return ResponseEntity.ok(studentService.getAllStudentsSortedByName());
     }

     @GetMapping("/page")
     public ResponseEntity<Page<StudentResponseDto>> getAllStudent(Pageable pageable){
        return ResponseEntity.ok(studentService.getStudent(pageable));
     }

//     @GetMapping("/projection")
//    public ResponseEntity<List<StudentProjection>> getStudentProjection(){
//        return ResponseEntity.ok(studentService.getStudentProjection());
//     }

    @GetMapping("/projection/dto")
    public ResponseEntity<List<StudentProjectionDto>> getStudentProjection(){
        return ResponseEntity.ok(studentService.getStudentProjection());
    }

    @GetMapping("search/specification")
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> searchStudents(
            @RequestParam(required = false)String name,
            @RequestParam(required = false)String email,
            @RequestParam(required = false)String course ){
        List<StudentResponseDto> students=studentService.searchStudents(name,email,course);

        ApiResponse<List<StudentResponseDto>> response=new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Students Found Successfully",
                students
        );
        return ResponseEntity.ok(response);
     }
     @GetMapping("/custom")
     public List<StudentResponseDto> getAllStudentCustom(){
        return studentService.getAllStudentCustom();
     }
     @GetMapping("/custom/department")
     public List<StudentResponseDto> getStudentByDepartmentCustom(@RequestParam String departmentName){
        return studentService.getStudentByDepartmentCustom(departmentName);
     }
//     @GetMapping("/header")
//    public ResponseEntity<String> getClientVersion(@RequestHeader("X-client-version") String version){
//        return ResponseEntity.ok("Client version"+version);
//     }
}
