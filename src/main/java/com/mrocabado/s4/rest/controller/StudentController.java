package com.mrocabado.s4.rest.controller;

import com.mrocabado.s4.domain.entity.Course;
import com.mrocabado.s4.domain.entity.Student;
import com.mrocabado.s4.domain.service.StudentService;
import com.mrocabado.s4.rest.dto.StudentCoursesDto;
import com.mrocabado.s4.rest.dto.StudentDto;
import com.mrocabado.s4.rest.util.Mappings;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import static com.mrocabado.s4.rest.util.Mappings.dtoToEntity;
import static com.mrocabado.s4.rest.util.Mappings.entityToDto;

@RestController
@RequestMapping(value = "/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        Objects.requireNonNull(studentService);
        this.studentService = studentService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<StudentDto>> findAll() {
        List<StudentDto> body = studentService.findAll(null).stream()
                                    .map(Mappings::entityToDto)
                                    .collect(Collectors.toList());
        return ResponseEntity.ok().body(body);
    }

    @RequestMapping(value = "/{filter}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<StudentDto>> findWithFilter(@MatrixVariable Map<String, String> filter) {

        List<StudentDto> body = studentService.findAll(filter).stream()
                                                .map(Mappings::entityToDto)
                                                .collect(Collectors.toList());
        return ResponseEntity.ok().body(body);
    }

    @RequestMapping(value = "/{id}/classes", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StudentCoursesDto> findCourses(@PathVariable(value = "id") String id) {
        List<Course> courses = studentService.findCourses(id);

        StudentCoursesDto body = new StudentCoursesDto();
        body.setStudentId(id);
        body.setCourses(courses.stream().map(Mappings::entityToDto).collect(Collectors.toList()));

        return ResponseEntity.ok().body(body);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto student) {
        Student createdStudent = studentService.create(dtoToEntity(student));

        return ResponseEntity.status(HttpStatus.CREATED).body(entityToDto(createdStudent));
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StudentDto> edit(@RequestBody StudentDto student) {
        studentService.edit(dtoToEntity(student));

        return ResponseEntity.ok(student);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> delete(@PathVariable(value = "id") String id) {
        studentService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
