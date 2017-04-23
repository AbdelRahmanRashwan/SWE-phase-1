package com.example.rashwan.playacademy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.rashwan.playacademy.Models.Game;
import com.example.rashwan.playacademy.Models.MCQ;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class PlayGame extends AppCompatActivity implements GameScore.DialogListener {
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
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.FragmentContainer, question);
        fragmentTransaction.commit();

    }

    public void initialize(){

        Gson gson = new Gson();
        String gameJson = getIntent().getStringExtra("game");
        JsonObject gameJsonObject = new JsonParser().parse(gameJson).getAsJsonObject();
        game = Util.parseGame(gameJsonObject);
    }


    public Game getGame(){
        return game;
    }

    @Override
    public void onFinishYesNoDialog(int choice) {
        Log.i("Choice", String.valueOf(choice));
        if(choice == 1){
            finish();
        }else{
            getSupportFragmentManager().beginTransaction().remove(question);
            if(game.getQuestions().get(0) instanceof MCQ){
                question = new MCQFragment();
            }else{
                question = new TrueAndFalseFragment();
            }
            getSupportFragmentManager().beginTransaction().add(R.id.FragmentContainer, question).commit();
        }
    }
}
