package com.First.StudentManagementSystem;


import com.First.StudentManagementSystem.Dto.StudentResponseDto;
import com.First.StudentManagementSystem.Exception.StudentNotFoundException;
import com.First.StudentManagementSystem.entity.Addresses;
import com.First.StudentManagementSystem.entity.Course;
import com.First.StudentManagementSystem.entity.Department;
import com.First.StudentManagementSystem.entity.Student;
import com.First.StudentManagementSystem.repositery.CourseRepository;
import com.First.StudentManagementSystem.repositery.DepartmentRepository;
import com.First.StudentManagementSystem.repositery.StudentRepository;
import com.First.StudentManagementSystem.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StudentServiceImplIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentService studentService;

    @Test
    void ShouldReturnStudentById(){

        //Arranged
        Department department=new Department();
        department.setDepartmentName("Computer Science");
        department=departmentRepository.save(department);

        Course course=new Course();
        course.setCourseName("Spring Boot");
        course.setFees(30000.0);
        course.setDuration("4 months");
        course.setInstructor("Teachers");
        course=courseRepository.save(course);

        Addresses addresses=new Addresses();
        addresses.setCountry("INDIA");
        addresses.setState("BIHAR");
        addresses.setCity("MOTIHARI");

        Student student=new Student();
        student.setName("Vivek singh");
        student.setEmail("Vivek@gmail.com");
        student.setPassword("Vivek@12#");
        student.setAddress(addresses);
        student.setDepartment(department);
        student.setCourses(List.of(course));
        Student savedStudent=studentRepository.save(student);

        //Act
        StudentResponseDto result=studentService.getStudentById(savedStudent.getId());

        //Assert

        assertEquals(savedStudent.getId(),result.getId());
        assertEquals("Vivek singh",result.getName());
        assertEquals("Vivek@gmail.com",result.getEmail());
        assertEquals("Computer Science",result.getDepartmentName());
        assertEquals("MOTIHARI",result.getAddress().getCity());
        assertEquals("BIHAR",result.getAddress().getState());
        assertEquals("INDIA",result.getAddress().getCountry());
        assertEquals(1,result.getCourses().size());

    };

    @Test
    void ShouldStudentNotFoundException(){
        assertThrows(
                StudentNotFoundException.class,()->
                        studentService.getStudentById(20L)
        );
    }
}












