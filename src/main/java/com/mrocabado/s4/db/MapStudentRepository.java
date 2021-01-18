package com.mrocabado.s4.db;

import com.mrocabado.s4.domain.dependency.StudentRepository;
import com.mrocabado.s4.domain.entity.Student;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
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
            student.setId(UUID.randomUUID().toString());
        }

        map.putIfAbsent(student.getId(), student);
        return student;
    }

    @Override
    public void edit(final Student student) {
        Student existingStudent = map.get(student.getId());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setFirstName(student.getFirstName());
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
        map.putIfAbsent("1", new Student("1", "John", "Doe")
                                .addCourseCode("c-1"));
        map.putIfAbsent("2", new Student("2", "Mary", "Poppins")
                                .addCourseCode("c-1")
                                .addCourseCode("c-2"));
    }
}
