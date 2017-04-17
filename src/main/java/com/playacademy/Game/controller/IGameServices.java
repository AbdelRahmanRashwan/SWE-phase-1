package com.playacademy.Game.controller;

import java.util.List;

import com.playacademy.course.model.Course;
import com.playacademy.Game.Model.Game;

public interface IGameServices {
	
	public boolean addGame(Game game, Course course);
	public Game getGameByID(long id);
	public List<Game> getAllGamesInCourse(Course course);
	public boolean addEditedGame(Game game);
}
