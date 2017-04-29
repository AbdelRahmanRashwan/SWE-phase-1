package com.example.rashwan.playacademy;

import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rashwan.playacademy.Models.Game;
import com.example.rashwan.playacademy.Models.MCQ;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class PlayGame extends AppCompatActivity implements GameScore.DialogListener {
    Fragment question;
    Game game;
    TextView countDown;
    TextView questionProgress;
    ProgressBar countDownProgress;
    int timeCountInMilliSeconds;
    CountDownTimer countDownTimer;
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

    @Override
    public void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();
    }


    public void initialize(){
        Gson gson = new Gson();
        String gameJson = getIntent().getStringExtra("game");
        JsonObject gameJsonObject = new JsonParser().parse(gameJson).getAsJsonObject();
        game = Util.parseGame(gameJsonObject);

        timeCountInMilliSeconds = 10 * 1000;
        countDown = (TextView)findViewById(R.id.countDown);
        countDownProgress = (ProgressBar)findViewById(R.id.progressBarCircle);
        questionProgress = (TextView)findViewById(R.id.questionTrack);
        setProgressBarValues();
    }

    private void setProgressBarValues() {
        countDownProgress.setMax(0);
        countDownProgress.setProgress(0);
        countDownProgress.setMax( (int)timeCountInMilliSeconds / 1000);
        countDownProgress.setProgress( (int)timeCountInMilliSeconds / 1000);
    }

    protected void startTimer() {
        countDownProgress.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {

            public void onTick(long millisUntilFinished) {
                countDownProgress.setProgress(0);
                countDown.setText(String.valueOf(millisUntilFinished / 1000));
                countDownProgress.setProgress((int)(millisUntilFinished / 1000));
            }

            public void onFinish() {
                countDownProgress.setProgress(0);
                countDown.setText(String.valueOf(0));
                countDownProgress.setVisibility(View.INVISIBLE);
                countDown.setText("Time Up!");
                timeCountInMilliSeconds = 10 * 1000;
                setProgressBarValues();
                if(question instanceof TrueAndFalseFragment)
                    ((TrueAndFalseFragment)question).delay();
                else ((MCQFragment)question).delay();
            }
        };
        countDownTimer.start();
    }


    public Game getGame(){
        return game;
    }

    @Override
    public void onFinishYesNoDialog(int choice) {
        countDownTimer.cancel();
        Log.i("Choice", String.valueOf(choice));
        if(choice == 1){
            finish();
        }else{
            getSupportFragmentManager().beginTransaction().remove(question).commit();
            if(game.getQuestions().get(0) instanceof MCQ){
                question = new MCQFragment();
            }else{
                question = new TrueAndFalseFragment();
            }
            getSupportFragmentManager().beginTransaction().add(R.id.FragmentContainer, question).commit();
        }
    }
}
