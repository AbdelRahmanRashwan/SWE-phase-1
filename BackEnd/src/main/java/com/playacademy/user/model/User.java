package com.playacademy.user.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.playacademy.Notification.Notification;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playacademy.comments.Comment;



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
	protected long age;

	@Column(name = "password")
	protected String password;

	@Column(name = "type")
	protected String type;
	
	@Column(name = "verification_code")
	protected String verificationCode;
	
	@Column(name = "verified")
	protected boolean verified;

	protected Set<Notification> notifications;
	
	@JsonIgnore
	protected Set<Comment> comments;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="commentor")
	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getType() {
		return type;
	}

	public User() {
	}

	public void setType(String userType) {
		this.type = userType;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "receiver")
	public Set<Notification> getNotifications() {
		return notifications;
	}
	
	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}
}
