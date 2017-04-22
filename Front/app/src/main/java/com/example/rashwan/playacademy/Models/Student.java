package com.example.rashwan.playacademy.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Student extends User implements Serializable{

	
	private ArrayList<GameSheet> scores;
	private ArrayList<Course> attendedCourses;


	public ArrayList<Course> getAttendedCourses() {
		return attendedCourses;
	}

	public void setAttendedCourses(ArrayList<Course> attendedCourses) {
		this.attendedCourses = attendedCourses;
	}

	public void addCourse(Course course){
		attendedCourses.add(course);
	}

	public Student(){
		type="Student";
		attendedCourses=null;
	}
	public Student(long id){
		this.setUserId(id);
	}
	
	
	// Setters
	public void setScores(ArrayList<GameSheet> scores) {
		this.scores = scores;
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

}
