package com.mrocabado.s4.rest.util;

import com.mrocabado.s4.domain.entity.Course;
import com.mrocabado.s4.domain.entity.Student;
import com.mrocabado.s4.rest.dto.CourseDto;
import com.mrocabado.s4.rest.dto.StudentDto;


//FIXME: Try to use a mapping library for model conversion
//https://www.baeldung.com/java-performance-mapping-frameworks
//https://www.baeldung.com/jmapper
//https://www.baeldung.com/tag/mapstruct/
public class Mappings {
    public static Student dtoToEntity(StudentDto dto) {
        Student student = new Student(dto.getId(), dto.getFirstName(), dto.getLastName());
        return student;
    }

    public static Course dtoToEntity(CourseDto dto) {
        Course course = new Course(dto.getCode(), dto.getTitle(), dto.getDescription());
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
