package com.playacademy.game.model;

import org.springframework.data.repository.CrudRepository;

import com.playacademy.user.model.Student;

public interface ScoreSheetRepo extends CrudRepository<Student, Long>{

}
