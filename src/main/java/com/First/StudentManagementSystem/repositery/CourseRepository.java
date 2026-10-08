package com.First.StudentManagementSystem.repositery;

import com.First.StudentManagementSystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
