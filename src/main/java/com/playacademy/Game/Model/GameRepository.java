package com.playacademy.Game.Model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.playacademy.course.model.Course;



public interface GameRepository
extends CrudRepository<Game, Long>{

	Game findByGameId(long id);
	List<Game> findByCourse(Course course);

}
