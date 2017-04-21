package com.example.rashwan.playacademy.Models;

import java.util.HashSet;
import java.util.Set;



public class Game {

	private long gameId;
	private String name;
	private long rate;
	private Course course;
	private Set<Question> questions;
	private Set<GameSheet> scores;

	public Game() {
		questions = new HashSet<Question>();
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


	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public void setScores(Set<GameSheet> scores) {
		this.scores = scores;
	}

	public void setRate(long rate) {
		this.rate = rate;
	}

	// add
	public void addScore(GameSheet score) {
		score.setGame(this);
		scores.add(score);
	}

	public void addQuestion(Question question) {
		question.setGame(this);
		questions.add(question);
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
	public Set<Question> getQuestions() {
		return questions;
	}

	public Set<GameSheet> getScores() {
		return scores;
	}

	public Course getCourse() {
		return course;
	}

}
