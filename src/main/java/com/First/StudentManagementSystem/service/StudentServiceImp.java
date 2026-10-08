package com.First.StudentManagementSystem.service;

import com.First.StudentManagementSystem.Dto.*;
import com.First.StudentManagementSystem.Exception.DuplicateEmailException;
import com.First.StudentManagementSystem.Exception.ResourceNotFoundException;
import com.First.StudentManagementSystem.Exception.StudentNotFoundException;
import com.First.StudentManagementSystem.Specification.StudentSpecification;
import com.First.StudentManagementSystem.entity.Addresses;
import com.First.StudentManagementSystem.entity.Course;
import com.First.StudentManagementSystem.entity.Department;
import com.First.StudentManagementSystem.entity.Student;
import com.First.StudentManagementSystem.repositery.CourseRepository;
import com.First.StudentManagementSystem.repositery.DepartmentRepository;
import com.First.StudentManagementSystem.repositery.StudentRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImp implements StudentService {


    public final StudentRepository studentRepository;
    public final DepartmentRepository departmentRepository;
    public final CourseRepository courseRepository;
    public final PasswordEncoder passwordEncoder;



    @Override
    public List<StudentResponseDto> getAllStudent() {
        log.info("================>Fetching all Student<=================== ");
        List<StudentResponseDto> student= studentRepository.findAllWithDetails()
                .stream().map(this::mapToResponseDto).collect(Collectors.toList());
        log.info("Successfully fetched {} student ",student.size());
        return student;
    }

    @Override
    public StudentResponseDto getStudentById(Long id) {
        log.debug("Student found with id: {}",id);
        Student student= studentRepository.findById(id).orElseThrow(()->{
            log.warn("Student not found with id: {}",id);
               return new StudentNotFoundException("student is not exist with id:"+id);
        });
        log.info("Student fetched successfully with id: {}",id);
        return mapToResponseDto(student);
    }
   @Transactional(rollbackFor = Exception.class)
    @Override
    public StudentResponseDto saveStudent(StudentRequestDto dto) {
        if(studentRepository.existsByEmail(dto.getEmail())){
            throw new DuplicateEmailException("This email is already exists"+dto.getEmail());
        }

        //Department entity
        Department department=departmentRepository.findById(dto.getDepartmentId()).orElseThrow(()->
                new ResourceNotFoundException("department not found with id :"+dto.getDepartmentId()));
        //Address Entity
        Addresses addresses=new Addresses();
        addresses.setCity(dto.getAddresses().getCity());
        addresses.setState(dto.getAddresses().getState());
        addresses.setCountry(dto.getAddresses().getCountry());
        //Course Entity
        List<Course> courses=courseRepository.findAllById(dto.getCourseIds());

        //Student Entity
      Student student=new Student();
      student.setName(dto.getName());
      student.setEmail(dto.getEmail());
      student.setPassword(passwordEncoder.encode(dto.getPassword()));
      student.setAddress(addresses);
      student.setDepartment(department);
      student.setCourses(courses);
      Student saveStudent=studentRepository.save(student);

//      try{
//          ConfirmationEmail();}
//      catch (Exception e){
//          log.error("Failed to send confirmation email:{}",e);
//      }
      return mapToResponseDto(saveStudent);

    }
    private void ConfirmationEmail(){
        throw new RuntimeException("Something is Wrong");
    }

    @Transactional
    @Override
    public StudentResponseDto updateStudent(Long id, StudentRequestDto dto) {
        Student exitingStudent = studentRepository.findById(id).orElseThrow(()->
                new StudentNotFoundException("student is not exist with id:"+id));
        if(!exitingStudent.getVersion().equals(dto.getVersion())){
            throw new OptimisticLockException("Student was already updated by another user");
        }
            exitingStudent.setName(dto.getName());
            if(studentRepository.existsByEmail(dto.getEmail()) && !exitingStudent.getEmail().equals(dto.getEmail())){
                throw new DuplicateEmailException("this student is already exits"+dto.getEmail());
            }
            exitingStudent.setEmail(dto.getEmail());
            if(dto.getAddresses()!=null) {
                Addresses addresses = exitingStudent.getAddress();
                if (addresses == null) {
                    addresses = new Addresses();
                }
                addresses.setCity(dto.getAddresses().getCity());
                addresses.setState(dto.getAddresses().getState());
                addresses.setCountry(dto.getAddresses().getCountry());
                exitingStudent.setAddress(addresses);
            }
        Department department=departmentRepository.findById(dto.getDepartmentId()).orElseThrow(()->
                new ResourceNotFoundException("department not found with id :"+dto.getDepartmentId()));
        exitingStudent.setDepartment(department);

        Student student= studentRepository.save(exitingStudent);

        return mapToResponseDto(student);


    }

    @Override
    public void deleteStudent(Long id){
        if(!studentRepository.existsById(id)){
            throw new IllegalArgumentException("This student is not exist");
        }
        Student student=studentRepository.findById(id).orElseThrow(()->new StudentNotFoundException
                ("This student is not exist"));
        student.setDeleted(true);
        studentRepository.save(student);
    }

    @Override
    public void deleteStudentByEmail(String email) {
        Student student=studentRepository.findByEmail(email).orElseThrow(()->new StudentNotFoundException
                ("This student is not exist"));
        student.setDeleted(true);
        studentRepository.save(student);
    }

    @Override
    public StudentResponseDto changeStudent(Long id, StudentRequestDto dto) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(()->
                new StudentNotFoundException("student is not exits with id:"+id));

            if (dto.getName() != null) {
                existingStudent.setName(dto.getName());
            }
            if (dto.getEmail() != null) {
                existingStudent.setEmail(dto.getEmail());
            }
            if(dto.getAddresses()!=null){
                Addresses addresses= existingStudent.getAddress();
                if(addresses==null){
                    addresses=new Addresses();
                }
                if(dto.getAddresses().getCity()!=null){
                    addresses.setCity(dto.getAddresses().getCity());
                }
                if(dto.getAddresses().getState()!=null){
                    addresses.setState(dto.getAddresses().getState());
                }
                if(dto.getAddresses().getCountry()!=null){
                    addresses.setCountry(dto.getAddresses().getCountry());
                }
                existingStudent.setAddress(addresses);
            }
            if(dto.getDepartmentId()!=null){
                Department department=departmentRepository.findById(dto.getDepartmentId()).orElseThrow(()->
                        new ResourceNotFoundException("department not found with id :"+dto.getDepartmentId()));
                existingStudent.setDepartment(department);
            }
            Student student= studentRepository.save(existingStudent);
            return mapToResponseDto(student);

    }

    @Override
    public StudentResponseDto getStudentByEmail(String email) {
        Student student=studentRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundException("Student not found with email"+email));
        return mapToResponseDto(student);
    }

    @Override
    public Long getStudentCountByCourse(String courseName) {
        return  studentRepository.countStudentByCourse(courseName);

    }

    @Override
    public List<StudentResponseDto> getStudentNameAndCourse(String name, String course) {
       List<Student> student=studentRepository.findNameAndCourse(name, course);
        return student.stream()
                .map(this::mapToResponseDto).toList();
    }

    @Override
    public List<StudentResponseDto> findStudentByNameContaining(String name) {
        List<Student> students=studentRepository.findByNameContaining(name);
        return students.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public StudentResponseDto getStudentByEmailNative(String email) {
        Student student= studentRepository.findStudentByEmailNative(email).orElseThrow(()->new StudentNotFoundException("student not found with email:"+ email));
        return mapToResponseDto(student);
    }

    @Override
    public List<StudentResponseDto> getAllStudentsSortedByName() {
        List<Student> students=studentRepository.findAll(
          Sort.by("name").ascending()
        );
        return students.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public Page<StudentResponseDto> getStudent(Pageable pageable) {
        Page<Student> studentPage=studentRepository.findAll(pageable);
        return studentPage.map(this::mapToResponseDto);
    }

    @Override
    public List<StudentProjectionDto> getStudentProjection() {
        return studentRepository.getStudentProjection();
    }

    @Override
    public List<StudentResponseDto> searchStudents(String name, String email, String course) {
        Specification<Student> specification=Specification.allOf(
                            StudentSpecification.hasName(name),
                        StudentSpecification.hasEmail(email),
                        StudentSpecification.hasCourse(course));
        List<Student> students=studentRepository.findAll(specification);
        return students.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<StudentResponseDto> getAllStudentCustom() {
        List<Student> students=studentRepository.findStudentCustom();
        return students.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<StudentResponseDto> getStudentByDepartmentCustom(String departmentName) {
        List<Student> students=studentRepository.findStudentByDepartmentCustom(departmentName);
        return students.stream().map(this::mapToResponseDto).toList();
    }


    public StudentResponseDto mapToResponseDto(Student student){
        StudentResponseDto dto=new StudentResponseDto();
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
//        dto.setPassword(student.getPassword());
        dto.setId(student.getId());
        dto.setCreatedAt(student.getCreatedAt());
        dto.setUpdatedAt(student.getUpdatedAt());
        dto.setCreatedBy(student.getCreatedBy());
        dto.setLastModifiedBy(student.getLastModifiedBy());
        AddressResponseDto addressDto=new AddressResponseDto();
        addressDto.setCity(student.getAddress().getCity());
        addressDto.setState(student.getAddress().getState());
        addressDto.setCountry(student.getAddress().getCountry());
        dto.setAddress(addressDto);
       dto.setDepartmentName(student.getDepartment().getDepartmentName());
       dto.setCourses(
               student.getCourses().
                       stream().
                       map(
                               course-> new CourseResponseDto(
                                       course.getId(),
                                       course.getCourseName(),
                                       course.getDuration(),
                                       course.getFees(),
                                       course.getInstructor()
                               )).toList()
       );


        dto.setVersion(student.getVersion());
        return dto;

    }

}
