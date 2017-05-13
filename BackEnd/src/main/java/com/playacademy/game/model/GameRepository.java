package com.playacademy.game.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.playacademy.course.model.Course;



public interface GameRepository
extends CrudRepository<Game, Long>{

	Game findByGameIdAndCanceled(long id,Boolean canceled);
	List<Game> findByCourseAndCanceled(Course course,Boolean canceled);

}
