package com.playacademy.user.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playacademy.Notification.CommentNotification;
import com.playacademy.Notification.Notification;
import com.playacademy.course.model.Course;

@Entity
public class Teacher extends User implements Observer {

	@Column(name = "educationalMail")
	private String educationalMail;
	
	@JsonIgnore
	private Set <Course> createdCourses;
	

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
	public void setCreatedCourses(Set<Course> courses) {
		this.createdCourses = courses;
	}
	
	
	// add
	public void addCourse(Course course) {
		course.setCreator(this);
		createdCourses.add(course);
	}
	
	// Getters
	public String getEducationalMail() {
		return educationalMail;
	}
	
	// Relations
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="creator")
	public Set<Course> getCreatedCourses() {
		return createdCourses;
	}

	@Override
	public void update(Notification n) {
		// TODO Auto-generated method stub
		n.setReceiver(this);
		notifications.add((CommentNotification)n);
	}

}
