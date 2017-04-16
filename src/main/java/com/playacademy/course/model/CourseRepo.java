package com.playacademy.course.model;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepo extends CrudRepository<Course, Long> {
	
	public Course findByCourseName(String name);
	
}
