package com.playacademy.game.model;

import javax.transaction.Transactional;

@Transactional
public interface MCQRepository extends QuestionBaseRepository<MCQ> { }
