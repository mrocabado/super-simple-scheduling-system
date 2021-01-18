package com.mrocabado.s4.db;

import com.mrocabado.s4.domain.dependency.CourseRepository;
import com.mrocabado.s4.domain.entity.Course;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
            course.setCode(UUID.randomUUID().toString());
        }
        map.putIfAbsent(course.getCode(), course);
        return course;
    }

    @Override
    public void edit(final Course course) {
        Course existingCourse = map.get(course.getCode());
        existingCourse.setTitle(course.getTitle());
        existingCourse.setDescription(course.getDescription());
    }

    @Override
    public void delete(final String code) {
        map.remove(code);
    }


    static{
        map.putIfAbsent("c-1", new Course("c-1", "Class-1", "Class-1 description")
                                    .addStudentId("1")
                                    .addStudentId("2"));
        map.putIfAbsent("c-2", new Course("c-2", "Class-2", "Class-2 description")
                                    .addStudentId("2"));
    }
}
