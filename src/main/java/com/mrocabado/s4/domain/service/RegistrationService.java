package com.mrocabado.s4.domain.service;

import com.mrocabado.s4.domain.dependency.CourseRepository;
import com.mrocabado.s4.domain.dependency.StudentRepository;
import com.mrocabado.s4.domain.entity.Course;
import com.mrocabado.s4.domain.entity.Registration;
import com.mrocabado.s4.domain.entity.Student;
import com.mrocabado.s4.domain.exception.EntityNotFoundException;
import com.mrocabado.s4.domain.exception.InvalidEntityException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RegistrationService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public RegistrationService(CourseRepository courseRepository, StudentRepository studentRepository) {
        Objects.requireNonNull(courseRepository);
        Objects.requireNonNull(studentRepository);
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public void create(Registration registration) {
        if (Objects.isNull(registration)) {
            throw new InvalidEntityException("Invalid registration");
        }

        if ( this.courseRepository.findById(registration.getCourseCode()).isEmpty() ) {
            throw new IllegalArgumentException("Class code not found in repository");
        }

        if ( this.studentRepository.findById(registration.getStudentId()).isEmpty() ) {
            throw new IllegalArgumentException("Student id not found in repository");
        }

        this.courseRepository.findById(registration.getCourseCode())
                                .get(0)
                                .addStudentId(registration.getStudentId());


        this.studentRepository.findById(registration.getStudentId())
                .get(0)
                .addCourseCode(registration.getCourseCode());
    }
}
