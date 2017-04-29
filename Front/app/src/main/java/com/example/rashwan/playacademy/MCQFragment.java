package com.example.rashwan.playacademy;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MCQFragment extends Fragment implements View.OnClickListener{

    private TextView questionName;
    private TextView questionTrack;
    private TextView countDown;
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

        view = inflater.inflate(R.layout.fragment_mcq, null);
        initializeViews();
        initializeObjects();

        showNextQuestion();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        questionIndex = 0;
    }

    private void initializeObjects() {
        activity = (PlayGame)getActivity();
        game = activity.getGame();
        questions = game.getQuestions();
        questionIndex = 0;
    }


    private void showNextQuestion() {
        activity.questionProgress.setText("Question "+ (questionIndex+1) + " of " + questions.size());
        answerNumber = -1;
        MCQ question;
        question = (MCQ)game.getQuestions().get(questionIndex);
        questionName.setText(question.getQuestion());
        ArrayList<Choice> choicesArray = question.getChoices();

        for(int j=0;j<choicesArray.size();j++){
            choices[j].setText(choicesArray.get(j).getChoice());
        }
       activity.startTimer();
    }

    private void initializeViews() {
        questionName = (TextView)view.findViewById(R.id.question);
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
                    activity.countDownTimer.cancel();
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    String answer = choices[answerNumber - 1].getText().toString();
                    String requestLink = ServicesLinks.JUDGE_ANSWER +"?gameId="+game.getGameId() + "&questionId="+questions.get(questionIndex).getQuestionId()
                            + "&answer="+answer;
                    requestJudge(requestLink);
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

    private void requestJudge(String requestLink){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, requestLink, null,
                new Response.Listener<JSONObject>() {
                    boolean correct;
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            correct = response.getBoolean("judge");
                            score += (correct == true?10:0);
                            choices[answerNumber - 1].setBackgroundResource(correct == true ?R.drawable.bordergreen:R.drawable.borderred);
                            for(int i=0;i<4;i++) {
                                choices[i].setClickable(false);
                            }
                            delay();
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "Judge error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Volley Error" , Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(jsonObjectRequest);
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
        Bundle scoreBundle = new Bundle();
        scoreBundle.putInt("score",score);
        GameScore gameScore = new GameScore();
        gameScore.setArguments(scoreBundle);
        gameScore.setCancelable(false);
        gameScore.setDialogTitle("Score");
        gameScore.show(fragmentManager,"score");
    }



    protected void delay(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                questionIndex++;
                if(questionIndex >= questions.size()){
                    finishGame(score);
                }else {
                    for(int i=0;i<4;i++) {
                        choices[i].setClickable(true);
                    }
                    choices[answerNumber - 1].setBackgroundResource(R.drawable.border);
                    checkDrawables[answerNumber - 1].setImageResource(R.drawable.checkchoice1);
                    showNextQuestion();
                }
            }
        }, 1000);
    }
}
