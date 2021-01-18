package com.mrocabado.s4.domain.dependency;

import com.mrocabado.s4.domain.entity.Course;

import java.util.List;
import java.util.Map;

public interface CourseRepository {
    List<Course> findById(String... code);
    List<Course> findAll(Map<String,String> filter);
    Course create(Course course);
    void edit(Course course);
    void delete(String code);
}
