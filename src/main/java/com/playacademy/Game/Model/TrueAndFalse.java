package com.playacademy.Game.Model;

import javax.persistence.Entity;

@Entity
public class TrueAndFalse extends Question {
	
	
	public TrueAndFalse(){}
	public TrueAndFalse(long id){
		this.setQuestionID(id);
	}
	
	
}
