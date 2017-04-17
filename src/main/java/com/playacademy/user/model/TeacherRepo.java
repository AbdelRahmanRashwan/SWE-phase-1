package com.playacademy.user.model;

public interface TeacherRepo extends UserBaseRepo<Teacher>{

	public Teacher findByEducationalMail(String educationalMail);
}
