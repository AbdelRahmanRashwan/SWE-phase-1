package com.playacademy.user.model;

import javax.persistence.Entity;


@Entity
public class Student extends User{

	public Student(){
		type="Student";
	}
	public Student(long id){
		this.setID(id);
	}
	
}
