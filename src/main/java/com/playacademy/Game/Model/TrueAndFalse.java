package com.playacademy.Game.Model;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;

@Entity
public class TrueAndFalse extends Question {
	
	@JsonCreator
	public TrueAndFalse(){}
	public TrueAndFalse(long id){
		this.setQuestionId(id);
	}
	
	
}
