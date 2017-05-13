package com.playacademy.game.helper;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.playacademy.game.model.*;
public class QuestionDeserializer extends JsonDeserializer<Question> {

    @Override
    public Question deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        ObjectCodec codec = p.getCodec();
        JsonNode tree = codec.readTree(p);

        if (tree.has("choices")) {
        	MCQ question = codec.treeToValue(tree, MCQ.class);
        	Iterator<Choice> it = ((MCQ) question).getChoices().iterator();
			while (it.hasNext()) {
				it.next().setQuestion((MCQ) question);
			}
            return question;
        }else {
        	return codec.treeToValue(tree, TrueAndFalse.class);
        }

        // Other types...

    }
}