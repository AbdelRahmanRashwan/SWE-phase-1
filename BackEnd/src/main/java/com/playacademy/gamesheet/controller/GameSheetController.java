package com.playacademy.gamesheet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playacademy.game.model.Game;
import com.playacademy.gamesheet.model.GameSheet;
import com.playacademy.gamesheet.model.GameSheetRepo;
import com.playacademy.user.model.Student;

@Service
public class GameSheetController {

	@Autowired
	GameSheetRepo gameSheetRepo;
	
	public boolean updateSheet(Game game , Student student,int score , int rate){
		GameSheet gameSheet = gameSheetRepo.findByStudentAndGame(student, game);
		if(gameSheet!=null){
			gameSheet.setScore(Math.max(gameSheet.getScore(), score));
			game.setRate((game.getRate()+rate-gameSheet.getRate())/game.getNumOfRates());
		}else{
			gameSheet = new GameSheet();
			game.addScore(gameSheet);
			student.addScore(gameSheet);
			gameSheet.setScore(score);
			game.setNumOfRates(game.getNumOfRates()+1);
			game.setRate((game.getRate()+rate)/game.getNumOfRates());
		}
		gameSheet.setRate(rate);
		if(gameSheetRepo.save(gameSheet) != null){
			return true;
		}
		else
			return false;
	}
	
	public List<GameSheet> scoreBoard(Student student){
		return gameSheetRepo.findByStudent(student);
	}
	
}
