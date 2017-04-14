package com.playacademy.Game.Model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance
@DiscriminatorColumn(name = "question_type")
@Table(name = "questions")
public abstract class Question {
	
	
	private long questionId;
	@JsonIgnore
    private Game game;
	
	@Column(name="question")
	private String question;
	
	@Column(name="answer")
	private String answer;
//	private Set<Choice> choices;
	
	public Question(){	}
	
	public Question(Game game){
		this.game = game;
	}
	
	//Setters	
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public void setQuestionID(long questionID) {
		this.questionId = questionID;
	}
	public void setGame(Game game) {
		this.game = game;
	}
//	public void addChoice(Choice choice) {
//		choice.setQuestion(this);
//		choices.add(choice);
//	}
	
	// Getters
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getQuestionID() {
		return questionId;
	}
	@ManyToOne
    @JoinColumn(name="gameId", nullable=false)
	public Game getGame() {
		return game;
	}
	
	public String getQuestion() {
		return question;
	}
	public String getAnswer() {
		return answer;
	}
	
	
//	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="question")
	
	
}
