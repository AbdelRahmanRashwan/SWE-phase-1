package com.example.rashwan.playacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddGame extends AppCompatActivity implements View.OnClickListener{

    TextView courseName;
    EditText gameName;
    ImageButton TAFButton;
    ImageButton MCQButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        initializeViews();
        initializeListeners();

    }

    private void initializeListeners() {
        TAFButton.setOnClickListener(this);
        MCQButton.setOnClickListener(this);
    }

    private void initializeViews() {
        courseName = (TextView) findViewById(R.id.crsName);
        gameName = (EditText) findViewById(R.id.gameName);
        TAFButton = (ImageButton) findViewById(R.id.TAF);
        MCQButton = (ImageButton) findViewById(R.id.MCQ);
        courseName .setText(getIntent().getStringExtra("courseName"));
    }

    @Override
    public void onClick(View view) {
        Intent addQuestions = new Intent(AddGame.this, AddQuestions.class);
        Bundle gameInfo = new Bundle();
        switch (view.getId()){
            case R.id.TAF:
                gameInfo.putString("type","TAF");
                break;
            case R.id.MCQ:
                gameInfo.putString("type","MCQ");
                break;
        }
        if(validateInput()){
            gameInfo.putString("gameName",gameName.getText().toString());
            gameInfo.putString("courseName",courseName.getText().toString());
            addQuestions.putExtras(gameInfo);
            startActivity(addQuestions);
            finish();
        }

    }

    private boolean validateInput() {
        String gameNameString = gameName.getText().toString();
        if(gameNameString.trim().isEmpty()){
            gameName.setError("This field is required");
            return false;
        }
        return true;
    }
}
