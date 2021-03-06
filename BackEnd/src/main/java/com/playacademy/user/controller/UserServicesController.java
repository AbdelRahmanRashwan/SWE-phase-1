package com.playacademy.user.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.playacademy.user.model.User;
import com.playacademy.user.model.UserRepository;
import com.playacademy.Notification.Notification;;

@Service
@Component(value = "UBean")
public class UserServicesController {

	@Autowired
	UserRepository userBaseRepo;

	// public String getUserType(String email,String password){
	// User temp=userBaseRepo.findByEmailAndPassword(email, password);
	// return temp.getUserType();
	// }

	// takes the user and adds the user data in the user base repository

	protected boolean verifyEmail(String email) {
		User user=userBaseRepo.findByEmail(email);
		if (user==null)
			return true;
		return false;
	}

	public long addUser(User user) {
		return -1;
	}
	
	public boolean deleteUser(long userId) {
		try{
			userBaseRepo.delete(userId);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	// gets user by ID from the user base repository
	public User getUserByID(long ID) {
		User user = userBaseRepo.findOne(ID);
		return user;
	}

	// to be implemented after knowing how to update in database
	public boolean editUser(User user) {
		return true;
	}

	// gets user ID from the user base repository by email and password
	public long getUserID(String email, String password) {
		User returns = userBaseRepo.findByEmailAndPassword(email, password);
		return (returns == null ? -1 : returns.getUserId());
	}

	// gets user ID from the user base repository by email
	public long getUserID(String email) {
		User returns = userBaseRepo.findByEmail(email);
		return (returns == null ? -1 : returns.getUserId());
	}
	
	public Set<Notification> getUserNotification(long ID){
		User u = userBaseRepo.findOne(ID);
		return u.getNotifications();
	}
	
}
