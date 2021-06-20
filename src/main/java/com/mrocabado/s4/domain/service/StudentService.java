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
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository,CourseRepository courseRepository) {
        Objects.requireNonNull(studentRepository);
        Objects.requireNonNull(courseRepository);
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> findAll(Map<String,String> filter) {
        return this.studentRepository.findAll( filter);
    }

    public Student create(Student student) {
        //FIXME: We could have use lombok or simmilar to generate this null check
        if (Objects.isNull(student)) {
            throw new InvalidEntityException("Invalid student");
        }
        if ( !this.studentRepository.findById(student.getId()).isEmpty() ) {
            throw new IllegalArgumentException("Student id already in repository");
        }

        //NOTE In a service class data access should be done over an Interface
        return this.studentRepository.create(student);
    }

    public void edit(Student student) {
        if (Objects.isNull(student)) {
            throw new InvalidEntityException("Invalid student");
        }
        if ( this.studentRepository.findById(student.getId()).isEmpty() ) {
            throw new IllegalArgumentException("Student id not found in repository");
        }

       this.studentRepository.edit(student);
    }

    public void delete(String id) {
        if (Objects.isNull(id) || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }

        if ( this.studentRepository.findById(id).isEmpty() ) {
            throw new EntityNotFoundException("Student id not found in repository");
        }

        this.studentRepository.delete(id);
    }

    public List<Course> findCourses(String id) {
        if (Objects.isNull(id) || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }

        if ( this.studentRepository.findById(id).isEmpty() ) {
            throw new EntityNotFoundException("Student id not found in repository");
        }

        Student student = this.studentRepository.findById(id).get(0);
        if (student.getCourseCodes().isEmpty()) {
            return Collections.emptyList();
        }

        String[] courseCodes = new String[student.getCourseCodes().size()];
        int i=0;
        for (String code: student.getCourseCodes()) {
            courseCodes[i] = code;
            i++;
        }
        return this.courseRepository.findById(courseCodes);
    }
}
