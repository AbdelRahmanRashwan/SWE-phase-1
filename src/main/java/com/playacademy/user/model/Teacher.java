package com.playacademy.user.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playacademy.course.model.Course;
import com.playacademy.game.model.Game;

@Entity
public class Teacher extends User {

	@Column(name = "educationalMail")
	private String educationalMail;
	
	@JsonIgnore
	private Set <Game> games;
	
	@JsonIgnore
	private Set <Course> courses;

	

	

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
	public void setGames(Set<Game> games) {
		this.games = games;
	}
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
	
	
	// add
	public void addGame(Game game) {
		game.setCreator(this);
		games.add(game);
	}
	
	public void addCourse(Course course) {
		course.setCreator(this);
		courses.add(course);
	}
	
	// Getters
	public String getEducationalMail() {
		return educationalMail;
	}
	
	// Relations
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="creator")
	public Set<Game> getGames() {
		return games;
	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="creator")
	public Set<Course> getCourses() {
		return courses;
	}

}
