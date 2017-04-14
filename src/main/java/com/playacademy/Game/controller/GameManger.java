package com.playacademy.game.controller;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playacademy.game.model.*;
import com.playacademy.helper.ObjectMapperConfiguration;



@RestController
public class GameManger {

	 @Autowired
	 private IGameServices services;

	@RequestMapping(value = "/game/mcq/create", method = RequestMethod.POST)
	public boolean createMCQGame(@RequestBody ItemCollector<MCQ> items) {
		Game game = new Game();
		game.setName(items.gameName);
		game.setCourseId(items.courseId);
		for(int i =0;i<items.questions.size();i++){
			game.addQuestion(items.questions.get(i));
			Iterator<Choice> it = items.questions.get(i).getChoices().iterator();
			while(it.hasNext()){
				it.next().setQuestion(items.questions.get(i));;
			}
		}
		
		return services.addGame(game);
	}
	
	@RequestMapping(value = "/game/trueandfalse/create", method = RequestMethod.POST)
	public boolean createTrueAndFalseGame(@RequestBody ItemCollector<TrueAndFalse> items) {
		Game game = new Game();
		game.setName(items.gameName);
		game.setCourseId(items.courseId);
		for(int i =0;i<items.questions.size();i++){
			game.addQuestion(items.questions.get(i));
		}
		
		return services.addGame(game);
	}
	
	@RequestMapping(value = "/game/question/add", method = RequestMethod.POST)
	public Question addQuuestion(@RequestParam("gameId") long id, @RequestBody String quest) {
		
		ObjectMapper objectMapper = new ObjectMapperConfiguration().objectMapper();
		Question question = null;
		try {
			question = objectMapper.readValue(quest, Question.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(question instanceof MCQ){
			Iterator<Choice> it = ((MCQ)question).getChoices().iterator();
			while(it.hasNext()){
				it.next().setQuestion((MCQ)question);
			}
		}
		
		Game g = services.getGameByID(id);
		g.addQuestion(question);
		
		services.addGame(g);
		return question;
	}
	
	@RequestMapping(value = "/game/get", method = RequestMethod.GET)
	public Game getGame(@RequestParam("id") long id) {
		
		Game game = services.getGameByID(id);
		return game;
	}
	
	

	@RequestMapping(value = "/gamescourse/get", method = RequestMethod.GET)
	public List<Game> getAllGamesInCourse(@RequestParam("courseId") long courseId){
		return services.getAllGamesInCourse(courseId);
	}
	
	static class ItemCollector<T>{
		
		public ItemCollector(){
			
		}
		public String gameName;
		public long courseId;
		public List<T> questions;
		
	}
}
