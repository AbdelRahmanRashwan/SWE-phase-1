package com.example.rashwan.playacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rashwan.playacademy.Models.Game;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GameInfo extends AppCompatActivity {

    TextView gameName;
    TextView numOfQuestions;
    Button comment;
    Button play;
    Game game;
    String gameJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);
        initialize();

        gameJson = getIntent().getStringExtra("game");
        JsonObject gameJsonObject = new JsonParser().parse(gameJson).getAsJsonObject();
        game = Util.parseGame(gameJsonObject);

        setTexts();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playGameIntent=new Intent(getApplicationContext(),PlayGame.class);
                playGameIntent.putExtra("game",gameJson);
                startActivity(playGameIntent);
            }
        });

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commentsIntent = new Intent(getApplicationContext(),Comments.class);
                Bundle b= new Bundle();
                b.putInt("gameId",(int)game.getGameId());
                commentsIntent.putExtras(b);
                startActivity(commentsIntent);
            }
        });
    }

    public void setTexts(){
        gameName.setText(game.getName());
        numOfQuestions.setText("Number of Questions= "+game.getQuestions().size());
    }

    public void initialize(){
        gameName=(TextView)findViewById(R.id.gameName);
        numOfQuestions=(TextView) findViewById(R.id.numberOfQuestions);
        comment=(Button) findViewById(R.id.viewComments);
        play=(Button) findViewById(R.id.play);
    }
}
