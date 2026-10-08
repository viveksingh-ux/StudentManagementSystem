package com.First.StudentManagementSystem.repositery.Custom;

import com.First.StudentManagementSystem.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class StudentRepositoryCustomImpl implements StudentRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Student> findStudentCustom() {
        TypedQuery<Student> query=entityManager.createQuery("SELECT s FROM Student s", Student.class);
        return query.getResultList();
    }

    @Override
    public List<Student> findStudentByDepartmentCustom(String departmentName) {
        TypedQuery<Student> query=entityManager.createQuery("SELECT s FROM Student s WHERE s.department.departmentName=:departmentName", Student.class);
       query.setParameter("departmentName",departmentName);
        return query.getResultList();
    }
}
