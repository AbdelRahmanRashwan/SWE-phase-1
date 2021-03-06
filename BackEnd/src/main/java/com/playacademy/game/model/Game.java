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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playacademy.comments.Comment;
import com.playacademy.course.model.Course;
import com.playacademy.gamesheet.model.GameSheet;
import com.playacademy.user.model.Teacher;

//import com.playacademy.user.model.ScoreSheet;

@Entity
@Table(name = "games")
public class Game {

	private long gameId;

	@Column(name = "name")
	private String name;

	@Column(name = "rate")
	private long rate;

	@Column(name = "canceled")
	private boolean canceled;

	int numOfRates;

	@JsonIgnore
	private Course course;

	private Set<Question> questions;
	@JsonIgnore
	private Set<GameSheet> scores;
	@JsonIgnore
	private Set<Teacher> collaborators;

	@JsonIgnore
	protected Set<Comment> comments;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="game")
	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
		
	public Game() {
		questions = new HashSet<Question>();
		scores = new HashSet<GameSheet>();
		collaborators = new HashSet<Teacher>();
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

	public void setCanceled(boolean b) {
		canceled = b;
	}

	public void setNumOfRates(int i) {
		numOfRates = i;
	}

	public void setCollaborators(Set<Teacher> collaborators) {
		this.collaborators = collaborators;
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

	public void deleteQuestion(Question question) {
		question.setGame(null);
		questions.remove(question);
	}
	
	public void addCollaborator(Teacher collaborator) {
		collaborators.add(collaborator);
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

	public boolean isCanceled() {
		return canceled;
	}

	public int getNumOfRates() {
		return numOfRates;
	}

	// Relations
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "game")
	public Set<Question> getQuestions() {
		return questions;
	}

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "game")
	public Set<GameSheet> getScores() {
		return scores;
	}

	@ManyToOne
	@JoinColumn(name = "courseId", nullable = false)
	public Course getCourse() {
		return course;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "game_collaborators", joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "gameId"), 
	inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "userId"))
	public Set<Teacher> getCollaborators() {
		return collaborators;
	}

}
