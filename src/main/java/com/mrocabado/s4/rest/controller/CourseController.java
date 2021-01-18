package com.mrocabado.s4.rest.controller;

import com.mrocabado.s4.domain.entity.Course;
import com.mrocabado.s4.domain.entity.Student;
import com.mrocabado.s4.domain.service.CourseService;
import com.mrocabado.s4.rest.dto.CourseDto;
import com.mrocabado.s4.rest.dto.CourseStudentsDto;
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
@RequestMapping(value = "/classes")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        Objects.requireNonNull(courseService);
        this.courseService = courseService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CourseDto>> findAll() {
        List<CourseDto> body = courseService.findAll(null).stream()
                                        .map(Mappings::entityToDto)
                                        .collect(Collectors.toList());
            return ResponseEntity.ok().body(body);
    }

    @RequestMapping(value = "/{filter}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CourseDto>> findWithFilter(@MatrixVariable Map<String, String> filter) {

        List<CourseDto> body = courseService.findAll(filter).stream()
                                        .map(Mappings::entityToDto)
                                        .collect(Collectors.toList());
        return ResponseEntity.ok().body(body);
    }

    @RequestMapping(value = "/{code}/students", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CourseStudentsDto> findCourses(@PathVariable(value = "code") String code) {
        List<Student> students = courseService.findStudents(code);

        CourseStudentsDto body = new CourseStudentsDto();
        body.setCourseCode(code);
        body.setStudents(students.stream().map(Mappings::entityToDto).collect(Collectors.toList()));

        return ResponseEntity.ok().body(body);
    }


    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CourseDto> create(@RequestBody CourseDto course) {
        Course createdCourse = courseService.create(dtoToEntity(course));

        return ResponseEntity.status(HttpStatus.CREATED).body(entityToDto(createdCourse));
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CourseDto> edit(@RequestBody CourseDto course) {
        courseService.edit(dtoToEntity(course));

        return ResponseEntity.ok(course);
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> delete(@PathVariable(value = "code") String code) {
        courseService.delete(code);

        return ResponseEntity.noContent().build();
    }
}
