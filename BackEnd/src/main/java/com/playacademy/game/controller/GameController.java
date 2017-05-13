package com.playacademy.game.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.playacademy.course.model.Course;
import com.playacademy.game.model.*;

@Service
@Component(value = "GBean")
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
	public Game getExistGameByID(long id) {
		System.out.println(id);
		return repository.findByGameIdAndCanceled(id,false);
	}

	@Override
	public List<Game> getAllExistGamesInCourse(Course course) {
		return repository.findByCourseAndCanceled(course,false);
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

	@Override
	public Game getDeletedGameByID(long id) {
		return repository.findByGameIdAndCanceled(id,true);
	}
	
	@Override
	public List<Game> getAllDeletedGamesInCourse(Course course) {
		return repository.findByCourseAndCanceled(course,true);
	}
	
	

	
	
}
