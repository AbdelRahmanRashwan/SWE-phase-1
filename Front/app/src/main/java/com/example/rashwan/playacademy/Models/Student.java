package com.example.rashwan.playacademy.Models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Student extends User implements Serializable{

	
	private Set<GameSheet> scores;
    private Set<CourseAttendance> attendance_sheets;
	
	
	public Student(){
		type="Student";
		attendance_sheets = new HashSet<CourseAttendance> ();
	}
	public Student(long id){
		this.setUserId(id);
	}
	
	
	// Setters
	public void setScores(Set<GameSheet> scores) {
		this.scores = scores;
	}
	public void setAttendance_sheets(Set<CourseAttendance> attendance_sheets) {
		this.attendance_sheets = attendance_sheets;
	}
	
	// add
	public void addScore(GameSheet score) {
		score.setStudent(this);
		scores.add(score);
	}
	
	// Relations
	public Set<GameSheet> getScores() {
		return scores;
	}
	
	public Set<CourseAttendance> getAttendance_sheets() {
		return attendance_sheets;
	}
	public void addAttendance(CourseAttendance attendance) {
		attendance.setStudent(this);
		attendance_sheets.add(attendance);
	}
	
	
	
}
