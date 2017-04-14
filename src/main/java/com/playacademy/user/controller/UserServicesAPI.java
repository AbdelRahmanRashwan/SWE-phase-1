package com.playacademy.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.playacademy.user.model.User;
import com.playacademy.user.model.UserRepository;
@Service
@Component(value="UBean")
public  class UserServicesAPI {
	
	@Autowired
	UserRepository userBaseRepo;
	
	
	
	
//	public String getUserType(String email,String password){
//		User temp=userBaseRepo.findByEmailAndPassword(email, password);
//		return temp.getUserType();
//	}
	
	//takes the user and adds the user data in the user base repository
	
	protected boolean verifyEmail(String email){
		return false;
	}
	
	public  boolean addUser(User user){
		return false;
	}
	
	// gets user by ID from the user base repository
	public User getUserByID(long ID){
		User user =userBaseRepo.findOne(ID);
//		if (user instanceof Teacher)
//	        user.setUserType("Teahcer");
//	      else if (user instanceof Student)
//	        user.setUserType ("Student");
		return user;
	}
	
	//to be implemented after knowing how to update in database
	public boolean editUser(User user){
		return true;
	}
	
	//gets user ID from the user base repository by email and password
	public long getUserID(String email,String password){
		User returns=userBaseRepo.findByEmailAndPassword(email, password);
		return returns.getID();
	}
	
	//gets user ID from the user base repository by email
	public long getUserID(String email){
		System.out.println("hi " + userBaseRepo);
		User returns =userBaseRepo.findByEmail(email);
		return (returns == null? -1:returns.getID());
	}
}
