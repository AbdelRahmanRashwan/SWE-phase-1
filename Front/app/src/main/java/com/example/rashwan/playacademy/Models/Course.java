package com.example.rashwan.playacademy.Models;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Rashwan on 4/19/2017.
 */

public class Course implements Serializable {

    private long courseId;
    private String courseName;
    private String courseDescription;
    private Set<Game> games;
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

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public Teacher getCreator() {
        return creator;
    }

    public void setCreator(Teacher creator) {
        this.creator = creator;
    }

    public Course(long courseId, String courseName, String courseDescription, Set<Game> games, Teacher creator) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.games = games;
        this.creator = creator;
    }
}
