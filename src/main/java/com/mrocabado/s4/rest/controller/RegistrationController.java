package com.mrocabado.s4.rest.controller;

import com.mrocabado.s4.domain.dto.Registration;
import com.mrocabado.s4.domain.service.RegistrationService;
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

    public RegistrationController(RegistrationService registrationService) {
        Objects.requireNonNull(registrationService);
        this.registrationService = registrationService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Registration> create(@RequestBody Registration registration) {
        registrationService.create(registration);

        return ResponseEntity.status(HttpStatus.CREATED).body(registration);
    }
}
