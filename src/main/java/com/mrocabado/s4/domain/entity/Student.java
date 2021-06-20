package com.mrocabado.s4.domain.entity;

import com.mrocabado.s4.domain.exception.InvalidEntityException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

//FIXME having setters in entity classes is poor encapsulation, But ok for this simple case
@Getter
public class Student {
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 20;

    private String
            //FIXME: Yeah, I know Does no makes sense to make a surrogate/generated ID visible in the domain model.
            //This should be a natural/domain key.
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

        //To follow the always-valid domain model principle
        this.checkValid();
    }

    public Student addCourseCode(String code) {
        //NOTE: See how we did not expose courseCodes over a getter!
        //This is proper encapsulation
        courseCodes.add(code);
        return this;
    }

    private void checkValid() {
        //TODO Make sure you add a proper Value Type and move this validation there if you need
        //these 'firstName' or 'lastName' in a other entity or application
        //And of course, just checking for the length may not be enough.
        //Remember that Value Types are part of core/domain layer

        //TODO: try to avoid writing all validation code:
        //https://docs.vavr.io/#_validation
        //https://github.com/mvallim/java-fluent-validator
        //https://github.com/making/yavi
        //https://github.com/google/libphonenumber
        if (StringUtils.isEmpty(firstName) || firstName.length() < MIN_NAME_LENGTH ||  firstName.length() > MAX_NAME_LENGTH) {
            throw new InvalidEntityException("Invalid firstName");
        }

        if (StringUtils.isEmpty(lastName) || lastName.length() < MIN_NAME_LENGTH ||  lastName.length() > MAX_NAME_LENGTH) {
            throw new InvalidEntityException("Invalid lastName");
        }
    }

    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
}
