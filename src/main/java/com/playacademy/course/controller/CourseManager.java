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
	
	@RequestMapping( value = "/course/create" , method=RequestMethod.GET)
	public boolean createCourse(@RequestParam("courseName") String courseName,
								@RequestParam("courseDescription") String courseDescription,
								@RequestParam("teacherId") long teacherId){
		
		if(courseManagerAPI.getCourseId(courseName) != -1 ){
			System.out.println("why "+teacherId);
			return false;
		}
		System.out.println("What "+teacherId);
		Course course = new Course (courseName,courseDescription);
		
		Teacher creator = (Teacher) userServices.getUserByID(teacherId);
		creator.addCourse(course);
		return courseManagerAPI.addCourse(course);
	}
	

	
	@RequestMapping( value = "/course/attend" , method=RequestMethod.GET)
	public boolean attendCourse(@RequestParam("courseName") String courseName,
								@RequestParam("studentId") long studentId){
		long courseId = courseManagerAPI.getCourseId(courseName);
		if(courseId == -1 ){
			return false;
		}
		Course course = courseManagerAPI.getCourse(courseId);
		Student student = (Student) userServices.getUserByID(studentId);
		student.addCourse(course);
		course.addStudent(student);
		return courseManagerAPI.addCourse(course);
	}
	
	@RequestMapping( value = "/attendeted/courses/student/get" , method=RequestMethod.GET)
	public Set<Course> getAttendedCourse(@RequestParam("studentId") long studentId){
		Student student = (Student) userServices.getUserByID(studentId);
		if(student == null ){
			return null;
		}
		return student.getCourses();
	}
	
	@RequestMapping( value = "/course/registered/students/get" , method=RequestMethod.GET)
	public Set<Student> getRegisteredSrudents(@RequestParam("courseName") String courseName){
		long courseId = courseManagerAPI.getCourseId(courseName);
		if(courseId == -1 ){
			return null;
		}
		Course course = courseManagerAPI.getCourse(courseId);
		return course.getStudents();
	}
	
	@RequestMapping(value = "/course/get" , method=RequestMethod.GET)
	public Course getCourse(@RequestParam("courseID") long courseID){
		return courseManagerAPI.getCourse(courseID);
	}
	
	@RequestMapping(value = "/course/edit" , method=RequestMethod.GET)
	public boolean editCourse(
			@RequestParam("courseID") long courseID,
			@RequestParam("courseName") String courseName,
			@RequestParam("courseDescription") String courseDescription){
		
		Course c =new Course(courseID,courseName,courseDescription);
		return courseManagerAPI.addCourse(c);	
	}
	
	@RequestMapping(value = "/course/delete" , method=RequestMethod.GET)
	public boolean deleteCourse(@RequestParam("courseID") long courseID){
		return courseManagerAPI.removeCourse(courseID);
	}
	
	
}
