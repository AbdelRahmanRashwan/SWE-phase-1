package com.playacademy.course.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playacademy.course.model.*;
import com.playacademy.user.model.Student;

@Service
public class CourseManagerAPI {
	
	@Autowired
	CourseRepo courseRepo;
	
	@Autowired
	CourseAttendanceRepo courseAttendanceRepo;
	
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
	
	public CourseAttendance getCourseAttendance(Student student, Course course){
		return courseAttendanceRepo.findByCourseAndStudent(course, student);
	}

	public boolean attend(Student student, long courseId) {
		Course course = getCourse(courseId);
		CourseAttendance courseAttendance = getCourseAttendance(student, course);
		if(courseAttendance != null)
			return false;
		courseAttendance = new CourseAttendance();
		course.addAttendance(courseAttendance);
		student.addAttendance(courseAttendance);
		
		if(courseAttendanceRepo.save(courseAttendance)!=null)
			return true;
		return false;
	}
	public List<CourseAttendance> getAttendedCourses(Student student) {
		return courseAttendanceRepo.findByStudent(student);
	}

	public List<CourseAttendance> getStudentsInCourse(Course course) {
		return courseAttendanceRepo.findByCourse(course);
	}

	public boolean updateAchievement(Course course, Student student, long ach) {
		CourseAttendanceID pk = new CourseAttendanceID(student, course);  
		CourseAttendance courseAttendance = courseAttendanceRepo.findOne(pk);
		courseAttendance.setAchievement(ach);
		
		if(courseAttendanceRepo.save(courseAttendance)!=null)
			return true;
		return false;
	}
}
