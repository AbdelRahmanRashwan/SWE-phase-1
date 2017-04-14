package com.playacademy.Game.Model;


import java.util.Set;

import javax.persistence.*;

@Entity
public class MCQ extends Question {
	
//	private String[] choices;

	
//	@Transient
	private Set<Choice> choices;
	
	public MCQ(){}
	public MCQ(long id){
		this.setQuestionID(id);
	}
	// Setters
//	public void setChoices(String[] choices) {
//		this.choices = choices;
//	}
	
	public void setChoices(Set<Choice> choices) {
		this.choices = choices;
	}
	
	public void addChoice(Choice choice) {
		choice.setQuestion(this);
		choices.add(choice);
	}
	
	// Getters
//	public String[] getChoices() {
//		return choices;
//	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="question")
	public Set<Choice> getChoices() {
		return choices;
	}
	
	
	
	
}
