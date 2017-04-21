package com.example.rashwan.playacademy.Models;

public class Choice {

	private long choiceId;
	private String choice;
	private MCQ question;

	// Setters
	public void setChoiceId(long choiceId) {
		this.choiceId = choiceId;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public void setQuestion(MCQ question) {
		this.question = question;
	}

	// Getters
	public long getChoiceId() {
		return choiceId;
	}

	public String getChoice() {
		return choice;
	}

	public MCQ getQuestion() {
		return question;
	}

}
