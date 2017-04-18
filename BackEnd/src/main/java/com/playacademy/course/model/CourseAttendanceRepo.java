package com.playacademy.course.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.playacademy.user.model.Student;

public interface CourseAttendanceRepo extends CrudRepository<CourseAttendance, Long>{
	public List<CourseAttendance> findByStudent(Student student);

	public List<CourseAttendance> findByCourse(Course course);
	public CourseAttendance findByCourseAndStudent(Course course, Student student);
}
