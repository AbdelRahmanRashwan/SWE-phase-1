package com.example.rashwan.playacademy.Models;

import java.util.Iterator;
import java.util.Set;

public class MCQ extends Question {
	

	public MCQ(){
	}
	
	public MCQ( Set<Choice> choices){
		this.choices = choices;
	}
	public MCQ(long id){
		this.setQuestionId(id);
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
	// Relations
	public Set<Choice> getChoices() {
		return choices;
	}
}
