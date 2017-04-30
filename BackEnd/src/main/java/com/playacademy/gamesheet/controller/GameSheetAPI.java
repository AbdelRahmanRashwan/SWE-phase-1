package com.playacademy.gamesheet.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.playacademy.game.controller.IGameController;
import com.playacademy.game.model.Game;
import com.playacademy.gamesheet.model.GameSheet;
import com.playacademy.user.controller.UserServicesController;
import com.playacademy.user.model.Student;
@RestController
public class GameSheetAPI {

	
	@Autowired
	private IGameController gameServices;
	
	@Autowired
	private GameSheetController gameSheetController;
	
	@Autowired
	@Qualifier(value = "UBean")
	UserServicesController userServices;
	
	@RequestMapping(value="/game/score/update", method = RequestMethod.POST)
	public Map<String,Boolean> updateScore(@RequestBody ParseJsonToGameSheet parseJsonToGameSheet){
		Game game = gameServices.getGameByID(parseJsonToGameSheet.gameId);
		Student student=(Student) userServices.getUserByID(parseJsonToGameSheet.studentId);

		Map <String,Boolean> map=new HashMap<>();
		Boolean ack = gameSheetController.saveScore(game, student, parseJsonToGameSheet.score, parseJsonToGameSheet.rate);
		map.put("updated",ack);
		return map;

	}
	@RequestMapping(value="/student/scoreBoard", method = RequestMethod.GET)
	public ArrayList<GameSheet> scoreBoard(@RequestParam("studentId")long studentId){
		Student student =(Student) userServices.getUserByID(studentId);
		if(student == null){
			return null;	
		}
		return gameSheetController.scoreBoard(student);
	}
	
	static class ParseJsonToGameSheet {
		public long gameId;
		public long studentId;
		public int score;
		public int rate;

		public ParseJsonToGameSheet(){
		}
	}
}
