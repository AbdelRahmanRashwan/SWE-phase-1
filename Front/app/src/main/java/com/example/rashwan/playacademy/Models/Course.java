package com.example.rashwan.playacademy.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Rashwan on 4/19/2017.
 */

public class Course implements Serializable {

    private long courseId;
    private String courseName;
    private String courseDescription;
    private Teacher creator;

    public Course(){}

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Teacher getCreator() {
        return creator;
    }

    public void setCreator(Teacher creator) {
        this.creator = creator;
    }

    public Course(long courseId, String courseName, String courseDescriptio, Teacher creator) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.creator = creator;
    }
}
