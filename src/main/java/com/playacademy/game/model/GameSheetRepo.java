package com.playacademy.game.model;

import org.springframework.data.repository.CrudRepository;

import com.playacademy.user.model.Student;

public interface GameSheetRepo extends CrudRepository<GameSheet, Long>{

}
