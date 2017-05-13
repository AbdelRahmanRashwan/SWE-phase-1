package com.example.rashwan.playacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rashwan.playacademy.Models.Game;
import com.example.rashwan.playacademy.Models.Teacher;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class GameInfo extends AppCompatActivity {

    TextView gameName;
    TextView numOfQuestions;
    Button comment;
    Button play;
    Button deleteGame;
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

        deleteGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link=ServicesLinks.CANCEL_GAME_URL+"?gameId="+game.getGameId();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null,
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("confirmation")){
                                Toast.makeText(GameInfo.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(GameInfo.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GameInfo.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonObjectRequest);
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
        deleteGame=(Button)findViewById(R.id.deleteGame);
        if (Login.loggedUser instanceof Teacher){
            deleteGame.setVisibility(View.VISIBLE);
        }
    }
}
