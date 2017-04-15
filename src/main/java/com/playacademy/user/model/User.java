package com.playacademy.user.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;

@Entity
@Inheritance
@DiscriminatorColumn(name = "user_type")
@Table(name = "users")

public abstract class User {
	
	protected long userId;
	
	@Column(name = "firstName")
	protected String firstName;
	
	@Column(name = "lastName")
	protected String lastName;
	
	@Column(name = "email")
	protected String email;
	
	@Column(name = "age")
	protected String age;
	
	@Column(name = "password")
	protected String password;
	
	@Column(name = "Type")
	protected String type;
	
	public String getType() {
		return type;
	}
	public User() {
	}
	public void setType(String userType) {
		this.type = userType;
	}
	
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	public long getUserId() {
		return userId;
	}
	public void setUserId(long id) {
		userId = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
