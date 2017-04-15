package com.playacademy.user.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;



@Entity
public class Student extends User{

	
	
	private Set<ScoreSheet> scores;
	
	
	public Student(){
		type="Student";
	}
	public Student(long id){
		this.setUserId(id);
	}
	
	public void setScores(Set<ScoreSheet> scores) {
		this.scores = scores;
	}
	
	public void addScore(ScoreSheet score) {
		score.setStudent(this);
		scores.add(score);
	}
	
	// Relations
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="student")
	public Set<ScoreSheet> getScores() {
		return scores;
	}
	
}
