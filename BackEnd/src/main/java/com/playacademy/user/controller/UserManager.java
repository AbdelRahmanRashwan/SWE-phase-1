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
	UserServicesAPI teacherServices;

	@Autowired
	@Qualifier(value = "SBean")
	UserServicesAPI studentService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User login(@RequestBody Student user) {
		User userRet = userServices.getUserByID(userServices.getUserID(user.getEmail(), user.getPassword()));
		return userRet;
	}
	
	@RequestMapping(value = "/user/delete", method = RequestMethod.GET)
	public boolean login(@RequestParam("id") long id) {
		return userServices.deleteUser(id);
	}

	@RequestMapping(value = "/student/register", method = RequestMethod.POST)
	public String registerStudent(@RequestBody Student student) {
		int  id = (int) studentService.addUser(student);
		String ret="{\"confirmation\":"+id+"}";
		
		return ret;
	}

	@RequestMapping(value = "/teacher/register", method = RequestMethod.POST)
	public int registerTeacher(@RequestBody Teacher teacher) {
		// userServices = new TeacherService();
		int  id =(int) teacherServices.addUser(teacher);
		return id;
	}

}
