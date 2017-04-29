package com.playacademy.gamesheet.model;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.playacademy.game.model.Game;
import com.playacademy.user.model.Student;

public interface GameSheetRepo extends CrudRepository<GameSheet, Long>{
	ArrayList<GameSheet> findByStudent(Student student);
	GameSheet findByStudentAndGame(Student student, Game game);
}
