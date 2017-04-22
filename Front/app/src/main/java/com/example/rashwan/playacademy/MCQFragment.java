package com.example.rashwan.playacademy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rashwan.playacademy.Models.Choice;
import com.example.rashwan.playacademy.Models.Game;
import com.example.rashwan.playacademy.Models.MCQ;
import com.example.rashwan.playacademy.Models.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MCQFragment extends Fragment implements View.OnClickListener , GameScore.DialogListener{

    private TextView questionName;
    private TextView questionTrack;
    private TextView choices[];
    private Button next;
    private PlayGame activity ;
    private Game game;
    private ArrayList<Question> questions;
    private int answerNumber;
    private int questionIndex;
    private int score;
    private ImageView[] checkDrawables;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initializeViews();
        initializeObjects();
        view = inflater.inflate(R.layout.fragment_mcq, null);

        showQuestion();

        return view;
    }

    private void initializeObjects() {
        activity = (PlayGame)getActivity();
        game = activity.getGame();
        questions = game.getQuestions();
        questionIndex = 0;
    }

    private void showQuestion() {
        questionTrack.setText("Question "+ (questionIndex+1) + " of " + questions.size());
        answerNumber = -1;
        MCQ question;
        question = (MCQ)game.getQuestions().get(questionIndex);
        questionName.setText(question.getQuestion());
        ArrayList<Choice> choicesArray = question.getChoices();

        for(int j=0;j<choicesArray.size();j++){
            choices[j].setText(choicesArray.get(j).getChoice());
        }
    }

    private void initializeViews() {
        questionName = (TextView)view.findViewById(R.id.question);
        questionTrack = (TextView)view.findViewById(R.id.quesTrack);
        next = (Button)view.findViewById(R.id.next);

        choices = new TextView[4];
        choices[0] = (TextView)view.findViewById(R.id.choice1);
        choices[1] = (TextView)view.findViewById(R.id.choice2);
        choices[2] = (TextView)view.findViewById(R.id.choice3);
        choices[3] = (TextView)view.findViewById(R.id.choice4);

        checkDrawables = new ImageView[4];
        checkDrawables[0] = (ImageView)view.findViewById(R.id.checker1);
        checkDrawables[1] = (ImageView)view.findViewById(R.id.checker2);
        checkDrawables[2] = (ImageView)view.findViewById(R.id.checker3);
        checkDrawables[3] = (ImageView)view.findViewById(R.id.checker4);

        next.setOnClickListener(this);
        for(int i=0;i<choices.length;i++){
            choices[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                if(answerNumber == -1){
                    Toast.makeText(getActivity(), "You must choose an answer", Toast.LENGTH_SHORT);
                }else{

                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    OnResponse requestResponse = new OnResponse();

                    String answer = choices[answerNumber].getText().toString();
                    String requestLink = ServicesLinks.JUDGE_ANSWER +"?gameId="+game.getGameId() + "questionId="+questions.get(questionIndex).getQuestionId()
                                + "&answer="+answer;
                    JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, requestLink, null, requestResponse
                        , new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    queue.add(jsonObjectRequest);
                }
                break;
            case R.id.choice1:
                answerNumber = 1;
                check(checkDrawables, answerNumber - 1);
                break;
            case R.id.choice2:
                answerNumber = 2;
                check(checkDrawables, answerNumber - 1);
                break;
            case R.id.choice3:
                answerNumber = 3;
                check(checkDrawables, answerNumber - 1);
                break;
            case R.id.choice4:
                answerNumber = 4;
                check(checkDrawables, answerNumber - 1);
                break;
        }
    }

    private void check(ImageView[] checkDrawables, int i) {

        checkDrawables[i].setImageResource(R.drawable.checkchoice2);
        i = (i+1)%4;
        checkDrawables[i].setImageResource(R.drawable.checkchoice1);
        i = (i+1)%4;
        checkDrawables[i].setImageResource(R.drawable.checkchoice1);
        i = (i+1)%4;
        checkDrawables[i].setImageResource(R.drawable.checkchoice1);
    }

    @Override
    public void onFinishYesNoDialog(int choice) {
        if(choice == 1){
            getActivity().finish();
        }else{
            questionIndex = 0;
        }
    }

    private class OnResponse implements Response.Listener<JSONObject>{

        boolean correct;
        @Override
        public void onResponse(JSONObject response) {
            try {
                score += 10;
                correct = response.getBoolean("correct");
            } catch (JSONException e) {
                Toast.makeText(getActivity(), "Sorry somthing went wrong", Toast.LENGTH_SHORT).show();
            }
            if(correct == true) {
                choices[answerNumber].setBackgroundColor(Color.GREEN);

            }else {
                choices[answerNumber].setBackgroundColor(Color.RED);
            }
            Handler myHandler = new Handler();
            myHandler.postDelayed(mMyRunnable, 4000);
            if(questionIndex == questions.size() - 1){
                finishGame(score);
            }
            questionIndex++;
            showQuestion();
        }
    }

    private void finishGame(int score) {
        if(Login.loggedUser.getType().equals("Student")){
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            JSONObject gameSheet = new JSONObject();
            try {
                gameSheet.put("gameId", game.getGameId());
                gameSheet.put("studentId", Login.loggedUser.getUserId());
                gameSheet.put("score",score);
                gameSheet.put("rate",0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String requestLink = ServicesLinks.UPDATE_SCORE_LINK;
            JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, requestLink, gameSheet, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(jsonObjectRequest);

        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        GameScore gameScore = new GameScore();
        gameScore.setScore(score);
        gameScore.setCancelable(false);
        gameScore.setDialogTitle("Score");
        gameScore.show(fragmentManager,"score");
    }


    private Runnable mMyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            //Change state here
        }
    };
}
