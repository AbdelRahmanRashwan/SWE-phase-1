package com.playacademy.courseattendance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playacademy.course.model.Course;
import com.playacademy.course.model.CourseRepo;
import com.playacademy.courseattendance.model.CourseAttendance;
import com.playacademy.courseattendance.model.CourseAttendanceRepo;
import com.playacademy.user.model.Student;

@Service
public class CourseAttendanceController {

	@Autowired
	CourseAttendanceRepo courseAttendanceRepo;
	
	@Autowired
	CourseRepo courseRepo;
	
	public boolean attend(Student student, Course course) {
		CourseAttendance courseAttendance = courseAttendanceRepo.findByCourseAndStudent(course, student);
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
		CourseAttendance courseAttendance = courseAttendanceRepo.findByCourseAndStudent(course, student);
		courseAttendance.setAchievement(ach);
		
		if(courseAttendanceRepo.save(courseAttendance)!=null)
			return true;
		return false;
	}

	public boolean isRegistered(Student student, Course course) {
		CourseAttendance courseAttendance = courseAttendanceRepo.findByCourseAndStudent(course, student);
		if(courseAttendance == null)
			return false;
		return true;
	}
}
