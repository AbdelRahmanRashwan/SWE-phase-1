package com.example.rashwan.playacademy.Models;

public class CourseAttendance {
	
	private long attendanceId;
	private long achievement;
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
	
	public long getAttendanceId() {
		return attendanceId;
	}
	public long getAchievement() {
		return achievement;
	}
	// Relations
	public Student getStudent(){
		return student;
	}
	public Course getCourse() {
		return course;
	}
	
	
	
}
