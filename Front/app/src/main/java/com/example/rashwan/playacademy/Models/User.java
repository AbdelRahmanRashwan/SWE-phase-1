package com.example.rashwan.playacademy.Models;

public abstract class User {

	protected long userId;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected long age;
	protected String password;
	protected String type;
	protected String verificationCode;
	protected boolean verified;

	
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
}
