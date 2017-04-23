package com.example.rashwan.playacademy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddMCQ extends Fragment implements View.OnClickListener {

    TextView questionStatement;
    TextView[] choices;
    TextView answer;
    Button add;
    Button submit;
    View view;
    Game game;
    String courseName;
    JSONArray questionsJsonArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_mcq, container, false);

        initializeViews();
        initializeListeners();
        return view;
    }

    private void initializeListeners() {
        add.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    private void initializeViews() {
        questionsJsonArray = new JSONArray();
        choices = new TextView[4];
        questionStatement = (TextView) view.findViewById(R.id.questionStatment);
        choices[0] = (TextView) view.findViewById(R.id.choiceAdd1);
        choices[1] = (TextView) view.findViewById(R.id.choiceAdd2);
        choices[2] = (TextView) view.findViewById(R.id.choiceAdd3);
        choices[3] = (TextView) view.findViewById(R.id.choiceAdd4);

        add = (Button) view.findViewById(R.id.add);
        add = (Button) view.findViewById(R.id.submit);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                addQuestion();
                break;
            case R.id.submit:
                addGameRequest();
                getActivity().finish();
                break;
        }
    }

    private void addGameRequest() {
        String link=ServicesLinks.CREATE_MCQ_GAME;
        JSONObject gameInfo = new JSONObject();
        try {
            gameInfo.put("gameName", game.getName());
            gameInfo.put("courseName", courseName);
            gameInfo.put("questions", questionsJsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, link, gameInfo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String confirmation = "";
                try {
                    confirmation = response.getString("confirmation");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), confirmation, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Something went wrong try again later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addQuestion() {
        MCQ question = new MCQ();
        question.setQuestion(questionStatement.getText().toString());
        question.setAnswer(answer.getText().toString());
        JSONArray choicesJson = new JSONArray();
        for(int i=0;i<4;i++) {
            Choice choice = new Choice(choices[i].getText().toString());
            choicesJson.put(choice);
            question.addChoice(choice);
        }
        JSONObject questioneJson = new JSONObject();
        try {
            questioneJson.put("question", questionStatement.getText().toString());
            questioneJson.put("answer", answer.getText().toString());
            questioneJson.put("choices", choicesJson);
            questionsJsonArray.put(questioneJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        game.addQuestion(question);
    }
}
