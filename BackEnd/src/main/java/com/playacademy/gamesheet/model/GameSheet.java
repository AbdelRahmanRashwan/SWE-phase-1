package com.playacademy.gamesheet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playacademy.game.model.Game;
import com.playacademy.user.model.Student;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.playacademy.game.model.Game;


@Entity
@Table(name="score_sheet")
public class GameSheet {
	
	
	private long scoreId;
	
	@Column(name = "score")
	private long score;
	
	@Column(name = "rate")
	private long rate;
	
	@JsonIgnore
    private Student student;
	@JsonIgnore
    private Game game;
	
	
	
	// Setters
	public void setScoreId(long id) {
		scoreId = id;
	}
	public void setScore(long score) {
		this.score = score;
	}
	public void setRate(long rate) {
		this.rate = rate;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
	//Getters
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	public long getScoreId() {
		return scoreId;
	}
	public long getScore() {
		return score;
	}
	public long getRate() {
		return rate;
	}
	
	// Relations
	@ManyToOne
    @JoinColumn(name="studentId", nullable=false)
	public Student getStudent() {
		return student;
	}
	@ManyToOne
    @JoinColumn(name="gameId", nullable=false)
	public Game getGame() {
		return game;
	}
	
	
	
}
