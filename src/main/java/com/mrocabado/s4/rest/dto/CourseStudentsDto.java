package com.mrocabado.s4.rest.dto;

import lombok.Data;
import java.util.List;

@Data
public class CourseStudentsDto {
    private String courseCode;
    private List<StudentDto> students;
}

