package com.playacademy.Game.controller;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playacademy.course.controller.CourseManagerAPI;
import com.playacademy.course.model.Course;
import com.playacademy.Game.helper.ObjectMapperConfiguration;
import com.playacademy.Game.Model.*;

@RestController
public class GameManger {

	@Autowired
	private IGameServices services;

	@Autowired
	private CourseManagerAPI courseAPI;

	@RequestMapping(value = "/game/mcq/create", method = RequestMethod.POST)
	public String createMCQGame(@RequestBody ItemCollector<MCQ> items) {
		Game game = new Game();
		game.setName(items.gameName);
		for (int i = 0; i < items.questions.size(); i++) {
			game.addQuestion(items.questions.get(i));
			Iterator<Choice> it = items.questions.get(i).getChoices().iterator();
			while (it.hasNext()) {
				it.next().setQuestion(items.questions.get(i));
			}
		}
		return addGame(game, items.courseName);
	}

	@RequestMapping(value = "/game/trueandfalse/create", method = RequestMethod.POST)
	public String createTrueAndFalseGame(@RequestBody ItemCollector<TrueAndFalse> items) {
		Game game = new Game();
		game.setName(items.gameName);
		for (int i = 0; i < items.questions.size(); i++) {
			game.addQuestion(items.questions.get(i));
		}
		return addGame(game, items.courseName);

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

		return services.addEditedGame(game);
	}

	@RequestMapping(value = "/game/get", method = RequestMethod.GET)
	public Game getGame(@RequestParam("id") long id) {

		Game game = services.getGameByID(id);
		return game;
	}

	@RequestMapping(value = "/gamescourse/get", method = RequestMethod.GET)
	public List<Game> getAllGamesInCourse(@RequestParam("courseName") String courseName) {
		long courseId = courseAPI.getCourseId(courseName);
		Course course = courseAPI.getCourse(courseId);
		return services.getAllGamesInCourse(course);
	}

	private String addGame(Game game, String courseName) {
		long courseId = courseAPI.getCourseId(courseName);
		String ack = "";
		if (courseId == -1) {
			ack = "No course with that name.";
		} else {
			Course course = courseAPI.getCourse(courseId);

			if (services.addGame(game, course) == true)
				ack = "Game created succssessfully";
			else
				ack = "something wrong happened";
		}
		return ack;
	}

	static class ItemCollector<T> {

		public ItemCollector() {

		}
		public String gameName;
		public String courseName;
		public List<T> questions;

	}
}
