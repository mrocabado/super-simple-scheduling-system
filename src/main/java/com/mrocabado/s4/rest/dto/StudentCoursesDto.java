package com.mrocabado.s4.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentCoursesDto {
    private String studentId;
    private List<CourseDto> courses;
}
