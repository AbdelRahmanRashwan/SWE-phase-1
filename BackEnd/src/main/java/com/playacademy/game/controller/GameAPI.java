package com.playacademy.game.controller;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.playacademy.course.controller.CourseController;
import com.playacademy.course.model.Course;
import com.playacademy.game.helper.ObjectMapperConfiguration;
import com.playacademy.game.model.Choice;
import com.playacademy.game.model.Game;
import com.playacademy.game.model.MCQ;
import com.playacademy.game.model.Question;
import com.playacademy.game.model.TrueAndFalse;
import com.playacademy.user.controller.UserServicesController;
import com.playacademy.user.model.Teacher;

//
@RestController
public class GameAPI {

	@Autowired
	private IGameController gameServices;

	@Autowired
	private CourseController courseAPI;

	@Autowired
	@Qualifier(value = "UBean")
	UserServicesController userServices;

	@RequestMapping(value = "/game/create", method = RequestMethod.POST)
	public Map<String, String> createGame(@RequestBody ItemCollector<String> item) {
		Map<String, String> confirmation = new HashMap<String, String>();
		try {
			Game game = new Game();
			confirmation.put("confirmation", addGame(makeGame(item, game), item.courseName));
		} catch (IOException e) {
			confirmation.put("confirmation", "Something went wrong");
		}
		return confirmation;

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

		Game game = gameServices.getExistGameByID(id);
		game.addQuestion(question);

		return gameServices.addEditedGame(game);
	}

	@RequestMapping(value = "/game/collaborator/add", method = RequestMethod.POST)
	public Map<String, Boolean> addCollaborator(@RequestParam("teacherId") long collaboratorId,
			@RequestParam("gameId") long gameId) {
		Map<String, Boolean> confirmation = new HashMap<String, Boolean>();
		Game game = gameServices.getExistGameByID(gameId);
		Teacher collaborator = (Teacher) userServices.getUserByID(collaboratorId);
		game.addCollaborator(collaborator);
		confirmation.put("confirmation", gameServices.addEditedGame(game));
		return confirmation;
	}

	@RequestMapping(value = "/game/collaborators/get", method = RequestMethod.POST)
	public Map<String, Set<Teacher>> getAllCollaborators(@RequestParam("gameId") long gameId) {
		Map<String, Set<Teacher>> data = new HashMap<>();
		Game game = gameServices.getExistGameByID(gameId);
		if (game == null) {
			data.put("Error", null);
		} else {
			data.put("collaborators", game.getCollaborators());
		}
		return data;

	}

	@RequestMapping(value = "/game/get/deleted", method = RequestMethod.GET)
	public Game getDeletedGame(@RequestParam("id") long id) {

		Game game = gameServices.getDeletedGameByID(id);

		return game;
	}

	@RequestMapping(value = "/game/get", method = RequestMethod.GET)
	public Game getGame(@RequestParam("id") long id) {

		Game game = gameServices.getExistGameByID(id);

		return game;
	}

	@RequestMapping(value = "/game/copy", method = RequestMethod.GET)
	public Map<String, String> copyGame(@RequestParam("gameId") int gameId,
										@RequestParam("courseName") String  courseName){
			
		Game game=gameServices.getExistGameByID(gameId);
		Game game2=copyGameInfo(game);
		Map<String, String> confirmation = new HashMap<>();
		confirmation.put("confirmation",addGame(game2, courseName));
		return confirmation;
	}
	
	private Game copyGameInfo(Game data){
		Game game= new Game();
		game.setName(data.getName());
		for(Question question : data.getQuestions()){
			Question ques;
			if (question instanceof MCQ){
				ques=new MCQ();
				Set<Choice> choices=new HashSet<>();
				for (Choice choice:((MCQ)ques).getChoices()){
					Choice temp=new Choice();
					temp.setChoice(choice.getChoice());
					choices.add(temp);
				}
				((MCQ)ques).setChoices(choices);
			}
			else {
				ques=new TrueAndFalse();
			}
			
			ques.setQuestion(question.getQuestion());
			ques.setAnswer(question.getAnswer());
			game.addQuestion(ques);
		}
		return game;
	}


