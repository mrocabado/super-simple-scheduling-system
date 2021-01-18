package com.mrocabado.s4.domain.entity;

import com.mrocabado.s4.domain.exception.InvalidEntityException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

//FIXME having setters in entity classes is poor encapsulation, But ok for this simple case
@Data
public class Student {
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 20;

    private String
            id
            , firstName
            , lastName;

    private Set<String> courseCodes = new TreeSet<>();

    public Student() {
        //do nothing
    }
    //FIXME could have been replaced by a static factory methods
    public Student(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.checkValid();
    }

    public Student addCourseCode(String code) {
        courseCodes.add(code);
        return this;
    }

    public void checkValid() {
        if (StringUtils.isEmpty(firstName) || firstName.length() < MIN_NAME_LENGTH ||  firstName.length() > MAX_NAME_LENGTH) {
            throw new InvalidEntityException("Invalid firstName");
        }

        if (StringUtils.isEmpty(lastName) || lastName.length() < MIN_NAME_LENGTH ||  lastName.length() > MAX_NAME_LENGTH) {
            throw new InvalidEntityException("Invalid lastName");
        }
    }
}
