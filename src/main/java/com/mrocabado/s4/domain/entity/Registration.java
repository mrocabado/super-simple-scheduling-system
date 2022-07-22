package com.mrocabado.s4.domain.entity;

public class Registration {

    private Course course;
    private Student student;

    Registration(Course course, Student student) {
        this.course = course;
        this.student = student;
    }

    public boolean exists() {
        return this.course.getStudentIds().contains(this.student.getId())
                || this.student.getCourseCodes().contains(this.course.getCode());

    }

    public void apply() {
        this.course.addStudentId(this.student.getId());
        this.student.addCourseCode(this.course.getCode());
    }
    
    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }
}
