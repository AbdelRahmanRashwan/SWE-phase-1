package com.example.rashwan.playacademy.Models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;



public class Game {

	private long gameId;
	private String name;
	private long rate;
	private Course course;
	private ArrayList<Question> questions;

	public Game() {
		questions = new ArrayList<>();
	}

	// Setters
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCourse(Course course) {
		this.course = course;
	}


	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public void setRate(long rate) {
		this.rate = rate;
	}

	// Getters

	public long getGameId() {
		return gameId;
	}

	public String getName() {
		return name;
	}

	public long getRate() {
		return rate;
	}

	// Relations
	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public Course getCourse() {
		return course;
	}

}
