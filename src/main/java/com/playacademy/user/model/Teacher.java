package com.playacademy.user.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.playacademy.game.model.Game;

@Entity
public class Teacher extends User {

	@Column(name = "educationalMail")
	private String educationalMail;
	

	 private Set <Game> games;
	//
	// public void addCourse(Course course){
	// createCourses.add(course);
	// }
	//
	// @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER,
	// mappedBy="Teacher")
	// public Set<Course> getCreateCourses() {
	// return createCourses;
	// }
	//
	// public void setCreateCourses(Set<Course> createCourses) {
	// this.createCourses = createCourses;
	// }

	
	

	public Teacher() {
		type = "Teacher";
	}

	public Teacher(long id) {
		this.setUserId(id);
	}

	// Setters
	public void setGames(Set<Game> games) {
		this.games = games;
	}
	public void setEducationalMail(String educationalMail) {
		this.educationalMail = educationalMail;
	}	
	public void addGame(Game game) {
		game.setCreator(this);
		games.add(game);
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

}
