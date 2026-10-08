package com.First.StudentManagementSystem;

import com.First.StudentManagementSystem.Dto.StudentResponseDto;
import com.First.StudentManagementSystem.Exception.StudentNotFoundException;
import com.First.StudentManagementSystem.entity.Addresses;
import com.First.StudentManagementSystem.entity.Department;
import com.First.StudentManagementSystem.entity.Student;
import com.First.StudentManagementSystem.repositery.StudentRepository;
import com.First.StudentManagementSystem.service.StudentServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplUnitTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImp studentServiceImp;

    ///Arranged
    @Test
   void ShouldReturnStudentById() {
        Student student = new Student();
        student.setId(1L);
        student.setName("vivek singh");
        student.setEmail("don@gmail.com");
        student.setPassword("Vivek@1209#");
        Addresses addresses = new Addresses();
        addresses.setCity("Motihari");
        addresses.setState("Bihar");
        addresses.setCountry("India");
        student.setAddress(addresses);
        Department department = new Department();
        department.setDepartmentName("cse");
        student.setDepartment(department);
        student.setCourses(List.of());
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));


        ///Act
        StudentResponseDto result = studentServiceImp.getStudentById(1L);

        ///Assert
        assertEquals(1L, result.getId());
        assertEquals("vivek singh", result.getName());
        assertEquals("don@gmail.com", result.getEmail());
//        verify(studentRepository).findById(1L);
//        verify(studentRepository,never()).deleteById(1L);
    }
        @Test
        void ShouldThrowExceptionWhenRepositoryFails(){
        ///Arrange
            when(studentRepository.findById(1L)).thenThrow(new RuntimeException());
            ///Act

            RuntimeException runtimeException=assertThrows(
                    RuntimeException.class,()->studentServiceImp.getStudentById(1L)
            );

        }

        @Test
        void ShouldThrowExceptionWhenStudentNotFound(){

        //Arranged
            when(studentRepository.findById(1L)).thenReturn(Optional.empty());

            //Act & Assert
            assertThrows(
                    StudentNotFoundException.class,()->studentServiceImp.getStudentById(1L)
            );

        }

}
