package com.playacademy.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playacademy.user.model.Teacher;
import com.playacademy.user.model.TeacherRepo;
import com.playacademy.user.model.User;

@Component(value = "TBean")
public class TeacherService extends UserServicesAPI {
	@Autowired
	TeacherRepo teacherRepo;

	@Override
	protected boolean verifyEmail(String email) {
		if (email.contains("@stud.fci-cu.edu.eg"))
			return true;
		return false;
	}

	public boolean addUser(User user) {
		System.out.println(user.getEmail());
		long userID = getUserID(user.getEmail());
		long userID2 = getTeacherIDbyEducationalMail(((Teacher) user).getEducationalMail());
		if (!verifyEmail(((Teacher) user).getEducationalMail()) || userID != -1 || userID2 != -1) {
			System.out.println(userID+" "+userID2 );
			return false;
		}

		Teacher confirmation = teacherRepo.save(((Teacher) user));
		return confirmation.getEmail() == user.getEmail() ? true : false;
	}

	public boolean editUser(Teacher user) {
		return true;
	}

	public long getTeacherIDbyEducationalMail(String email) {
		Teacher t = teacherRepo.findByEducationalMail(email);
		if (t == null) {
			return -1;
		}
		return t.getUserId();
	}
}