	@RequestMapping(value = "/game/edit", method = RequestMethod.POST)
	public Map<String, Boolean> editGame(@RequestParam("gameId") long gameId, @RequestBody ItemCollector<String> item) {

		Map<String, Boolean> confirmation = new HashMap<String, Boolean>();
		try {
			Game oldGame = gameServices.getExistGameByID(gameId);
			oldGame.getQuestions().clear();
			confirmation.put("confirmation", gameServices.addEditedGame(makeGame(item, oldGame)));
		} catch (IOException e) {
			confirmation.put("confirmation", false);
		}

		return confirmation;
	}

	@RequestMapping(value = "/game/cancel", method = RequestMethod.GET)
	public Map<String, Boolean> cancelGame(@RequestParam("gameId") long gameId) {
		System.out.println("in");
		Map<String, Boolean> confirmation = new HashMap<String, Boolean>();
		Game game = gameServices.getExistGameByID(gameId);
		System.out.println("game" + game);
		if (game == null) {
			confirmation.put("confirmation", false);
			return confirmation;
		}
		game.setCanceled(true);
		confirmation.put("confirmation", gameServices.addEditedGame(game));
		System.out.println(confirmation.get("confirmation"));
		return confirmation;
	}

	@RequestMapping(value = "/game/un-cancel", method = RequestMethod.GET)
	public Map<String, Boolean> uncancelGame(@RequestParam("gameId") int gameId) {

		Map<String, Boolean> confirmation = new HashMap<String, Boolean>();
		Game game = gameServices.getDeletedGameByID(gameId);

		game.setCanceled(false);
		confirmation.put("confirmation", gameServices.addEditedGame(game));

		return confirmation;
	}

	private Game makeGame(ItemCollector<String> item, Game game)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapperConfiguration().objectMapper();
		Gson gson = new Gson();
		game.setName(item.gameName);

		for (int i = 0; i < item.questions.size(); i++) {
			Question question = objectMapper.readValue(gson.toJson(item.questions.get(i)), Question.class);
			game.addQuestion(question);
		}
		return game;
	}

	@RequestMapping(value = "/gamescourse/get/deleted", method = RequestMethod.GET)
	public Map<String, List<Game>> getAllDeletedGamesInCourse(@RequestParam("courseName") String courseName) {
		long courseId = courseAPI.getCourseId(courseName);
		Course course = courseAPI.getCourse(courseId);
		Map<String, List<Game>> data = new HashMap<>();
		data.put("games", gameServices.getAllDeletedGamesInCourse(course));
		return data;
	}

	@RequestMapping(value = "/gamescourse/get", method = RequestMethod.GET)
	public Map<String, List<Game>> getAllGamesInCourse(@RequestParam("courseName") String courseName) {
		long courseId = courseAPI.getCourseId(courseName);
		Course course = courseAPI.getCourse(courseId);
		Map<String, List<Game>> data = new HashMap<>();
		data.put("games", gameServices.getAllExistGamesInCourse(course));
		return data;
	}

	@RequestMapping(value = "/judgeGame", method = RequestMethod.GET)
	public Map<String, Boolean> judge(@RequestParam("gameId") long gameId, @RequestParam("questionId") long questionId,
			@RequestParam("answer") String answer) {
		Game g = gameServices.getExistGameByID(gameId);
		Set<Question> gameQuestions = g.getQuestions();
		Iterator<Question> i = gameQuestions.iterator();
		Question question = null;
		while (i.hasNext()) {
			question = (Question) i.next();
			if (question.getQuestionId() == questionId)
				break;
		}

		Map<String, Boolean> map = new HashMap<>();
		map.put("judge", gameServices.judge(question, answer));
		return map;

	}

	private String addGame(Game game, String courseName) {
		long courseId = courseAPI.getCourseId(courseName);
		String ack = "";
		if (courseId == -1) {
			ack = "No course with that name.";
		} else {
			Course course = courseAPI.getCourse(courseId);
			
			if (gameServices.addGame(game, course) == true)
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
		public List<Object> questions;

	}

}
