package com.playacademy.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playacademy.course.model.*;

@Service
public class CourseManagerAPI {
	
	@Autowired
	CourseRepo courseRepo;
	
	public boolean addCourse(Course c){
		if(courseRepo.save(c)==null)
			return false;
		return true;
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
