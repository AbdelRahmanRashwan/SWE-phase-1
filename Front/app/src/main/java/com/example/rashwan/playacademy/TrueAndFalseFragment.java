package com.example.rashwan.playacademy;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rashwan.playacademy.Models.Game;
import com.example.rashwan.playacademy.Models.Question;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TrueAndFalseFragment extends Fragment implements View.OnClickListener  {
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

        showQuestions();

        return view;
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

    private void showQuestions() {
        trueBtn.setBackgroundResource(R.drawable.circlegreen);
        falseBtn.setBackgroundResource(R.drawable.circlered);
        questionTrack.setText("Question "+ (questionIndex+1) + " of " + questions.size());
        Log.i("Questions", String.valueOf(questions));
        Question question = questions.get(questionIndex);
        questionName.setText(question.getQuestion());
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
        String requestLink = ServicesLinks.JUDGE_ANSWER +"?gameId="+game.getGameId() + "&questionId="+questions.get(questionIndex).getQuestionId()
                + "&answer="+answer;
        requestJudge(requestLink);

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
                            if(answer.equals("true")) {
                                trueBtn.setBackgroundResource(correct == true?R.drawable.filledcirclegreen:R.drawable.filledcirclered);
                            }else{
                                falseBtn.setBackgroundResource(correct == true?R.drawable.filledcirclegreen:R.drawable.filledcirclered);
                            }
                            trueBtn.setClickable(false);
                            falseBtn.setClickable(false);
                            delay();
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
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


    private void finishGame(int score) {
        if(Login.loggedUser.getType().equals("Student")){
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            JSONObject gameSheet = new JSONObject();
            try {
                gameSheet.put("gameId",game.getGameId());
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
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getActivity(),  "Some Thing went wrong couldn't save your score", Toast.LENGTH_SHORT).show();
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




    private void delay(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                questionIndex++;
                if(questionIndex >= questions.size()){
                    finishGame(score);
                }else {
                    trueBtn.setClickable(true);
                    falseBtn.setClickable(true);
                    showQuestions();
                }
            }
        }, 2000);
    }
}
