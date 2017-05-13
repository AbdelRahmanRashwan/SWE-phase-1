package com.playacademy.game.controller;

import java.util.List;

import com.playacademy.course.model.Course;
import com.playacademy.game.model.Game;
import com.playacademy.game.model.Question;

public interface IGameController {
	
	public boolean addGame(Game game, Course course);
	public boolean addEditedGame(Game game);
	public boolean judge(Question question,String answer);
	
	public Game getExistGameByID(long id);
	public Game getDeletedGameByID(long id);
	
	public List<Game> getAllDeletedGamesInCourse(Course course);
	public List<Game> getAllExistGamesInCourse(Course course);
}
