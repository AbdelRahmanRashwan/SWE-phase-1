package com.example.rashwan.playacademy.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Student extends User implements Serializable{

	
	private ArrayList<GameSheet> scores;
    private ArrayList<CourseAttendance> attendance_sheets;
	
	
	public Student(){
		type="Student";
		attendance_sheets = new ArrayList<> ();
	}
	public Student(long id){
		this.setUserId(id);
	}
	
	
	// Setters
	public void setScores(ArrayList<GameSheet> scores) {
		this.scores = scores;
	}
	public void setAttendance_sheets(ArrayList<CourseAttendance> attendance_sheets) {
		this.attendance_sheets = attendance_sheets;
	}
	
	// add
	public void addScore(GameSheet score) {
		score.setStudent(this);
		scores.add(score);
	}
	
	// Relations
	public ArrayList<GameSheet> getScores() {
		return scores;
	}
	
	public ArrayList<CourseAttendance> getAttendance_sheets() {
		return attendance_sheets;
	}
	public void addAttendance(CourseAttendance attendance) {
		attendance.setStudent(this);
		attendance_sheets.add(attendance);
	}
	
	
	
}
