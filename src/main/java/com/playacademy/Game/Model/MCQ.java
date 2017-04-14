package com.playacademy.Game.Model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class MCQ extends Question {
	
//	private String[] choices;

	public MCQ(){
	}
	
	@JsonCreator
	public MCQ(@JsonProperty("choices") Set<Choice> choices){
		this.choices = choices;
	}
	public MCQ(long id){
		this.setQuestionID(id);
	}
	
	
//	@Transient
	private Set<Choice> choices;
	
	
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
