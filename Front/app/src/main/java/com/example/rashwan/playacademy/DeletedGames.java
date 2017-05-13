package com.example.rashwan.playacademy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rashwan.playacademy.Models.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DeletedGames extends AppCompatActivity {

    ListView deletedGames;
    ArrayList<Game> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleted_games);

        deletedGames=(ListView)findViewById(R.id.deletedGameList);
        games=new ArrayList<>();
        String courseName=getIntent().getExtras().getString("courseName");
        String link=ServicesLinks.GET_DELETED_GAMES_IN_COURSE_URL+"?courseName="+courseName.replaceAll(" ","%20");

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray gamesJSON=response.getJSONArray("games");
                    for (int i=0;i<gamesJSON.length();i++){
                        Game game=Util.parseGame(gamesJSON.getJSONObject(i));
                        games.add(game);
                    }

                    GameAdapter gameAdapter=new GameAdapter(getApplicationContext(),games);
                    deletedGames.setAdapter(gameAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeletedGames.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);

        deletedGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Game game=games.get(i);
                String link=ServicesLinks.UN_CANCEL_GAME_URL+"?gameId="+game.getGameId();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("confirmation")){
                                Toast.makeText(DeletedGames.this, "Restored Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(DeletedGames.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DeletedGames.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(jsonObjectRequest);
            }
        });
    }
}
