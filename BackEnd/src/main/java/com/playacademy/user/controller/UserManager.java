package com.playacademy.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playacademy.user.model.Student;
import com.playacademy.user.model.Teacher;
import com.playacademy.user.model.User;

@RestController
public class UserManager {

	@Autowired
	@Qualifier(value = "UBean")
	UserServicesAPI userServices;

	@Autowired
	@Qualifier(value = "TBean")
	UserServicesAPI userTServices;

	@Autowired
	@Qualifier(value = "SBean")
	UserServicesAPI userSServices;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public User login(@RequestParam("email") String email, @RequestParam("password") String password) {
		User user = userServices.getUserByID(userServices.getUserID(email, password));
		return user;
	}
	
	@RequestMapping(value = "/user/delete", method = RequestMethod.GET)
	public boolean login(@RequestParam("id") long id) {
		return userServices.deleteUser(id);
	}

	@RequestMapping(value = "/student/register", method = RequestMethod.POST)
	public boolean registerStudent(@RequestBody Student student) {

		boolean confirmation = userSServices.addUser(student);
		return confirmation;
	}

	@RequestMapping(value = "/teacher/register", method = RequestMethod.POST)
	public boolean registerTeacher(@RequestBody Teacher teacher) {
		// userServices = new TeacherService();
		System.out.println(teacher.getAge());
		boolean confirmation = userTServices.addUser(teacher);
		return confirmation;
	}

}