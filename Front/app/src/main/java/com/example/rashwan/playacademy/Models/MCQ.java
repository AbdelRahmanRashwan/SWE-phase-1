package com.example.rashwan.playacademy.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class MCQ extends Question {

	private ArrayList<Choice> choices;
	public MCQ(){
	}
	
	public MCQ( ArrayList<Choice> choices){
		this.choices = choices;
	}
	public MCQ(long id){
		this.setQuestionId(id);
	}

	// Setters
	
	public void setChoices(ArrayList<Choice> choices) {
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
	public ArrayList<Choice> getChoices() {
		return choices;
	}
}
