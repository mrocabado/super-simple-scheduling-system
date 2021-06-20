package com.mrocabado.s4.rest.dto;

import lombok.Data;

//NOTE: Given the current com.mrocabado.s4.domain.entity.Student, this may seem overkill. But This separation may allow
//API and domain models to evolve independently.
@Data
public class StudentDto {
    private String
            id
            , firstName
            , lastName;
}
