package com.playacademy.course.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playacademy.course.model.Course;
import com.playacademy.course.model.CourseAttendance;
import com.playacademy.user.controller.UserServicesAPI;
import com.playacademy.user.model.Student;
import com.playacademy.user.model.Teacher;
import com.playacademy.user.model.User;

@RestController
public class CourseManager {

	@Autowired
	CourseManagerAPI courseManagerAPI;
	@Autowired
	@Qualifier(value = "UBean")
	private UserServicesAPI userServices;

	
	// Course manipulation
	
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
	
	@RequestMapping(value = "/course/get", method = RequestMethod.GET)
	public Course getCourse(@RequestParam("courseID") long courseID) {
		return courseManagerAPI.getCourse(courseID);
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
	
	
	// Course and teacher
	@RequestMapping(value = "/courses/created/teacher/", method = RequestMethod.GET)
	public Set<Course> getCreatedCourse(@RequestParam("teacherId") long studentId) {
		User user = userServices.getUserByID(studentId);
		if (user == null || user instanceof Student) {
			return null;
		}
		return ((Teacher)user).getCreatedCourses();
	}

	
	// Course and student
	@RequestMapping(value = "/course/registered/students", method = RequestMethod.GET)
	public List<CourseAttendance> getRegisteredSrudents(@RequestParam("courseName") String courseName) {
		long courseId = courseManagerAPI.getCourseId(courseName);
		if (courseId == -1) {
			return null;
		}
		Course course = courseManagerAPI.getCourse(courseId);
		return courseManagerAPI.getStudentsInCourse(course);
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
		else ack = "You are already enrolled in this course";
		return ack;
	}

	@RequestMapping(value = "/courses/attendeted/student/", method = RequestMethod.GET)
	public List<CourseAttendance> getAttendedCourse(@RequestParam("studentId") long studentId) {
		User user = userServices.getUserByID(studentId);
		if (user == null || user instanceof Teacher) {
			return null;
		}
		return courseManagerAPI.getAttendedCourses(((Student)user));
	}

	@RequestMapping("/course/achievement/update/")
	public boolean updateAchievement(@RequestParam("newAchievement") long ach,
								  @RequestParam("studentId") long studentId,
								  @RequestParam("courseName") String courseName){
		
		long courseId = courseManagerAPI.getCourseId(courseName);
		User user =  userServices.getUserByID(studentId);
		if (courseId == -1 || user == null || user instanceof Teacher) {
			return false;
		}
		Course course = courseManagerAPI.getCourse(courseId);
		Student student = (Student) userServices.getUserByID(studentId);
		if(courseManagerAPI.isRegistered(student, course) != false){
			return false;
		}
		
		return courseManagerAPI.updateAchievement(course, student, ach);
	}
	
	
}
