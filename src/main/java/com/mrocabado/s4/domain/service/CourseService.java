package com.mrocabado.s4.domain.service;

import com.mrocabado.s4.domain.dependency.CourseRepository;
import com.mrocabado.s4.domain.dependency.StudentRepository;
import com.mrocabado.s4.domain.entity.Course;
import com.mrocabado.s4.domain.entity.Student;
import com.mrocabado.s4.domain.exception.EntityNotFoundException;
import com.mrocabado.s4.domain.exception.InvalidEntityException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository) {
        Objects.requireNonNull(courseRepository);
        Objects.requireNonNull(studentRepository);
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<Course> findAll(Map<String,String> filter) {
        return this.courseRepository.findAll( filter);
    }

    public Course create(Course course) {
        if (Objects.isNull(course)) {
            throw new InvalidEntityException("Invalid class");
        }
        if ( !this.courseRepository.findById(course.getCode()).isEmpty() ) {
            throw new IllegalArgumentException("Class code already in repository");
        }

        return this.courseRepository.create(course);
    }

    public void edit(Course course) {
        if (Objects.isNull(course)) {
            throw new InvalidEntityException("Invalid class");
        }
        if ( this.courseRepository.findById(course.getCode()).isEmpty() ) {
            throw new EntityNotFoundException("Class code not found in repository");
        }

        this.courseRepository.edit(course);
    }

    public void delete(String code) {
        if (Objects.isNull(code) || code.isEmpty()) {
            throw new IllegalArgumentException("Invalid code");
        }

        if ( this.courseRepository.findById(code).isEmpty() ) {
            throw new EntityNotFoundException("Class code not found in repository");
        }

        this.courseRepository.delete(code);
    }

    public List<Student> findStudents(String code) {
        if (Objects.isNull(code) || code.isEmpty()) {
            throw new IllegalArgumentException("Invalid code");
        }

        if ( this.courseRepository.findById(code).isEmpty() ) {
            throw new EntityNotFoundException("Class code not found in repository");
        }

        Course course = this.courseRepository.findById(code).get(0);
        if (course.getStudentIds().isEmpty()) {
            return Collections.emptyList();
        }

        String[] studentIds = new String[course.getStudentIds().size()];
        int i=0;
        for (String studentId: course.getStudentIds()) {
            studentIds[i] = studentId;
            i++;
        }
        return this.studentRepository.findById(studentIds);
    }

}
