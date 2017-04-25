package com.playacademy.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playacademy.user.model.*;

@Component(value = "SBean")
public class StudentService extends UserServicesAPI {

	@Autowired
	StudentRepo studentRepo;

	@Override
	public long addUser(User user){
		long userID=getUserID(user.getEmail());
		if (!verifyEmail(user.getEmail())||userID!=-1){
			return -1;
		}
		Student confirmation=studentRepo.save((Student)user);
		return confirmation.getUserId();
	}
}
