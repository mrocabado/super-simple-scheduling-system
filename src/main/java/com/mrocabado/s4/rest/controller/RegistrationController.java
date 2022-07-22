package com.mrocabado.s4.rest.controller;

import com.mrocabado.s4.domain.entity.Registration;
import com.mrocabado.s4.domain.entity.RegistrationBuilder;
import com.mrocabado.s4.domain.service.RegistrationService;
import com.mrocabado.s4.rest.dto.RegistrationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

@RestController
@RequestMapping(value = "/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;
    private final  RegistrationBuilder registrationBuilder;

    public RegistrationController(RegistrationService registrationService, RegistrationBuilder registrationBuilder) {
        this.registrationService = Objects.requireNonNull(registrationService);
        this.registrationBuilder = Objects.requireNonNull(registrationBuilder);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RegistrationDto> create(@RequestBody RegistrationDto registrationDto) {
        Registration registration = registrationBuilder.from(registrationDto.getCourseCode(), registrationDto.getStudentId());

        registrationService.apply(registration);

        return ResponseEntity.status(HttpStatus.CREATED).body(registrationDto);
    }

}
