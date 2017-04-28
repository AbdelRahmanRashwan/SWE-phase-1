package com.playacademy.game.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playacademy.course.model.Course;
import com.playacademy.game.model.*;

@Service
public class GameController implements IGameController{
	
	@Autowired
	GameRepository repository;
	

	@Override
	public boolean addGame(Game game, Course course) {
		course.addGame(game);
		if(repository.save(game) != null)
			return true;
		else return false;
		
	}

	@Override
	public Game getGameByID(long id) {
		return repository.findByGameId(id);
	}

	@Override
	public List<Game> getAllGamesInCourse(Course course) {
		return repository.findByCourse(course);
	}

	@Override
	public boolean addEditedGame(Game game) {
		if(repository.save(game) != null)
			return true;
		else return false;
	}

	@Override
	public boolean judge(Question question, String answer) {
		if(question!=null&&answer.equals(question.getAnswer())){
			return true;
		}else {
			return false;
		}
	}
	
	

	
	
}
