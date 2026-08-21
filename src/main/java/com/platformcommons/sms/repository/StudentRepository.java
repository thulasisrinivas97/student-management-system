package com.platformcommons.sms.repository;

import com.platformcommons.sms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByNameContainingIgnoreCase(String name);

    Optional<Student> findByStudentCode(String studentCode);

    Optional<Student> findByStudentCodeAndDateOfBirth(String studentCode, LocalDate dateOfBirth);

    List<Student> findByCourses_Id(Long courseId);
}
