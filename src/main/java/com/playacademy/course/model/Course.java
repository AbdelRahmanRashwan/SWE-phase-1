package com.playacademy.course.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playacademy.Game.Model.Game;
import com.playacademy.user.model.*;

@Entity
@Table(name = "courses")
public class Course {

	private long courseId;

	@Column(name = "courseName")
	private String courseName;

	@Column(name = "courseDescription")
	private String courseDescription;
	
	@JsonIgnore
	private Set<Game> games;
	
	private Teacher creator;
	
	
	@JsonIgnore
    private Set<Student> students;

	public Course() {

	}

	public Course(String courseName, String courseDescription) {
		this.courseName = courseName;
		this.courseDescription = courseDescription;
	}

	public Course(long courseID, String courseName, String courseDescription) {
		this.courseId = courseID;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
	}

	// Setters
	public void setCourseId(long courseID) {
		this.courseId = courseID;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	
	public void setGames(Set<Game> games) {
		this.games = games;
	}
	
	public void setCreator(Teacher creator) {
		this.creator = creator;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	
	// add
	public void addGame(Game game) {
		game.setCourse(this);
		games.add(game);
	}
	// Getters

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getCourseId() {
		return courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	// Relations
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "course")
	public Set<Game> getGames() {
		return games;
	}
	@ManyToMany(mappedBy="attendedCourses", cascade=CascadeType.ALL)

	public Set<Student> getStudents() {
		return students;
	}
	@ManyToOne
    @JoinColumn(name="creatorId", nullable=false)
	public Teacher getCreator() {
		return creator;
	}

	public void addStudent(Student student) {
		students.add(student);
	}

}
