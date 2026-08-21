package com.platformcommons.sms.service;

import com.platformcommons.sms.dto.AddressDto;
import com.platformcommons.sms.dto.StudentProfileUpdateRequest;
import com.platformcommons.sms.dto.StudentRequest;
import com.platformcommons.sms.dto.StudentResponse;
import com.platformcommons.sms.entity.Address;
import com.platformcommons.sms.entity.Course;
import com.platformcommons.sms.entity.Student;
import com.platformcommons.sms.exception.ResourceNotFoundException;
import com.platformcommons.sms.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseService courseService;

    public StudentService(StudentRepository studentRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    // ---------- Admin operations ----------

    @Transactional
    public StudentResponse admitStudent(StudentRequest request) {
        studentRepository.findByStudentCode(request.getStudentCode()).ifPresent(s -> {
            throw new IllegalArgumentException("A student with code " + request.getStudentCode() + " already exists");
        });

        Student student = new Student();
        student.setName(request.getName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setStudentCode(request.getStudentCode());
        student.setEmail(request.getEmail());
        student.setMobileNumber(request.getMobileNumber());
        student.setParentsNames(request.getParentsNames());

        if (request.getAddresses() != null) {
            request.getAddresses().forEach(dto -> student.addAddress(toAddressEntity(dto)));
        }

        return toResponse(studentRepository.save(student));
    }

    @Transactional
    public StudentResponse assignCourseToStudent(Long studentId, Long courseId) {
        Student student = getStudentEntity(studentId);
        Course course = courseService.getCourseEntity(courseId);
        student.getCourses().add(course);
        return toResponse(studentRepository.save(student));
    }

    public List<StudentResponse> searchStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name).stream().map(this::toResponse).toList();
    }

    public List<StudentResponse> getStudentsByCourse(Long courseId) {
        return studentRepository.findByCourses_Id(courseId).stream().map(this::toResponse).toList();
    }

    // ---------- Student self-service operations ----------

    @Transactional
    public StudentResponse updateOwnProfile(String studentCode, StudentProfileUpdateRequest request) {
        Student student = getStudentByCode(studentCode);

        if (request.getEmail() != null) student.setEmail(request.getEmail());
        if (request.getMobileNumber() != null) student.setMobileNumber(request.getMobileNumber());
        if (request.getParentsNames() != null) student.setParentsNames(request.getParentsNames());

        if (request.getAddresses() != null) {
            student.getAddresses().clear();
            request.getAddresses().forEach(dto -> student.addAddress(toAddressEntity(dto)));
        }

        return toResponse(studentRepository.save(student));
    }

    public StudentResponse getOwnProfile(String studentCode) {
        return toResponse(getStudentByCode(studentCode));
    }

    @Transactional
    public StudentResponse leaveCourse(String studentCode, Long courseId) {
        Student student = getStudentByCode(studentCode);
        boolean removed = student.getCourses().removeIf(c -> Objects.equals(c.getId(), courseId));
        if (!removed) {
            throw new ResourceNotFoundException("Student is not enrolled in course id " + courseId);
        }
        return toResponse(studentRepository.save(student));
    }

    // ---------- helpers ----------

    private Student getStudentEntity(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));
    }

    private Student getStudentByCode(String studentCode) {
        return studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with code " + studentCode));
    }

    private Address toAddressEntity(AddressDto dto) {
        Address address = new Address();
        address.setType(dto.getType());
        address.setAddressLine(dto.getAddressLine());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());
        return address;
    }

    private AddressDto toAddressDto(Address address) {
        AddressDto dto = new AddressDto();
        dto.setType(address.getType());
        dto.setAddressLine(address.getAddressLine());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZipCode(address.getZipCode());
        dto.setCountry(address.getCountry());
        return dto;
    }

    private StudentResponse toResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setDateOfBirth(student.getDateOfBirth());
        response.setGender(student.getGender());
        response.setStudentCode(student.getStudentCode());
        response.setEmail(student.getEmail());
        response.setMobileNumber(student.getMobileNumber());
        response.setParentsNames(student.getParentsNames());
        response.setAddresses(student.getAddresses().stream().map(this::toAddressDto).toList());
        response.setEnrolledCourseNames(student.getCourses().stream().map(Course::getName).toList());
        return response;
    }
}
