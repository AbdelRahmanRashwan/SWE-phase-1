package com.playacademy.game.controller;

import java.util.List;

import com.playacademy.course.model.Course;
import com.playacademy.game.model.Game;
import com.playacademy.game.model.Question;
import com.playacademy.user.model.Student;

public interface IGameServices {
	
	public boolean addGame(Game game, Course course);
	public Game getGameByID(long id);
	public List<Game> getAllGamesInCourse(Course course);
	public boolean addEditedGame(Game game);
	public int judge(Question question,String answer);
	boolean saveScore(Game game, Student student, int score, int rate);
}
