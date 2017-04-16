package com.playacademy.game.controller;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playacademy.course.controller.CourseManagerAPI;
import com.playacademy.course.model.Course;
import com.playacademy.game.helper.ObjectMapperConfiguration;
import com.playacademy.game.model.*;
import com.playacademy.user.controller.UserServicesAPI;
import com.playacademy.user.model.Teacher;

@RestController
public class GameManger {

	@Autowired
	private IGameServices services;

	@Autowired
	@Qualifier(value = "TBean")
	private UserServicesAPI userServices;
	
	@Autowired
	private CourseManagerAPI courseAPI;

	@RequestMapping(value = "/game/mcq/create", method = RequestMethod.POST)
	public boolean createMCQGame(@RequestBody ItemCollector<MCQ> items) {
		Game game = new Game();
		game.setName(items.gameName);
		for (int i = 0; i < items.questions.size(); i++) {
			game.addQuestion(items.questions.get(i));
			Iterator<Choice> it = items.questions.get(i).getChoices().iterator();
			while (it.hasNext()) {
				it.next().setQuestion(items.questions.get(i));
			}
		}
		return addGame(game, items.courseId, items.creatorId);
		
	}

	@RequestMapping(value = "/game/trueandfalse/create", method = RequestMethod.POST)
	public boolean createTrueAndFalseGame(@RequestBody ItemCollector<TrueAndFalse> items) {
		Game game = new Game();
		game.setName(items.gameName);
		for (int i = 0; i < items.questions.size(); i++) {
			game.addQuestion(items.questions.get(i));
		}
		return addGame(game, items.courseId, items.creatorId);
	}

	@RequestMapping(value = "/game/question/add", method = RequestMethod.POST)
	public boolean addQuuestion(@RequestParam("gameId") long id, @RequestBody String quest) {

		ObjectMapper objectMapper = new ObjectMapperConfiguration().objectMapper();
		Question question = null;
		try {
			question = objectMapper.readValue(quest, Question.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (question instanceof MCQ) {
			Iterator<Choice> it = ((MCQ) question).getChoices().iterator();
			while (it.hasNext()) {
				it.next().setQuestion((MCQ) question);
			}
		}

		Game game = services.getGameByID(id);
		game.addQuestion(question);

		return services.addGame(game);
	}

	@RequestMapping(value = "/game/get", method = RequestMethod.GET)
	public Game getGame(@RequestParam("id") long id) {

		Game game = services.getGameByID(id);
		return game;
	}

	@RequestMapping(value = "/gamescourse/get", method = RequestMethod.GET)
	public List<Game> getAllGamesInCourse(@RequestParam("courseId") long courseId) {
		Course course = courseAPI.getCourse(courseId);
		return services.getAllGamesInCourse(course);
	}

	private boolean addGame(Game game, long courseId, long creatorId){
		Course course = courseAPI.getCourse(courseId);
		course.addGame(game);
		
		Teacher creator = (Teacher) userServices.getUserByID(creatorId);
		game.setCreator(creator);

		return services.addGame(game);
	}
	
	static class ItemCollector<T> {

		public ItemCollector() {

		}

		public String gameName;
		public long courseId;
		public long creatorId;
		public List<T> questions;

	}
}
