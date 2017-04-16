package com.playacademy.user.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playacademy.course.model.Course;
import com.playacademy.game.model.ScoreSheet;



@Entity
public class Student extends User{

	
	@JsonIgnore
	private Set<ScoreSheet> scores;
	
	
    @JsonIgnore
	private Set<Course> courses;
	
	
	public Student(){
		type="Student";
	}
	public Student(long id){
		this.setUserId(id);
	}
	
	
	// Setters
	public void setScores(Set<ScoreSheet> scores) {
		this.scores = scores;
	}
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
	
	// add
	public void addScore(ScoreSheet score) {
		score.setStudent(this);
		scores.add(score);
	}
	
	// Relations
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="student")
	public Set<ScoreSheet> getScores() {
		return scores;
	}
	
	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="course_attendance")
	public Set<Course> getCourses() {
		return courses;
	}
	public void addCourse(Course course) {
		courses.add(course);
	}
	
	
}
