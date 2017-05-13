package com.playacademy.comments;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playacademy.game.model.Game;

@Service
public class CommentService {

	@Autowired
	CommentsRepo commentRepo;
	
	public boolean addComment(Comment comment) {
		// TODO Auto-generated method stub
		return (commentRepo.save(comment)==null)? false:true;
	}

	public ArrayList<Comment> getComments(Game game) {
		ArrayList<Comment> comments=commentRepo.findByGame(game);	
		return comments;
	}

}
