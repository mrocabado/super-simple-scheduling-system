package com.mrocabado.s4.domain.dependency;

import com.mrocabado.s4.domain.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentRepository {
    List<Student> findById(String... id);
    List<Student> findAll(Map<String,String> filter);
    Student create(Student student);
    void edit(Student student);
    void delete(String id);
}
