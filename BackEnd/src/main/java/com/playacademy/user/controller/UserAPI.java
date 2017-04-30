package com.playacademy.user.controller;

import java.util.HashMap;
import java.util.Map;

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
public class UserAPI {

	@Autowired
	@Qualifier(value = "UBean")
	UserServicesController userServices;

	@Autowired
	@Qualifier(value = "TBean")
	UserServicesController teacherServices;

	@Autowired
	@Qualifier(value = "SBean")
	UserServicesController studentServices;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String,Object> login(@RequestBody Student user) {
		Map<String,Object> returnUser = new HashMap<String,Object>();
		String error = "";
		long userId = userServices.getUserID(user.getEmail());
		User userData= userServices.getUserByID(userId);
		if(userId == -1){
			error = "This Email does not exist";
		}else if(!userData.getPassword().equals(user.getPassword())){
			error = "Incorrect password";
		}
		//else if(!userData.isVerified()){
		//	error = "Not verified";
		//}
		if(error.length()>1){
			returnUser.put("Error", error);
		}else{
			returnUser.put("userData", userData);
		}
		
		return returnUser;
	}
	
	@RequestMapping(value = "/user/delete", method = RequestMethod.GET)
	public boolean delete(@RequestParam("id") long id) {
		return userServices.deleteUser(id);
	}

	@RequestMapping(value = "/student/register", method = RequestMethod.POST)
	public Map<String,Object> registerStudent(@RequestBody Student student) {
		Map<String,Object> returnData=new HashMap<>();
		long userId = userServices.getUserID(student.getEmail());
		if(userId != -1){
			returnData.put("Error", "This Email already exists"); 
		}else{
			userId = studentServices.addUser(student);
			returnData.put("userId", userId);
		}
		return returnData;
	}

	@RequestMapping(value = "/teacher/register", method = RequestMethod.POST)
	public Map<String, Object> registerTeacher(@RequestBody Teacher teacher) {
		Map<String,Object> returnData=new HashMap<>();
		long userId = userServices.getUserID(teacher.getEmail());
		if(userId != -1){
			returnData.put("Error", "This Email already exists"); 
		}else{
			userId = teacherServices.addUser(teacher);
			returnData.put("userId", userId);
		}
		return returnData;
	}

}
