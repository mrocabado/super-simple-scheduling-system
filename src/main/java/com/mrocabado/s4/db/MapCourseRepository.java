package com.mrocabado.s4.db;

import com.mrocabado.s4.domain.dependency.CourseRepository;
import com.mrocabado.s4.domain.entity.Course;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
                .filter( student -> this.applyFilter(student, filter))
                .collect(Collectors.toList());
    }


    @Override
    public Course create(final Course course) {
        if (course.getCode() == null || course.getCode().isEmpty()) {
            course.setCode(UUID.randomUUID().toString());
        }
        return map.putIfAbsent(course.getCode(), course);
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

    //Simple bean property filter
    private boolean applyFilter(Course course, Map<String, String> filter) {
        boolean isFiltered = true;

        if (Objects.nonNull(filter) && !filter.isEmpty()) {
            try {
                Map<String, Object> properties = PropertyUtils.describe(course);

                isFiltered = false;
                for (Map.Entry<String, String> filterEntry : filter.entrySet()) {
                    isFiltered = properties.entrySet().stream()
                            .anyMatch( property ->
                                    property.getKey().equals(filterEntry.getKey())
                                            && property.getValue().equals(filterEntry.getValue())
                            );
                    if (isFiltered) {
                        break;
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
                //do nothing
            }
        }

        return isFiltered;
    }

    static{
        map.putIfAbsent("c-1", new Course("c-1", "Class-1", "Class-1 description")
                                    .addStudentId("1")
                                    .addStudentId("2"));
        map.putIfAbsent("c-2", new Course("2", "Class-2", "Class-2 description")
                                    .addStudentId("2"));
    }
}
