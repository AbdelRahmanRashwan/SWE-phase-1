package com.playacademy.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playacademy.course.model.*;
import com.playacademy.user.model.Student;

@Service
public class CourseManagerAPI {
	
	@Autowired
	CourseRepo courseRepo;
	
	public boolean saveCourse(Course c){
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

	public boolean attend(Student student, long courseId) {
		Course course = getCourse(courseId);
		student.addCourse(course);
		course.addStudent(student);
		
		return saveCourse(course);
	}
}
