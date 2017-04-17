package com.playacademy.course.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import com.playacademy.course.model.Course;
import com.playacademy.user.controller.UserServicesAPI;
import com.playacademy.user.model.*;

@RestController
public class CourseManager {
	@Autowired
	CourseManagerAPI courseManagerAPI;
	@Autowired
	@Qualifier(value = "UBean")
	private UserServicesAPI userServices;

	@RequestMapping(value = "/course/create", method = RequestMethod.GET)
	public String createCourse(@RequestParam("courseName") String courseName,
			@RequestParam("courseDescription") String courseDescription, @RequestParam("teacherId") long teacherId) {

		long courseId = courseManagerAPI.getCourseId(courseName);
		User user =  userServices.getUserByID(teacherId);
		String ack = "";
		
		if (courseId != -1 || user == null || user instanceof Student) {
			if(courseId != -1){
				ack = "Another course exists with the same name.";
			}else if(user == null){
				ack = "No user exist with that ID.";
			}else{
				ack = "This ID represents a student, students can only enroll in courses";
			}
			return ack;
		}
		
		Course course = new Course(courseName, courseDescription);
		((Teacher)user).addCourse(course);
		if(courseManagerAPI.saveCourse(course ) == true){
			ack = "Course created successfully";
		}else ack = "Sorry something went wrong.";
		return ack;
	}

	@RequestMapping(value = "/course/attend", method = RequestMethod.GET)
	public String attendCourse(@RequestParam("courseName") String courseName,
			@RequestParam("studentId") long studentId) {
		long courseId = courseManagerAPI.getCourseId(courseName);
		User user =  userServices.getUserByID(studentId);
		String ack = "";
		if (courseId == -1 || user == null || user instanceof Teacher) {
			if(courseId == -1){
				ack = "No course with that name.";
			}else if(user == null){
				ack = "No user exist with that ID.";
			}else{
				ack = "This ID represents a teacher, teacher can only creat courses";
			}
			return ack;
		}
		if(courseManagerAPI.attend((Student)user, courseId) == true)
			ack = "Enrolled succssessfully.";
		else ack = "Sorry something went wrong.";
		return ack;
	}

	@RequestMapping(value = "/courses/attendeted/student/", method = RequestMethod.GET)
	public Set<Course> getAttendedCourse(@RequestParam("studentId") long studentId) {
		User user = userServices.getUserByID(studentId);
		if (user == null || user instanceof Teacher) {
			return null;
		}
		return ((Student)user).getAttendedCourses();
	}
	
	@RequestMapping(value = "/courses/created/teacher/", method = RequestMethod.GET)
	public Set<Course> getCreatedCourse(@RequestParam("teacherId") long studentId) {
		User user = userServices.getUserByID(studentId);
		if (user == null || user instanceof Student) {
			return null;
		}
		return ((Teacher)user).getCreatedCourses();
	}

	@RequestMapping(value = "/course/registered/students/get", method = RequestMethod.GET)
	public Set<Student> getRegisteredSrudents(@RequestParam("courseName") String courseName) {
		long courseId = courseManagerAPI.getCourseId(courseName);
		if (courseId == -1) {
			return null;
		}
		Course course = courseManagerAPI.getCourse(courseId);
		return course.getStudents();
	}
	@RequestMapping(value = "/course/get", method = RequestMethod.GET)
	public Course getCourse(@RequestParam("courseID") long courseID) {
		return courseManagerAPI.getCourse(courseID);
	}

	@RequestMapping(value = "/course/edit", method = RequestMethod.GET)
	public boolean editCourse(@RequestParam("courseID") long courseID, @RequestParam("courseName") String courseName,
			@RequestParam("courseDescription") String courseDescription) {

		Course c = new Course(courseID, courseName, courseDescription);
		return courseManagerAPI.saveCourse(c);
	}

	@RequestMapping(value = "/course/delete", method = RequestMethod.GET)
	public boolean deleteCourse(@RequestParam("courseID") long courseID) {
		return courseManagerAPI.removeCourse(courseID);
	}
}
