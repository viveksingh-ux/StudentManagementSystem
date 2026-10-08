package com.First.StudentManagementSystem.repositery;

import com.First.StudentManagementSystem.Dto.StudentProjectionDto;
import com.First.StudentManagementSystem.Projection.StudentProjection;
import com.First.StudentManagementSystem.entity.Student;
import com.First.StudentManagementSystem.repositery.Custom.StudentRepositoryCustom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> , JpaSpecificationExecutor<Student> ,
        StudentRepositoryCustom {

    ///derived query
    boolean  existsByEmail(String Email);
    Optional<Student> findByEmail(String email);
    /// JPQL query
    @Query("""
SELECT COUNT (s)
FROM Student s
JOIN s.courses  c
WHERE LOWER(c.courseName)=LOWER(:courseName)
""")
    Long countStudentByCourse(@Param("courseName") String courseName);
    void deleteByEmail(String email);
    @Query("""
SELECT s FROM Student s
JOIN s.courses c 
WHERE s.name=:name
AND LOWER(c.courseName)= LOWER(:course)
""")
    List<Student> findNameAndCourse(String name, String course);
    List<Student> findByNameContaining(String name);
    /// Native query
@Query(
  value ="SELECT * FROM students WHERE email=:email", nativeQuery=true)
Optional<Student> findStudentByEmailNative(@Param("email")String email);
List<StudentProjection> findBy();

@Query("""
SELECT new com.First.StudentManagementSystem.Dto.StudentProjectionDto(s.name,s.email) FROM Student s
""")
    List<StudentProjectionDto> getStudentProjection();
//@Query("""
//SELECT DISTINCT s FROM Student s
//LEFT JOIN FETCH s.courses
//LEFT JOIN FETCH s.department
//LEFT JOIN FETCH s.address
//""")
//List<Student> findAllWithDetails();
@EntityGraph(attributePaths = {"address","department","courses"})
@Query("SELECT s FROM Student s")
    List<Student> findAllWithDetails();

}
