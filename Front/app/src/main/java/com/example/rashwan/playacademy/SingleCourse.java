package com.example.rashwan.playacademy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.rashwan.playacademy.Models.Student;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SingleCourse extends AppCompatActivity {

    TextView courseName;
    TextView courseCreator;
    TextView emptyGames;
    Course course;
    ArrayList<Game>games;
    ListView gamesList;
    Button addGame;
    Button enroll;
    String courseJson;
    boolean isEnrolled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_course);
        initialize();
        setText();

        String link=ServicesLinks.GET_GAMES_IN_COURSE_URL+"?courseName="+course.getCourseName().replaceAll(" ","%20");
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

                            if (gamesJson.length()==0){
                                emptyGames.setVisibility(View.VISIBLE);
                            }

                            GameAdapter adapter=new GameAdapter(getApplicationContext(),games);
                            gamesList.setAdapter(adapter);

                            gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    startPlayGame(i);
                                }
                            });
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

        addGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addGameIntent=new Intent(SingleCourse.this, AddGame.class);
                Bundle courseInfo = new Bundle();
                courseInfo.putString("courseName", courseName.getText().toString());
                addGameIntent.putExtras(courseInfo);
                startActivity(addGameIntent);
            }
        });

        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link=ServicesLinks.ENROLL_URL+"?courseName="+course.getCourseName()+"&studentId="+Login.loggedUser.getUserId();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String ack=response.getString("ack");
                            if (!ack.equals("Enrolled succssessfully.")){
                                Toast.makeText(SingleCourse.this, ack, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(SingleCourse.this, "Enrolled Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),SingleCourse.class);
                                intent.putExtra("course",courseJson);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SingleCourse.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonObjectRequest);
            }
        });
    }

    public void setText(){
        courseName.setText(course.getCourseName());
        courseCreator.setText(course.getCreator().getFirstName()+" "+course.getCreator().getLastName());
    }

    public void initialize(){
        Gson gson=new Gson();
        courseJson=getIntent().getStringExtra("course");
        course = gson.fromJson(courseJson, Course.class);
        courseName=(TextView)findViewById(R.id.courseName);
        courseCreator=(TextView)findViewById(R.id.creatorName);
        emptyGames=(TextView) findViewById(R.id.noGames);
        gamesList=(ListView)findViewById(R.id.gameList);
        addGame=(Button)findViewById(R.id.addGame);
        enroll=(Button)findViewById(R.id.enroll);
        games=new ArrayList<>();
        if (Login.loggedUser instanceof Student)
           studentButtonVisibility();

        if (course.getCreator().getUserId()== Login.loggedUser.getUserId()){
            addGame.setVisibility(View.VISIBLE);
        }

    }
    private void startPlayGame(int i) {
        Intent playGame = new Intent(SingleCourse.this, PlayGame.class);
        Gson gson=new Gson();
        String game=gson.toJson(games.get(i));
        playGame.putExtra("game",game);
        startActivity(playGame);
    }
    public void studentButtonVisibility(){
        String link=ServicesLinks.IS_ENROLLED_URL+"?courseName="+course.getCourseName()+"&studentId="+Login.loggedUser.getUserId();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    isEnrolled=response.getBoolean("ack");
                    if (Login.loggedUser instanceof Student&&!isEnrolled){
                        enroll.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SingleCourse.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        if(Login.loggedUser.getType().equals("Student"))
            startActivity(new Intent(this,StudentHome.class));
        else
            startActivity(new Intent(this,TeacherHome.class));
        finish();
    }

}
