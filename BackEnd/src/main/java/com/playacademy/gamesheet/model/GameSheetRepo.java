package com.playacademy.gamesheet.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.playacademy.game.model.Game;
import com.playacademy.user.model.Student;

public interface GameSheetRepo extends CrudRepository<GameSheet, Long>{
	List<GameSheet> findByStudent(Student student);
	GameSheet findByStudentAndGame(Student student, Game game);
}
