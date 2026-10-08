package com.First.StudentManagementSystem.repositery.Custom;

import com.First.StudentManagementSystem.entity.Student;

import java.util.List;

public interface StudentRepositoryCustom {
    List<Student> findStudentCustom();
    List<Student> findStudentByDepartmentCustom(String departmentName);
}
