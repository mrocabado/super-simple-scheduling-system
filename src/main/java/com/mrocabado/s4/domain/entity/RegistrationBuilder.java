package com.mrocabado.s4.domain.entity;


import com.mrocabado.s4.domain.dependency.CourseRepository;
import com.mrocabado.s4.domain.dependency.StudentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class RegistrationBuilder {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public RegistrationBuilder(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = Objects.requireNonNull(courseRepository);
        this.studentRepository = Objects.requireNonNull(studentRepository);
    }

    public Registration from(String courseCode, String studentId) {
        Course course = retrieveCourseFromRepository(courseCode);
        Student student = retrieveStudentFromRepository(studentId);

        return new Registration(course, student);
    }

    private Course retrieveCourseFromRepository(String courseCode) {
        List<Course> courses = this.courseRepository.findById(courseCode);

        if ( courses.isEmpty() ) {
            throw new IllegalArgumentException("Class code not found in repository");
        }

        return courses.get(0);
    }

    private Student retrieveStudentFromRepository(String studentId) {
        List<Student> students = this.studentRepository.findById(studentId);

        if ( students.isEmpty() ) {
            throw new IllegalArgumentException("Student id not found in repository");
        }

        return students.get(0);
    }
}
