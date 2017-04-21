package com.example.rashwan.playacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rashwan.playacademy.Models.Course;
import com.example.rashwan.playacademy.Models.Game;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SingleCourse extends AppCompatActivity {

    TextView courseName;
    TextView courseCreator;
    Course course;
    ArrayList<Game>games;
    ListView gamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_course);
        initialize();
        setText();

        String link=ServicesLinks.GET_GAMES_IN_COURSE_URL+"?courseName="+course.getCourseName();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray gamesJson=response.getJSONArray("games");
                            for (int i=0;i<gamesJson.length();i++){
                                games.add(Util.parseGame(gamesJson.getJSONObject(i)));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SingleCourse.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
        GameAdapter adapter=new GameAdapter(getApplicationContext(),games);
        gamesList.setAdapter(adapter);
    }

    public void setText(){
        courseName.setText(course.getCourseName());
        courseCreator.setText(course.getCreator().getFirstName()+" "+course.getCreator().getLastName());
    }

    public void initialize(){
        Gson gson=new Gson();
        String courseJson=getIntent().getStringExtra("course");
        course = gson.fromJson(courseJson, Course.class);
        courseName=(TextView)findViewById(R.id.courseName);
        courseCreator=(TextView)findViewById(R.id.creatorName);
        gamesList=(ListView)findViewById(R.id.gameList);
        games=new ArrayList<>();
    }
}
