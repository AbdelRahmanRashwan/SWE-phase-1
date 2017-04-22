package com.example.rashwan.playacademy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.example.rashwan.playacademy.Models.TrueAndFalse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.rashwan.playacademy.R.id.question;


public class TrueAndFalseFragment extends Fragment implements View.OnClickListener , GameScore.DialogListener {
    private TextView questionName;
    private TextView questionTrack;
    private ImageButton trueBtn;
    private ImageButton falseBtn;
    private PlayGame activity ;
    private Game game;
    private ArrayList<Question> questions;
    private int questionIndex;
    private String answer;
    private int score;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_true_and_false, null);
        initializeObjects();
        initializeViews();
        showQuestion();

        return view;
    }



    private void showQuestion() {
        questionTrack.setText("Question "+ (questionIndex+1) + " of " + questions.size());
        TrueAndFalse question;
        question = (TrueAndFalse) questions.get(questionIndex);
        questionName.setText(question.getQuestion());
    }

    private void initializeObjects() {
        activity = (PlayGame)getActivity();
        game = activity.getGame();
        questions = game.getQuestions();
        questionIndex = 0;
    }

    private void initializeViews() {
        questionName = (TextView)view.findViewById(R.id.questionTAF);
        questionTrack = (TextView)view.findViewById(R.id.quesTrackTAF);
        trueBtn = (ImageButton)view.findViewById(R.id.trueBtn);
        falseBtn = (ImageButton)view.findViewById(R.id.falseBtn);
        trueBtn.setOnClickListener(this);
        falseBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.trueBtn:
                answer = "true";
                break;
            case R.id.falseBtn:
                answer = "false";
                break;
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        OnResponse requestResponse = new OnResponse();
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
        questionIndex++;
        showQuestion();
    }
    private class OnResponse implements Response.Listener<JSONObject>{

        boolean correct;
        @Override
        public void onResponse(JSONObject response) {
            try {
                correct = response.getBoolean("correct");
            } catch (JSONException e) {
                Toast.makeText(getActivity(), "blla", Toast.LENGTH_SHORT).show();
            }
            if(correct == true) {
                score += 10;
                trueBtn.setBackgroundColor(Color.GREEN);

            }else {
                falseBtn.setBackgroundColor(Color.RED);
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

    @Override
    public void onFinishYesNoDialog(int choice) {
        if(choice == 1){
            getActivity().finish();
        }else{
            questionIndex = 0;
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
