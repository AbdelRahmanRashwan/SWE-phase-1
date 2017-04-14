package com.playacademy.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.playacademy.Game.Model.Question;

public class ObjectMapperConfiguration{
	public ObjectMapper objectMapper(){
		SimpleModule module = new SimpleModule();
        module.addDeserializer(Question.class, new QuestionDeserializer());
        return new ObjectMapper().registerModules(module);
	}
}
