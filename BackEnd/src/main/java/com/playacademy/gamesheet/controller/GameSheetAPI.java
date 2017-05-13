package com.playacademy.gamesheet.controller;
//
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
		Boolean ack = gameSheetController.updateSheet(game, student, parseJsonToGameSheet.score, parseJsonToGameSheet.rate);
		map.put("updated",ack);
		return map;

	}
	@RequestMapping(value="/game/rate", method = RequestMethod.POST)
	public Map<String,Boolean> rate(@RequestBody ParseJsonToGameSheet parseJsonToGameSheet){
		Game game = gameServices.getGameByID(parseJsonToGameSheet.gameId);
		Student student=(Student) userServices.getUserByID(parseJsonToGameSheet.studentId);
		
		Map <String,Boolean> map=new HashMap<>();
		Boolean ack = gameSheetController.updateSheet(game, student, parseJsonToGameSheet.score, parseJsonToGameSheet.rate);
		if(ack){
			gameServices.addEditedGame(game);
		}
		map.put("updated",ack);
		return map;

	}
	@RequestMapping(value="/student/scoreBoard", method = RequestMethod.GET)
	public Map<String,List<GameSheet>> scoreBoard(@RequestParam("studentId")long studentId){
		Map<String,List<GameSheet>> data=new HashMap<>();
		System.out.println(studentId);
		Student student =(Student) userServices.getUserByID(studentId);
		if(student == null){
			return null;	
		}
		data.put("score_board", gameSheetController.scoreBoard(student));
		return data;
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
