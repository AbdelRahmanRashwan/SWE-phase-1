package com.playacademy.user.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Null;

//import course.Course;

@Entity
public class Teacher extends User{
	
	@Column(name = "educationalMail")
	private String educationalMail;
	
	
//	private Set <Course> createCourses;
//	
//	public void addCourse(Course course){
//		createCourses.add(course);
//	}
//	
//	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="Teacher")
//	public Set<Course> getCreateCourses() {
//		return createCourses;
//	}
//
//	public void setCreateCourses(Set<Course> createCourses) {
//		this.createCourses = createCourses;
//	}

	public Teacher(){
		type="Teacher";
	}
	
	public Teacher(long id){
		this.setID(id);
	}

	public String getEducationalMail() {
		return educationalMail;
	}

	public void setEducationalMail(String educationalMail) {
		this.educationalMail = educationalMail;
	}
	
}
