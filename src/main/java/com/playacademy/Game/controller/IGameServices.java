package com.playacademy.game.controller;

import java.util.List;

import com.playacademy.game.model.Game;

public interface IGameServices {
	
	public boolean addGame(Game game);
	public Game getGameByID(long id);
	public List<Game> getAllGamesInCourse(long courseId);
}
