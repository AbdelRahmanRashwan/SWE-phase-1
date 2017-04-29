package com.playacademy.game.controller;

import java.util.List;

import com.playacademy.course.model.Course;
import com.playacademy.game.model.Game;
import com.playacademy.game.model.Question;

public interface IGameController {
	
	public boolean addGame(Game game, Course course);
	public Game getGameByID(long id);
	public List<Game> getAllGamesInCourse(Course course);
	public boolean addEditedGame(Game game);
	public boolean judge(Question question,String answer);
}
