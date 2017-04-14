package com.playacademy.Game.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="choices")
public class Choice {
	
	
	private long choiceId;
	
	@Column(name="choice")
	private String choice;
	
	@JsonIgnore
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
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getChoiceId() {
		return choiceId;
	}
	public String getChoice() {
		return choice;
	}
	
	@ManyToOne
    @JoinColumn(name="questionId", nullable=false)
	public MCQ getQuestion() {
		return question;
	}
	
	
	
	
}
