package com.mrocabado.s4.rest.util;

import com.mrocabado.s4.domain.entity.Course;
import com.mrocabado.s4.domain.entity.Student;
import com.mrocabado.s4.rest.dto.CourseDto;
import com.mrocabado.s4.rest.dto.StudentDto;

public class Mappings {
    public static Student dtoToEntity(StudentDto dto) {
        Student student = new Student();

        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());

        return student;
    }

    public static Course dtoToEntity(CourseDto dto) {
        Course course = new Course();

        course.setCode(dto.getCode());
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());

        return course;
    }

    public static StudentDto entityToDto(Student student) {
        StudentDto dto = new StudentDto();

        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());

        return dto;
    }

    public static CourseDto entityToDto(Course course) {
        CourseDto dto = new CourseDto();

        dto.setCode(course.getCode());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());

        return dto;
    }
}
