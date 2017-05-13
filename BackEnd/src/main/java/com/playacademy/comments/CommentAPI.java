package com.playacademy.comments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playacademy.game.controller.GameController;
import com.playacademy.game.model.Game;
import com.playacademy.user.controller.UserServicesController;
import com.playacademy.user.model.User;

@RestController
public class CommentAPI {

	@Autowired
	CommentService commentService;
	
	@Autowired
	@Qualifier(value = "UBean")
	UserServicesController userServicesController;
	
	@Autowired
	@Qualifier(value = "GBean")
	GameController gameController;
	
	@RequestMapping (value="/comment/add", method = RequestMethod.GET)
	public Map<String,String> addComment(@RequestParam("commentorID") int commentorID,
			@RequestParam("description") String description,
			@RequestParam("gameID") int gameID)
	{
		Map <String,String> map=new HashMap<>();
		User user=userServicesController.getUserByID(commentorID);
		Game game = gameController.getExistGameByID(gameID);
		Comment comment=new Comment(user,game,description);
		boolean b=commentService.addComment(comment);
		if (b){
			map.put("confirmation","Comment Added Successfully");
		}
		else{
			map.put("confirmation","Comment Wasn't Added Successfully");
		}
		return map;
	}
	
	@RequestMapping (value="/comments/get", method = RequestMethod.GET)
	public Map<String,ArrayList<Comment>> getComments(@RequestParam("gameID") int gameID){
		Game game=gameController.getExistGameByID(gameID);
		ArrayList<Comment> comments=commentService.getComments(game);
		Map<String,ArrayList<Comment>> map=new HashMap<>();
		map.put("comments", comments);
		return map;
	}
}
