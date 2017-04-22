package com.example.rashwan.playacademy.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class Teacher extends User implements Serializable{

	private String educationalMail;
	private ArrayList<Course> createdCourses;

	public Teacher() {
		type = "Teacher";
	}

	public Teacher(long id) {
		this.setUserId(id);
	}

	// Setters
	
	public void setEducationalMail(String educationalMail) {
		this.educationalMail = educationalMail;
	}
	public void setCreatedCourses(ArrayList<Course> courses) {
		this.createdCourses = courses;
	}
	
	
	// add

	
	// Getters
	public String getEducationalMail() {
		return educationalMail;
	}
	
	// Relations
	
	public ArrayList<Course> getCreatedCourses() {
		return createdCourses;
	}

}
