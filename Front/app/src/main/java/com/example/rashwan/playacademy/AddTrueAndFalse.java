package com.example.rashwan.playacademy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.rashwan.playacademy.Models.TrueAndFalse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AddTrueAndFalse extends Fragment implements View.OnClickListener{

    EditText questionStatement;
    View view;

    Button add;
    Button submit;
    Button back;
    ImageButton trueImgBtn;
    ImageButton falseImgBtn;

    Game game;
    String courseName;
    String gameName;
    JSONArray questionsJsonArray;
    String answerString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_true_and_false, container, false);
        initializeViewsAndExtras();
        initializeListeners();
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.add:
                if(validateInput()){
                    addQuestion();
                    trimViews();
                }
                break;
            case R.id.submit:
                if (questionsJsonArray.length()==0){
                    Toast.makeText(getContext(), "Add at least on question", Toast.LENGTH_SHORT).show();
                    break;
                }
                addGame();

                break;
            case R.id.trueImgBtn:
                answerString = "true";
                trueImgBtn.setBackgroundResource(R.drawable.filledcirclegreen);
                falseImgBtn.setBackgroundResource(R.drawable.circlered);
                break;
            case R.id.falseImgBtn:
                answerString = "false";
                falseImgBtn.setBackgroundResource(R.drawable.filledcirclegreen);
                trueImgBtn.setBackgroundResource(R.drawable.circlegreen);
                break;
            case R.id.back:
                getActivity().finish();
                break;
        }
    }

    private void initializeListeners() {
        add.setOnClickListener(this);
        submit.setOnClickListener(this);

        trueImgBtn.setOnClickListener(this);
        falseImgBtn.setOnClickListener(this);
    }

    private void initializeViewsAndExtras() {
        game = ((AddQuestions)getActivity()).game;
        questionsJsonArray = new JSONArray();
        questionStatement = (EditText) view.findViewById(R.id.questionStatmentTAF);

        add = (Button) view.findViewById(R.id.add);
        submit = (Button) view.findViewById(R.id.submit);
        back = (Button) view.findViewById(R.id.back);

        trueImgBtn =(ImageButton)view.findViewById(R.id.trueImgBtn);
        falseImgBtn =(ImageButton)view.findViewById(R.id.falseImgBtn);

        courseName = ((AddQuestions)getActivity()).courseName;
        gameName = ((AddQuestions)getActivity()).gameName;
        answerString = "";
    }

    private void trimViews() {
        falseImgBtn.setBackgroundResource(R.drawable.circlered);
        trueImgBtn.setBackgroundResource(R.drawable.circlegreen);
        questionStatement.setText("");
    }

    private boolean validateInput() {
        String quesStatment = questionStatement.getText().toString();
        String error = "This field is required";

        if (quesStatment.trim().isEmpty() || answerString.trim().isEmpty()) {
            if (quesStatment.trim().isEmpty()) {
                questionStatement.setError(error);
            } else {
                Toast.makeText(getActivity(), "You must choose an answer", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }

    private void addGame() {
        JSONObject gameInfo = new JSONObject();
        game.setName(gameName);
        try {

            gameInfo.put("gameName", gameName);
            gameInfo.put("courseName", courseName);
            gameInfo.put("questions", questionsJsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        addGameRequest(gameInfo);
    }

    private void addGameRequest(JSONObject gameInfo) {
        String link=ServicesLinks.CREATE_GAME;
        Log.i("why u ",gameInfo.toString()+ " "+link);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, link, gameInfo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String confirmation = "";
                try {
                    confirmation = response.getString("confirmation");
                    Toast.makeText(getActivity(), confirmation, Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Something went wrong try again later", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void addQuestion() {
        TrueAndFalse question = new TrueAndFalse();
        question.setQuestion(questionStatement.getText().toString());
        question.setAnswer(answerString);
        JSONObject questioneJson = new JSONObject();
        try {
            questioneJson.put("question", questionStatement.getText().toString());
            questioneJson.put("answer", answerString);
            questionsJsonArray.put(questioneJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        game.addQuestion(question);
    }

}
