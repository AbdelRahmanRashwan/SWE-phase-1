package com.playacademy.game.controller;

import java.util.List;

import com.playacademy.course.model.Course;
import com.playacademy.game.model.Game;

public interface IGameServices {
	
	public boolean addGame(Game game);
	public Game getGameByID(long id);
	public List<Game> getAllGamesInCourse(Course course);
}
