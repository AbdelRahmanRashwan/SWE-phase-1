package com.playacademy.game.model;

import javax.transaction.Transactional;

@Transactional
public interface QuestionRepository extends QuestionBaseRepository<Question> { }

