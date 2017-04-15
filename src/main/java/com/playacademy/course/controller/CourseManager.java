package com.playacademy.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.playacademy.course.model.Course;

@RestController
public class CourseManager {
	
	@Autowired
	CourseManagerAPI courseManagerAPI;
	
	@RequestMapping( value = "/createCourse" , method=RequestMethod.GET)
	public boolean createCourse(@RequestParam("courseName") String courseName,@RequestParam("courseDescription") String courseDescription){
		Course c = new Course (courseName,courseDescription);
		boolean z =courseManagerAPI.addCourse(c);
		return z;
	}
	

//	@RequestMapping(value = "/createGame" , method=RequestMethod.GET)
//	public Game addGame(@RequestParam("courseID") String courseID ,Game g){
//		Course c =CourseManagerAPI.getCourse(courseID);
////		GameManager.createGame(Name,courseID,Questions);
//		c.getGames().add(g);
//		return g;
//	}

	

	
	@RequestMapping(value = "/getCourse" , method=RequestMethod.GET)
	public Course getCourse(@RequestParam("courseID") long courseID){
		return courseManagerAPI.getCourse(courseID);
	}
	
	@RequestMapping(value = "/editCourse" , method=RequestMethod.GET)
	public boolean editCourse(
			@RequestParam("courseID") long courseID,
			@RequestParam("courseName") String courseName,
			@RequestParam("courseDescription") String courseDescription){
		
		Course c =new Course(courseID,courseName,courseDescription);
		return courseManagerAPI.addCourse(c);	
	}
	
	@RequestMapping(value = "/deleteCourse" , method=RequestMethod.GET)
	public boolean deleteCourse(@RequestParam("courseID") long courseID){
		return courseManagerAPI.removeCourse(courseID);
	}
	
	
}
