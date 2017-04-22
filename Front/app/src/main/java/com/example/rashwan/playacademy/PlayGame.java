package com.example.rashwan.playacademy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rashwan.playacademy.Models.Game;
import com.example.rashwan.playacademy.Models.MCQ;
import com.example.rashwan.playacademy.Models.Question;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PlayGame extends AppCompatActivity {
    Fragment question;
    Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        initialize();
        if(game.getQuestions().get(0) instanceof MCQ){
            question = new MCQFragment();
        }else{
            question = new TrueAndFalseFragment();
            Toast.makeText(this,"hi", Toast.LENGTH_LONG).show();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.FragmentContainer, question);
        fragmentTransaction.commit();

    }

    public void initialize(){
        Gson gson = new Gson();
        String gameJson = getIntent().getStringExtra("course");
        game = gson.fromJson(gameJson, Game.class);
    }

    public Game getGame(){
        return game;
    }
}
