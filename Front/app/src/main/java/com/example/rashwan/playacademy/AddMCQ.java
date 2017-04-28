package com.example.rashwan.playacademy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddMCQ extends Fragment implements View.OnClickListener {

    EditText questionStatement;
    EditText[] choices;
    EditText answer;
    Button add;
    Button submit;
    Button back;
    View view;
    Game game;
    String courseName;
    String gameName;
    JSONArray questionsJsonArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_mcq, container, false);

        initializeViewsAndExtras();
        initializeListeners();
        return view;
    }

    private void initializeListeners() {
        add.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    private void initializeViewsAndExtras() {
        game = ((AddQuestions)getActivity()).game;
        questionsJsonArray = new JSONArray();
        choices = new EditText[4];
        questionStatement = (EditText) view.findViewById(R.id.questionStatment);
        answer = (EditText)view.findViewById(R.id.answer);
        choices[0] = (EditText) view.findViewById(R.id.choiceAdd1);
        choices[1] = (EditText) view.findViewById(R.id.choiceAdd2);
        choices[2] = (EditText) view.findViewById(R.id.choiceAdd3);
        choices[3] = (EditText) view.findViewById(R.id.choiceAdd4);

        add = (Button) view.findViewById(R.id.add);
        submit = (Button) view.findViewById(R.id.submit);
        back = (Button) view.findViewById(R.id.back);

        courseName = ((AddQuestions)getActivity()).courseName;
        gameName = ((AddQuestions)getActivity()).gameName;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.add:
                if(validateInput()){
                    addQuestion();
                    trimTextViews();
                }
                break;
            case R.id.submit:
                if (questionsJsonArray.length()==0){
                    Toast.makeText(getContext(), "Add at least one question", Toast.LENGTH_SHORT).show();
                    break;
                }
                addGame();
                break;
        }
    }

    private void trimTextViews() {
        questionStatement.setText("");
        answer.setText("");
        for(int i=0;i<choices.length;i++) {
            choices[i].setText("");
        }
    }

    private boolean validateInput() {
        String quesStatment = questionStatement.getText().toString();
        String answerText =  answer.getText().toString();
        String choiceString;
        String error = "This field is required";
        String error2 = "Answer number must be between 1 and 4";

        if(quesStatment.trim().isEmpty() || answerText.trim().isEmpty() ||
                Integer.parseInt(answerText)<=0 || Integer.parseInt(answerText)>4){
            if(quesStatment.trim().isEmpty()) {
                questionStatement.setError(error);
            }else if (answerText.trim().isEmpty()){
                answer.setError(error);
            }else{
                answer.setError(error2);
            }
            return false;
        }
        for(int i=0;i<choices.length;i++){
            choiceString = choices[i].getText().toString();
            if(choiceString.trim().isEmpty()){
                choices[i].setError(error);
                return false;
            }
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
        String link=ServicesLinks.CREATE_MCQ_GAME;
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Log.i("why u ",gameInfo.toString()+ " "+link);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, link, gameInfo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String confirmation ;
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
        MCQ question = new MCQ();
        String answerString = choices[Integer.parseInt(answer.getText().toString()) - 1].getText().toString();
        question.setQuestion(questionStatement.getText().toString());
        question.setAnswer(answerString);
        JSONArray choicesJson = new JSONArray();
        for(int i=0;i<4;i++) {
            Choice choice = new Choice(choices[i].getText().toString());
            try {
                choicesJson.put(new JSONObject().put("choice",choice.getChoice()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            question.addChoice(choice);
        }
        JSONObject questioneJson = new JSONObject();
        try {
            questioneJson.put("question", questionStatement.getText().toString());
            questioneJson.put("answer", answerString);
            questioneJson.put("choices", choicesJson);
            questionsJsonArray.put(questioneJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        game.addQuestion(question);
    }
}
