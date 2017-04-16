package com.playacademy.game.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playacademy.course.model.Course;

//import com.playacademy.user.model.ScoreSheet;

@Entity
@Table(name = "games")
public class Game {

	private long gameId;

	@Column(name = "name")
	private String name;

	@Column(name = "rate")
	private long rate;

	private Course course;

	private Set<Question> questions;
	@JsonIgnore
	private Set<ScoreSheet> scores;

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

	public void setScores(Set<ScoreSheet> scores) {
		this.scores = scores;
	}

	public void setRate(long rate) {
		this.rate = rate;
	}

	// add
	public void addScore(ScoreSheet score) {
		score.setGame(this);
		scores.add(score);
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

	public long getRate() {
		return rate;
	}

	// Relations
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "game")
	public Set<Question> getQuestions() {
		return questions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "game")
	public Set<ScoreSheet> getScores() {
		return scores;
	}

	@ManyToOne
	@JoinColumn(name = "courseId", nullable = false)
	public Course getCourse() {
		return course;
	}

}
