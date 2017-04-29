package com.playacademy.course.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playacademy.course.model.Course;
import com.playacademy.user.controller.UserServicesController;
import com.playacademy.user.model.Student;
import com.playacademy.user.model.Teacher;
import com.playacademy.user.model.User;

@RestController
public class CourseAPI {

	@Autowired
	CourseController courseManagerAPI;
	@Autowired
	@Qualifier(value = "UBean")
	private UserServicesController userServices;

	
	// Course manipulation
	
	@RequestMapping(value = "/course/create", method = RequestMethod.GET)
	public Map<String,String>createCourse(@RequestParam("courseName") String courseName,
			@RequestParam("courseDescription") String courseDescription, @RequestParam("teacherId") long teacherId) {

		long courseId = courseManagerAPI.getCourseId(courseName);
		User user =  userServices.getUserByID(teacherId);
		String ack = "";
		Map <String,String> ac=new HashMap<>();
		
		if (courseId != -1 || user == null || user instanceof Student) {
			if(courseId != -1){
				ack = "Another course exists with the same name.";
			}else if(user == null){
				ack = "No user exist with that ID.";
			}else{
				ack = "This ID represents a student, students can only enroll in courses";
			}
			ac.put("ack", ack);
			return ac;
		}
		
		Course course = new Course(courseName, courseDescription);
		((Teacher)user).addCourse(course);
		if(courseManagerAPI.saveCourse(course ) == true){
			ack = "Course created successfully";
		}else ack = "Sorry something went wrong.";
		ac.put("ack", ack);
		return ac;

	}
	
	@RequestMapping(value = "/course/get", method = RequestMethod.GET)
	public Course getCourse(@RequestParam("courseID") long courseID) {
		return courseManagerAPI.getCourse(courseID);
	}
	
	@RequestMapping(value = "/course/getId", method = RequestMethod.GET)
	public Map<String,Integer> getCourse(@RequestParam("courseName") String courseName) {
		Map <String ,Integer> map=new HashMap<>();
		map.put("id", (int) courseManagerAPI.getCourseId(courseName));
		return map;
	}

	@RequestMapping(value = "/course/edit", method = RequestMethod.GET)
	public boolean editCourse(@RequestParam("courseID") long courseID, @RequestParam("courseName") String courseName,
			@RequestParam("courseDescription") String courseDescription) {

		Course c = courseManagerAPI.getCourse(courseID);
		c.setCourseName(courseName);
		c.setCourseName(courseDescription);
		return courseManagerAPI.saveCourse(c);
	}

	@RequestMapping(value = "/course/delete", method = RequestMethod.GET)
	public boolean deleteCourse(@RequestParam("courseID") long courseID) {
		return courseManagerAPI.removeCourse(courseID);
	}
	
	@RequestMapping(value = "/course/getAll", method = RequestMethod.GET)
	public Map<String,List<Course> > getAllCourses(){
		Map<String, List<Course>> courses=new HashMap<>();
		courses.put("courses", courseManagerAPI.getAllCourses());
		return courses;
	}
	
	// Course and teacher
	@RequestMapping(value = "/courses/created/teacher/", method = RequestMethod.GET)
	public Map<String,Set<Course> > getCreatedCourse(@RequestParam("teacherId") long teacherId) {
		User user = userServices.getUserByID(teacherId);
		if (user == null || user instanceof Student) {
			return null;
		}
		Map <String,Set<Course>> courses=new HashMap<>();
		courses.put("courses", ((Teacher)user).getCreatedCourses());
		return courses;
	}

	
	// Course and student
	
	
	
}
