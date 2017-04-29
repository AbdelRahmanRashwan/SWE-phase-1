package com.playacademy.courseattendance.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playacademy.course.controller.CourseController;
import com.playacademy.course.model.Course;
import com.playacademy.courseattendance.model.CourseAttendance;
import com.playacademy.user.controller.UserServicesController;
import com.playacademy.user.model.Student;
import com.playacademy.user.model.Teacher;
import com.playacademy.user.model.User;

@RestController
public class CourseAttendanceAPI {

	
	@Autowired
	CourseController courseManagerAPI;
	
	@Autowired
	CourseAttendanceController courseAttendanceController;
	
	@Autowired
	@Qualifier(value = "UBean")
	private UserServicesController userServices;
	
	@RequestMapping(value = "/course/registered/students", method = RequestMethod.GET)
	public List<CourseAttendance> getRegisteredSrudents(@RequestParam("courseName") String courseName) {
		long courseId = courseManagerAPI.getCourseId(courseName);
		if (courseId == -1) {
			return null;
		}
		Course course = courseManagerAPI.getCourse(courseId);
		return courseAttendanceController.getStudentsInCourse(course);
	}
	
	@RequestMapping(value = "/course/attend", method = RequestMethod.GET)
	public Map<String,String> attendCourse(@RequestParam("courseName") String courseName,
			@RequestParam("studentId") long studentId) {
		long courseId = courseManagerAPI.getCourseId(courseName);
		User user =  userServices.getUserByID(studentId);
		String ack = "";
		Map <String,String> ac=new HashMap<>();
		if (courseId == -1 || user == null || user instanceof Teacher) {
			if(courseId == -1){
				ack = "No course with that name.";
			}else if(user == null){
				ack = "No user exist with that ID.";
			}else{
				ack = "This ID represents a teacher, teacher can only creat courses";
			}
			ac.put("ack", ack);
			return ac;
		}
		Course course = courseManagerAPI.getCourse(courseId);
		if(courseAttendanceController.attend((Student)user, course) == true)
			ack = "Enrolled succssessfully.";
		else ack = "You are already enrolled in this course";
		ac.put("ack", ack);
		return ac;
	}

	@RequestMapping(value = "/courses/attendeted/student", method = RequestMethod.GET)
	public Map<String,List<CourseAttendance>> getAttendedCourse(@RequestParam("studentId") long studentId) {
		User user = userServices.getUserByID(studentId);
		if (user == null || user instanceof Teacher) {
			return null;
		}
		Map<String,List<CourseAttendance>> courses=new HashMap<>();
		courses.put("courses", courseAttendanceController.getAttendedCourses(((Student)user)));
		return courses;
	}
	
	@RequestMapping(value = "/courses/enrollment", method = RequestMethod.GET)
	public Map<String,Boolean> getEnrollment(@RequestParam("studentId") long studentId,@RequestParam("courseName")String courseName ) {
		Map <String, Boolean> map=new HashMap<>();
		Student student=(Student) userServices.getUserByID(studentId);
		Course course=courseManagerAPI.getCourse(courseManagerAPI.getCourseId(courseName));
		map.put("ack", courseAttendanceController.isRegistered(student, course));
		return map;
	}
	@RequestMapping("/course/achievement/update/")
	public Boolean updateAchievement(@RequestParam("newAchievement") long ach,
								  @RequestParam("studentId") long studentId,
								  @RequestParam("courseName") String courseName){
		
		long courseId = courseManagerAPI.getCourseId(courseName);
		User user =  userServices.getUserByID(studentId);
		if (courseId == -1 || user == null || user instanceof Teacher) {
			return false;
		}
		Course course = courseManagerAPI.getCourse(courseId);
		Student student = (Student) userServices.getUserByID(studentId);
		if(courseAttendanceController.isRegistered(student, course) != false){
			return false;
		}
		
		return courseAttendanceController.updateAchievement(course, student, ach);
	}
}
