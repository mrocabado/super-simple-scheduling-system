package com.mrocabado.s4.domain.entity;

import com.mrocabado.s4.domain.exception.InvalidEntityException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

//FIXME having setters in entity classes is poor encapsulation, But ok for this simple case
@Getter
public class Course {
    private static final int MIN_TITLE_LENGTH = 2;
    private static final int MAX_TITLE_LENGTH = 10;

    private static final int MIN_DESCRIPTION_LENGTH = 2;
    private static final int MAX_DESCRIPTION_LENGTH = 25;

    private String
            code
            , title
            , description;

    private Set<String> studentIds = new TreeSet<>();

    public Course() {
        //do nothing
    }

    public Course(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.checkValid();
    }

    public Course addStudentId(String id) {
        studentIds.add(id);
        return this;        //NOTE: Fluent APIs are nice!
    }

    private void checkValid() {
        if (StringUtils.isEmpty(title) || title.length() < MIN_TITLE_LENGTH ||  title.length() > MAX_TITLE_LENGTH) {
            throw new InvalidEntityException("Invalid title");
        }

        if (StringUtils.isEmpty(description) || description.length() < MIN_DESCRIPTION_LENGTH ||  description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new InvalidEntityException("Invalid description");
        }
    }

    public void generateCode() {
        this.code = "c-" + UUID.randomUUID();
    }
}
