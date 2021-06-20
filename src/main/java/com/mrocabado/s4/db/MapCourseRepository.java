package com.mrocabado.s4.db;

import com.mrocabado.s4.domain.dependency.CourseRepository;
import com.mrocabado.s4.domain.entity.Course;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.mrocabado.s4.db.SimpleBeanPropertyFilter.applyFilter;

@Component
public class MapCourseRepository implements CourseRepository {
    private final static Map<String, Course> map = new ConcurrentHashMap<>();

    @Override
    public List<Course> findById(String... code) {
        List<String> codes = Arrays.asList(code);

        return map.entrySet().stream()
                .filter(entry -> codes.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> findAll(Map<String,String> filter) {
        return map.values().stream()
                .filter( student -> applyFilter(student, filter))
                .collect(Collectors.toList());
    }


    @Override
    public Course create(final Course course) {
        if (course.getCode() == null || course.getCode().isEmpty()) {
            course.generateCode();
        }
        map.putIfAbsent(course.getCode(), course);
        return course;
    }

    @Override
    public void edit(final Course course) {
        map.remove(course.getCode());
        map.putIfAbsent(course.getCode(), course);
    }

    @Override
    public void delete(final String code) {
        map.remove(code);
    }


    static{
        map.putIfAbsent("c-692af00c-2964-44ab-af0a-370114d40896", new Course("c-692af00c-2964-44ab-af0a-370114d40896", "Class-1", "Class-1 description")
                                    .addStudentId("1")
                                    .addStudentId("2"));
        map.putIfAbsent("c-f9eed91c-6db2-4e64-bdee-7edd38bca003", new Course("c-f9eed91c-6db2-4e64-bdee-7edd38bca003", "Class-2", "Class-2 description")
                                    .addStudentId("2"));
    }
}
