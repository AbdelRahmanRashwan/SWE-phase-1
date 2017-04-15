package com.playacademy.course.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Courses")
public class Course {
	
	@Id
	@Column(name = "courseID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long courseID;

	@Column(name = "courseName")
	private String courseName;

	@Column(name = "courseDescription")
	private String courseDescription;

//	private ArrayList<Game> games;
//	private ArrayList<Student> students;
//	private ArrayList<Teacher>  teachers;
	
	public Course(){
		
	}
	
	public Course(String courseName, String courseDescription) {
		this.courseName=courseName;
		this.courseDescription = courseDescription;
	}
	
	public Course(long courseID, String courseName, String courseDescription) {
		this.courseID = courseID;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
	}

	public long getCourseID() {
		return courseID;
	}
	public void setCourseID(long courseID) {
		this.courseID = courseID;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
//	public ArrayList<Game> getGames() {
//		return games;
//	}
//	public void setGames(ArrayList<Game> games) {
//		this.games = games;
//	}
//	public ArrayList<Student> getStudents() {
//		return students;
//	}
//	public void setStudents(ArrayList<Student> students) {
//		this.students = students;
//	}
//	public ArrayList<Teacher> getTeatchers() {
//		return teachers;
//	}
//	public void setTeatchers(ArrayList<Teacher> teatchers) {
//		this.teachers = teatchers;
//	}
	
	
}
