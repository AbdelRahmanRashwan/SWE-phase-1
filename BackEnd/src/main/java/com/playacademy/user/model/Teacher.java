package com.playacademy.user.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playacademy.Notification.CommentNotification;
import com.playacademy.Notification.Notification;
import com.playacademy.course.model.Course;
import com.playacademy.game.model.Game;

@Entity
public class Teacher extends User implements Observer {

	@Column(name = "educationalMail")
	private String educationalMail;
	
	@JsonIgnore
	private Set <Course> createdCourses;
	
	private Set<Game> games;

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
	
	public void setGames(Set<Game> games) {
		this.games = games;
	}
	
	
	// add
	public void addCourse(Course course) {
		course.setCreator(this);
		createdCourses.add(course);
	}
	
	public void addGame(Game game){
		games.add(game);
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
	
	@ManyToMany(cascade=CascadeType.ALL,mappedBy = "collaborators")
    public Set<Game> getGames() {
		return games;
	}

	@Override
	public void update(Notification n) {
		// TODO Auto-generated method stub
		n.setReceiver(this);
		CommentNotification c = new CommentNotification();
		c.setNotificationId(n.getNotificationId());
		c.setNotificationTitle(n.getNotificationTitle());
		c.setNotificationDescription(n.getNotificationDescription());
		c.setReceiver(this);
		
		notifications.add(c);
	}

}
