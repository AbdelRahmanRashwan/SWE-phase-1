package com.playacademy.game.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playacademy.course.model.Course;
import com.playacademy.game.model.*;
import com.playacademy.user.model.Student;

@Service
public class GameServices implements IGameServices{
	
	@Autowired
	GameRepository repository;
	@Autowired
	GameSheetRepo gameSheetRepo;

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
	public int judge(Question question, String answer) {
		if(question!=null&&answer.equals(question.getAnswer())){
			return 10;
		}else if (question==null){
			return -1;
		}else {
			return 0;
		}
	}
	
	@Override
	public boolean saveScore(Game game , Student student,int score , int rate){
		GameSheet gameSheet = new GameSheet();
		gameSheet.setGame(game);
		gameSheet.setStudent(student);
		gameSheet.setScore(score);
		gameSheet.setScore(rate);
		if(gameSheetRepo.save(gameSheet) != null)
			return true;
		else 
			return false;
	}

	@Override
	public ArrayList<GameSheet> scoreBoard(Student student){
		return gameSheetRepo.findByStudent(student);
	}
	
}
