package com.example.rashwan.playacademy;

import android.util.Log;
import android.widget.Toast;

import com.example.rashwan.playacademy.Models.Choice;
import com.example.rashwan.playacademy.Models.Course;
import com.example.rashwan.playacademy.Models.Game;
import com.example.rashwan.playacademy.Models.MCQ;
import com.example.rashwan.playacademy.Models.Question;
import com.example.rashwan.playacademy.Models.Student;
import com.example.rashwan.playacademy.Models.Teacher;
import com.example.rashwan.playacademy.Models.TrueAndFalse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rashwan on 4/21/2017.
 */

public class Util {
    public static Teacher parseTeacher(JSONObject teacherData){
        Teacher teacher=new Teacher();
        try {
            teacher.setFirstName(teacherData.getString("firstName"));
            teacher.setFirstName(teacherData.getString("firstName"));
            teacher.setLastName(teacherData.getString("lastName"));
            teacher.setEmail(teacherData.getString("email"));
            teacher.setAge(teacherData.getInt("age"));
            teacher.setEducationalMail(teacherData.getString("educationalMail"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return teacher;
    }

    public static Student parseStudent(JSONObject studentData){
        Student student=new Student();
        try {
            student.setUserId(studentData.getInt("userId"));
            student.setFirstName(studentData.getString("firstName"));
            student.setLastName(studentData.getString("lastName"));
            student.setEmail(studentData.getString("email"));
            student.setAge(studentData.getInt("age"));
        }catch (JSONException e){
            e.printStackTrace();
        }
        return student;
    }

    public static ArrayList<Course> parseCourses(JSONObject response){
        JSONArray courses= null;
        ArrayList<Course>data=new ArrayList<>();
        try {
            courses = response.getJSONArray("courses");
            for (int i=0;i<courses.length();i++){
                Course course = null;
                course = parseCourse(courses.getJSONObject(i).getJSONObject("course"));
                data.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Course parseCourse(JSONObject courseData){
        Course course=new Course();
        try {
            course.setCourseId(courseData.getInt("courseId"));
            course.setCourseName(courseData.getString("courseName"));
            course.setCourseDescription(courseData.getString("courseDescription"));
            Teacher teacher=parseTeacher(courseData.getJSONObject("creator"));
            course.setCreator(teacher);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return course;
    }

    public static Game parseGame(JSONObject gameData){
        Game game=new Game();
        try {
            game.setName(gameData.getString("name"));
            game.setGameId(gameData.getInt("gameId"));
            Log.i("boolean sayed","sayed");
            JSONArray questions=gameData.getJSONArray("questions");
            boolean b;
            try {
                b=questions.getJSONObject(0).getJSONArray("choices")!=null;
            }catch(Exception e){
                b=false;
            }
            Log.i("boolean sayed",b+"");
            if (b){
                ArrayList<Question> questionsData=new ArrayList<>();
                for (int i=0;i<questions.length();i++){
                    MCQ question= parseMCQ(questions.getJSONObject(i));
                    questionsData.add(question);
                }
                game.setQuestions(questionsData);
            }
            else {
                ArrayList<Question> questionsData=new ArrayList<>();
                for (int i=0;i<questions.length();i++){
                    TrueAndFalse question= parseTAndF(questions.getJSONObject(i));
                    questionsData.add(question);
                }
                game.setQuestions(questionsData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return game;
    }

    public static TrueAndFalse parseTAndF(JSONObject questionData) {
        TrueAndFalse question=new TrueAndFalse();
        try {
            question.setQuestion(questionData.getString("question"));
            question.setAnswer(questionData.getString("answer"));
            question.setQuestionId(questionData.getInt("questionId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return question;
    }

    public static MCQ parseMCQ(JSONObject questionData){
        MCQ question=new MCQ();
        try {
            question.setQuestion(questionData.getString("question"));
            question.setAnswer(questionData.getString("answer"));
            question.setQuestionId(questionData.getInt("questionId"));
            JSONArray choices= questionData.getJSONArray("choices");
            ArrayList<Choice> choicesData=new ArrayList<>();
            for (int i=0;i<choices.length();i++){
                Choice choice=new Choice();
                choice.setChoiceId(choices.getJSONObject(i).getInt("choiceId"));
                choice.setChoice(choices.getJSONObject(i).getString("choice"));
                choicesData.add(choice);
            }
            question.setChoices(choicesData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return question;
    }

    public static Game parseGame(JsonObject gameInfo){
        Game game = new Game();
        game.setGameId(gameInfo.get("gameId").getAsLong());
        game.setName(gameInfo.get("name").getAsString());
        game.setRate(gameInfo.get("rate").getAsInt());
        JsonArray questions = gameInfo.get("questions").getAsJsonArray();
        JsonObject questionData = questions.get(0).getAsJsonObject();
        boolean type;
        try {
            type=questionData.get("choices").getAsJsonArray()!=null;
            type = true;
        }catch(Exception e){
            type=false;

        }
        Log.i("type", String.valueOf(type));
        for(int i=0;i<questions.size();i++){
            questionData = questions.get(i).getAsJsonObject();

            if(type == true) {
                MCQ question = parseMCQ(questionData.get("choices").getAsJsonArray());
                question.setQuestion(questionData.get("question").getAsString());
                question.setQuestionId(questionData.get("questionId").getAsInt());
                game.addQuestion(question);
            }else {
                TrueAndFalse question = new TrueAndFalse();
                question.setQuestion(questionData.get("question").getAsString());
                question.setQuestionId(questionData.get("questionId").getAsInt());
                game.addQuestion(question);
            }
        }
        return game;
    }

    private static MCQ parseMCQ( JsonArray questionChoices) {
        MCQ question = new MCQ();
        for(int i=0;i<questionChoices.size();i++){
            JsonObject choice = questionChoices.get(i).getAsJsonObject();
            question.addChoice(new Choice(choice.get("choice").getAsString()));
        }
        return  question;
    }

}
