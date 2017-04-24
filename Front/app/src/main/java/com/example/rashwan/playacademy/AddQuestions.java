package com.example.rashwan.playacademy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rashwan.playacademy.Models.Game;
import com.example.rashwan.playacademy.Models.MCQ;

public class AddQuestions extends AppCompatActivity {

    Fragment question;
    Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        initialize();
        if(game.getQuestions().get(0) instanceof MCQ){
            question = new AddMCQ();
        }else{
            question = new AddTrueAndFalse();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.FragmentContainer, question);
        fragmentTransaction.commit();

    }

    private void initialize() {
    }
}
