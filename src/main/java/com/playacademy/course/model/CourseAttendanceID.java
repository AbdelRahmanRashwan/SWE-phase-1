package com.playacademy.course.model;
 
import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

import com.playacademy.user.model.*;
 
@SuppressWarnings("serial")
@Embeddable
public class CourseAttendanceID implements Serializable { 
    
	private Student student;
    private Course course;
 
    
    public CourseAttendanceID(){}
    public CourseAttendanceID(Student student, Course course) {
		this.student= student;
		this.course = course;
	}
	// Setters
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
//    @Override
//    public boolean equals(Object o) {
//    	if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        CourseAttendanceID that = (CourseAttendanceID) o;
//
//        if (course != null ? !course.equals(that.course) : that.course != null) return false;
//        if (student != null ? !student.equals(that.student) : that.student != null)
//            return false;
//
//        return true;
//    }
// 
//    @Override
//    public int hashCode() {
//    	int result;
//        result = (course != null ? course.hashCode() : 0);
//        result = 31 * result + (student != null ? student.hashCode() : 0);
//        return result;
//    }
    
    // Relations
    @ManyToOne(cascade=CascadeType.ALL)
    public Student getStudent() {
        return student;
    }
 
    @ManyToOne(cascade=CascadeType.ALL)
    public Course getCourse() {
        return course;
    }
 
    
}