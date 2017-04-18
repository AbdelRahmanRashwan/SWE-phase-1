package com.playacademy.game.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface QuestionBaseRepository<T extends Question> 
extends CrudRepository<T, Long> {
  
}

