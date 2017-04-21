package com.example.rashwan.playacademy.Models;

public class GameSheet {
	
	
	private long scoreId;
	private long score;
	private long rate;
	private Student student;
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
	public Student getStudent() {
		return student;
	}
	public Game getGame() {
		return game;
	}
	
	
	
}
