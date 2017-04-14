package com.playacademy.game.model;


import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class MCQ extends Question {
	

	public MCQ(){
	}
	
	@JsonCreator
	public MCQ(@JsonProperty("choices") Set<Choice> choices){
		this.choices = choices;
	}
	public MCQ(long id){
		this.setQuestionID(id);
	}
	
	
	private Set<Choice> choices;
	
	
	// Setters
	
	public void setChoices(Set<Choice> choices) {
		this.choices = choices;
		if(choices != null){
			Iterator<Choice> it = this.choices.iterator();
			while(it.hasNext()){
				it.next().setQuestion(this);
			}
		}
	}
	
	public void addChoice(Choice choice) {
		choice.setQuestion(this);
		choices.add(choice);
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="question")
	public Set<Choice> getChoices() {
		return choices;
	}
}
