package com.playacademy.course.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playacademy.course.model.*;
import com.playacademy.user.model.Observer;

@Service
public class CourseController {
	
	@Autowired
	CourseRepo courseRepo;
	
	ArrayList<Observer> newGameObservers;
	
	public boolean saveCourse(Course c){
		if(courseRepo.save(c)==null)
			return false;
		return true;
	}
	
	public List<Course> getAllCourses(){
		return (List<Course>) courseRepo.findAll();
	}
	
	public Course getCourse(long courseID){
		return courseRepo.findOne(courseID);
	}
	
	public boolean removeCourse(long courseID){
		try{
			courseRepo.delete(courseID);	
		}catch(Exception e){
			return false;
		}
		return true;
	}

	public long getCourseId(String courseName) {
		Course course = courseRepo.findByCourseName(courseName);
		if(course == null)
			return -1;
		return course.getCourseId();
	}

	
	
}
