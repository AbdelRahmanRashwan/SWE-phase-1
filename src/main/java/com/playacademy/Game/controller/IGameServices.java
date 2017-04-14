package com.playacademy.Game.controller;

import java.util.List;

import com.playacademy.Game.Model.Game;

public interface IGameServices {
	
	public boolean addGame(Game game);
	public Game getGameByID(long id);
	public List<Game> getAllGamesInCourse(long courseId);
}
