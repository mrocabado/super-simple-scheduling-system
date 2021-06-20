package com.mrocabado.s4.db;

import com.mrocabado.s4.domain.dependency.StudentRepository;
import com.mrocabado.s4.domain.entity.Student;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import static com.mrocabado.s4.db.SimpleBeanPropertyFilter.applyFilter;

@Component
public class MapStudentRepository implements StudentRepository {
    private final static Map<String, Student> map = new ConcurrentHashMap<>();

    @Override
    public List<Student> findById(String... id) {
        List<String> ids = Arrays.asList(id);

        return map.entrySet().stream()
                    .filter(entry -> ids.contains(entry.getKey()))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
    }

    @Override
    public List<Student> findAll(Map<String,String> filter) {
        return map.values().stream()
                            .filter( student -> applyFilter(student, filter))
                            .collect(Collectors.toList());
    }

    @Override
    public Student create(Student student) {
        if (student.getId() == null || student.getId().isEmpty()) {
            //NOTE: Generating the internal/surrogate ID here instead of using the from the DB may ease DB engine migration
            student.generateId();
        }

        map.putIfAbsent(student.getId(), student);
        return student;
    }

    @Override
    public void edit(final Student student) {
        map.remove(student.getId());
        map.putIfAbsent(student.getId(), student);
    }

    @Override
    public void delete(final String id) {
        map.remove(id);
    }

    //Just for quick testing
    public static void main(String[] args) {
        Map<String,String> filterOk = new HashMap<>();
        filterOk.putIfAbsent("lastName", "Doe");

        Map<String,String> filterBad = new HashMap<>();
        filterBad.putIfAbsent("lastName", "Doll");

        StudentRepository repository = new MapStudentRepository();
        System.out.println("findById: " + repository.findById("1"));
        System.out.println("findAll(null): " + repository.findAll(null));
        System.out.println("findAll(filterOk): " + repository.findAll(filterOk));
        System.out.println("findAll(filterBad): " +  repository.findAll(filterBad));
    }


    static{
        map.putIfAbsent("284239e8-8c1c-4d3d-a046-90df74b1fac3", new Student("284239e8-8c1c-4d3d-a046-90df74b1fac3", "John", "Doe")
                                .addCourseCode("c-1"));
        map.putIfAbsent("a326064f-d224-4578-a58e-83a80b1cc8de", new Student("a326064f-d224-4578-a58e-83a80b1cc8de", "Mary", "Poppins")
                                .addCourseCode("c-1")
                                .addCourseCode("c-2"));
    }
}
