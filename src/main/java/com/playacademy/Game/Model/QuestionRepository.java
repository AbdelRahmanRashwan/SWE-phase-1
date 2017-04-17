package com.playacademy.Game.Model;

import javax.transaction.Transactional;

@Transactional
public interface QuestionRepository extends QuestionBaseRepository<Question> { }

