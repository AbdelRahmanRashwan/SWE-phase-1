package com.example.rashwan.playacademy.Models;

import java.io.Serializable;

public class Choice implements Serializable{

	private int choiceId;
	private String choice;

    public Choice(){}
	public Choice(String choice) {
		this.choice = choice;
	}

	// Setters
	public void setChoiceId(int choiceId) {
		this.choiceId = choiceId;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	// Getters
	public int getChoiceId() {
		return choiceId;
	}

	public String getChoice() {
		return choice;
	}

}
