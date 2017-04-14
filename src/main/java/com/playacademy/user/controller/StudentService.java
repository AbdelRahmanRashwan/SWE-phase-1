package com.playacademy.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playacademy.user.model.*;
@Component(value="SBean")
public class StudentService extends UserServicesAPI{
	
	@Autowired
	StudentRepo studentRepo;
	
	@Override
	protected boolean verifyEmail(String email){
		if (email.contains("@"))
			return true;
		return false;
	}

	@Override
	public boolean addUser(User user) {
		System.out.println(user.getEmail());
		long userID=getUserID(user.getEmail());
		System.out.println("Why1");
		if (!verifyEmail( user.getEmail())||userID!=-1){
			System.out.println("Why2 "+userID);
			return false;
		}
		
		System.out.println("Why");
		Student confirmation=studentRepo.save(((Student) user));
		return confirmation.getEmail()==user.getEmail()? true:false;
	}
	
}
