package com.First.StudentManagementSystem.Security;

import com.First.StudentManagementSystem.Exception.StudentNotFoundException;
import com.First.StudentManagementSystem.entity.Student;
import com.First.StudentManagementSystem.repositery.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student=studentRepository.findByEmail(username).orElseThrow(()->
                new StudentNotFoundException("Student not found Exception with email :"+username));
        return User.builder()
                .username(student.getEmail())
                .password(student.getPassword())
                .roles(student.getRole())
                .build();
    }
}
