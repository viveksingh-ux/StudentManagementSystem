package com.First.StudentManagementSystem.Specification;

import com.First.StudentManagementSystem.entity.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<Student> hasName(String name){
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%"+name.toLowerCase()+"%"
            );
        };
    }

    public static Specification<Student> hasEmail(String email){
        return (root,query,criteriaBuilder)->{
            if(email==null || email.isBlank()){
                return criteriaBuilder.conjunction();
            }
           return criteriaBuilder.like(
                   criteriaBuilder.lower(root.get("email")),
                   "%" +email.toLowerCase()+"%"
           );
        };

    }

    public static Specification<Student> hasCourse(String course) {
        return (root, query, criteriaBuilder) -> {
            if (course == null || course.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("course")),
                    "%" + course.toLowerCase() + "%"
            );
        };
    }
}
