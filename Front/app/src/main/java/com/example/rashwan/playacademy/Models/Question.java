package com.example.rashwan.playacademy.Models;

public class Question {
	

	public Question(){	}
	public Question(Game game){
		this.game = game;
	}
	
	private long questionId;
    private Game game;
	private String question;
	private String answer;
	
	
	//Setters	
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public void setQuestionId(long questionID) {
		this.questionId = questionID;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
	// Getters

	public long getQuestionId() {
		return questionId;
	}
	public String getQuestion() {
		return question;
	}
	public String getAnswer() {
		return answer;
	}
	
	// Relations
	public Game getGame() {
		return game;
	}
	
}
