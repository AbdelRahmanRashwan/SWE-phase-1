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
import com.playacademy.user.model.Student;

@RestController
public class GameManger {

	@Autowired
	private IGameServices gameServices;

	@Autowired
	private CourseManagerAPI courseAPI;

	@Autowired
	@Qualifier(value = "UBean")
	UserServicesAPI userServices;
	
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

		Game game = gameServices.getGameByID(id);
		game.addQuestion(question);

		return gameServices.addEditedGame(game);
	}

	@RequestMapping(value = "/game/get", method = RequestMethod.GET)
	public Game getGame(@RequestParam("id") long id) {

		Game game = gameServices.getGameByID(id);
		return game;
	}

	@RequestMapping(value="/game/score/update", method = RequestMethod.POST)
	public Map<String,Boolean> updateScore(@RequestBody ParseJsonToGameSheet parseJsonToGameSheet){
		Game game = gameServices.getGameByID(parseJsonToGameSheet.gameId);
		Student student=(Student) userServices.getUserByID(parseJsonToGameSheet.studentId);

		Map <String,Boolean> map=new HashMap<>();
		map.put("updated",gameServices.saveScore(game, student, parseJsonToGameSheet.score, parseJsonToGameSheet.rate));
		return map;

	}
	
	@RequestMapping(value = "/gamescourse/get", method = RequestMethod.GET)
	public Map<String,List<Game>> getAllGamesInCourse(@RequestParam("courseName") String courseName) {
		long courseId = courseAPI.getCourseId(courseName);
		Course course = courseAPI.getCourse(courseId);
		Map<String,List<Game>> data=new HashMap<>();
		data.put("games", gameServices.getAllGamesInCourse(course));
		return data;
	}
	
	@RequestMapping(value = "/judgeGame",method = RequestMethod.GET)
	public Map<String,Boolean> judge(@RequestParam("gameId")long gameId,@RequestParam("questionId")long questionId,@RequestParam("answer")String answer){
		Game g =gameServices.getGameByID(gameId);
		Set<Question> gameQuestions =g.getQuestions();
		Iterator<Question> i = gameQuestions.iterator();
		Question question =null;
		while(i.hasNext()){
			question =(Question) i.next();
			if(question.getQuestionId()==questionId)
				break;
		}

		Map <String,Boolean> map=new HashMap<>();
		map.put("judge",gameServices.judge(question, answer));
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
	
	public ArrayList<GameSheet> scoreBoard(long studentId){
		Student student =(Student) userServices.getUserByID(studentId);
		if(student == null){
			return null;	
		}
		return gameServices.scoreBoard(student);
	}

	static class ItemCollector<T> {

		public ItemCollector() {

		}

		public String gameName;
		public String courseName;
		public List<T> questions;

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
