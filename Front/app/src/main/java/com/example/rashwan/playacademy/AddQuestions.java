package com.example.rashwan.playacademy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rashwan.playacademy.Models.Game;

public class AddQuestions extends AppCompatActivity {

    Fragment question;
    String gameType;
    String gameName;
    String courseName;
    Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        initialize();
        if(gameType.equals("MCQ")){
            question = new AddMCQ();
        }else{
            question = new AddTrueAndFalse();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.FragmentContainerAddQuestion, question);
        fragmentTransaction.commit();

    }

    private void initialize() {
        game = new Game();
        Bundle extraInfo = getIntent().getExtras();
        gameType = extraInfo.getString("type");
        gameName = extraInfo.getString("gameName");
        courseName = extraInfo.getString("courseName");

    }

    @Override
    public void finish() {
        super.finish();

    }
}
