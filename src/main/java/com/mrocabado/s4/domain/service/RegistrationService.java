package com.mrocabado.s4.domain.service;

import com.mrocabado.s4.domain.dependency.CourseRepository;
import com.mrocabado.s4.domain.dependency.StudentRepository;
import com.mrocabado.s4.domain.entity.Registration;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RegistrationService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public RegistrationService(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = Objects.requireNonNull(courseRepository);
        this.studentRepository = Objects.requireNonNull(studentRepository);
    }

    public void apply(Registration registration) {

        if ( !registration.exists() ) {
            registration.apply();
            update(registration);       //FIXME: This must run in a transaction
        }
    }

    private void update(Registration registration) {
        courseRepository.edit(registration.getCourse());
        studentRepository.edit(registration.getStudent());
    }
}
