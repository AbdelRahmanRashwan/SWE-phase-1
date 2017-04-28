package com.playacademy.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playacademy.user.model.Teacher;
import com.playacademy.user.model.TeacherRepo;
import com.playacademy.user.model.User;

@Component(value = "TBean")
public class TeacherService extends UserServicesController {
	@Autowired
	TeacherRepo teacherRepo;

	public long addUser(User user) {
		long userID = getUserID(user.getEmail());
		long userID2 = getTeacherIDbyEducationalMail(((Teacher) user).getEducationalMail());
		if (userID != -1 || userID2 != -1) {
			return -1;
		}

		Teacher confirmation = teacherRepo.save(((Teacher) user));
		return confirmation.getUserId();
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
