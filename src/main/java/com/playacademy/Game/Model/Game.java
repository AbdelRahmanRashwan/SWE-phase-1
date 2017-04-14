package com.playacademy.game.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "games")
public class Game {
			
	private long gameId;
	  
	@Column(name="name")
	private String name;
	
	@Column(name="courseId")
    long courseId;
	
    private Set<Question> questions;
    
    
    public Game(){
    	questions = new HashSet<Question>();
    }
	
	// Setters
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}
	
	
	public void addQuestion(Question question) {
		question.setGame(this);
		questions.add(question);
	}
	
	// Getters
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getGameId() {
		return gameId;
	}
	public String getName() {
		return name;
	}
	public long getCourseId(){
		return courseId;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="game")
	public Set<Question> getQuestions() {
		return questions;
	}
	
	
	
	
	
  
}
