package com.example.rashwan.playacademy.Models;

public class Choice {

	private int choiceId;
	private String choice;
	private MCQ question;

	// Setters
	public void setChoiceId(int choiceId) {
		this.choiceId = choiceId;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public void setQuestion(MCQ question) {
		this.question = question;
	}

	// Getters
	public int getChoiceId() {
		return choiceId;
	}

	public String getChoice() {
		return choice;
	}

	public MCQ getQuestion() {
		return question;
	}

}
