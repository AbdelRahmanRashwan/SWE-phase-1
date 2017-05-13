package com.playacademy.comments;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.playacademy.game.model.Game;

public interface CommentsRepo extends CrudRepository<Comment, Long>{
	public ArrayList<Comment> findByGame(Game game);
}
