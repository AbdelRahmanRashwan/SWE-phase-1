package com.playacademy.Game.Model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance
@DiscriminatorColumn(name = "question_type")
@Table(name = "questions")
public abstract class Question {
	
	
	@JsonCreator
	public Question(){	}
	
	public Question(Game game){
		this.game = game;
	}
	
	private long questionId;
	@JsonIgnore
    private Game game;
	
	@Column(name="question")
	private String question;
	
	@Column(name="answer")
	private String answer;
	
	
	//Setters	
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public void setQuestionId(long questionID) {
		this.questionId = questionID;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
	// Getters
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getQuestionId() {
		return questionId;
	}
	public String getQuestion() {
		return question;
	}
	public String getAnswer() {
		return answer;
	}
	
	// Relations
	@ManyToOne
    @JoinColumn(name="gameId", nullable=false)
	public Game getGame() {
		return game;
	}
	
}
