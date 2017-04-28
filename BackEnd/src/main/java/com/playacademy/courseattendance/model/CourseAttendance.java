package com.playacademy.courseattendance.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playacademy.course.model.Course;
import com.playacademy.user.model.Student;


@Entity
@Table(name = "course_attendance")
//@AssociationOverrides({
//    @AssociationOverride(name = "attendanceId.student",
//        joinColumns = @JoinColumn(name = "Student_ID")),
//    @AssociationOverride(name = "attendanceId.course",
//        joinColumns = @JoinColumn(name = "Course_ID")) })
public class CourseAttendance {
	
	private long attendanceId;
	
	private long achievement;
	
	@JsonIgnore
	private Student student;
	private Course course;
	
	public CourseAttendance(){
	}
	
	// Setters
	
	public void setAttendanceId(long attendanceId) {
		this.attendanceId = attendanceId;
	}
	public void setAchievement(long achievement) {
		this.achievement = achievement;
	}
	
	public void setStudent(Student student){
		this.student = student;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	// Getters
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getAttendanceId() {
		return attendanceId;
	}
	public long getAchievement() {
		return achievement;
	}
	// Relations
	@ManyToOne
    @JoinColumn(name="studentId", nullable=false)
	public Student getStudent(){
		return student;
	}
	@ManyToOne
    @JoinColumn(name="courseId", nullable=false)
	public Course getCourse() {
		return course;
	}
	
	
	
}
