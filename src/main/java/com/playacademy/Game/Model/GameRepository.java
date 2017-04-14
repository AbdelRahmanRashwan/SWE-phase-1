package com.playacademy.game.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;



public interface GameRepository
extends CrudRepository<Game, Long>{

	Game findByGameId(long id);
	List<Game> findByCourseId(long courseId);

}
