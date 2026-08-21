package com.platformcommons.sms.service;

import com.platformcommons.sms.dto.StudentRequest;
import com.platformcommons.sms.entity.Student;
import com.platformcommons.sms.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private StudentService studentService;

    private StudentRequest request;

    @BeforeEach
    void setUp() {
        request = new StudentRequest();
        request.setName("Jane Doe");
        request.setDateOfBirth(LocalDate.of(2008, 5, 20));
        request.setGender("Female");
        request.setStudentCode("STU-1001");
        request.setEmail("jane@example.com");
    }

    @Test
    void admitStudent_savesNewStudent_whenStudentCodeIsUnique() {
        when(studentRepository.findByStudentCode("STU-1001")).thenReturn(Optional.empty());
        when(studentRepository.save(any(Student.class))).thenAnswer(inv -> inv.getArgument(0));

        var response = studentService.admitStudent(request);

        assertThat(response.getStudentCode()).isEqualTo("STU-1001");
        assertThat(response.getName()).isEqualTo("Jane Doe");
    }

    @Test
    void admitStudent_throws_whenStudentCodeAlreadyExists() {
        when(studentRepository.findByStudentCode("STU-1001"))
                .thenReturn(Optional.of(new Student()));

        assertThatThrownBy(() -> studentService.admitStudent(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("STU-1001");
    }
}
