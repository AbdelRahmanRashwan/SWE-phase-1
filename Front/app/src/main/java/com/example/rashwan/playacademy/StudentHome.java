package com.example.rashwan.playacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.rashwan.playacademy.Models.Teacher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;

public class StudentHome extends AppCompatActivity {

    ListView coursesList;
    ArrayList<Course>data;
    EditText searchBar;
    ImageButton searchButton;
    ImageButton cancel;
    TextView noCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        initialize();

        Toast.makeText(this, Login.loggedUser.getUserId()+"", Toast.LENGTH_SHORT).show();

        String link=ServicesLinks.GET_STUDENT_COURSES_URL+"?studentId="+Login.loggedUser.getUserId();
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,link,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray courses=response.getJSONArray("courses");
                            for (int i=0;i<courses.length();i++){
                                Course course = Util.parsCourse(courses.getJSONObject(i).getJSONObject("course"));
                                data.add(course);
                            }
                            response.getJSONArray("courses").getJSONObject(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StudentHome.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                );
        queue.add(jsonObjectRequest);

        final CourseAdapter courseAdapter=new CourseAdapter(this,data);
        coursesList.setAdapter(courseAdapter);

        coursesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startSingleActivity(i);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Course> searchResult=search(searchBar.getText().toString());
                cancel.setVisibility(View.VISIBLE);

                if (searchResult.size()==0){
                    noCourse.setVisibility(View.VISIBLE);
                    coursesList.setAdapter(null);
                }
                else {
                    noCourse.setVisibility(View.GONE);
                    CourseAdapter searchAdapter=new CourseAdapter(getApplicationContext(),searchResult);
                    coursesList.setAdapter(null);
                    coursesList.setAdapter(searchAdapter);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchButton.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.GONE);
                noCourse.setVisibility(View.GONE);
                searchBar.setText("");
                searchBar.clearFocus();
                CourseAdapter searchAdapter=new CourseAdapter(getApplicationContext(),data);
                coursesList.setAdapter(searchAdapter);
            }
        });


    }

    public void initialize(){
        data=new ArrayList<>();
        coursesList=(ListView)findViewById(R.id.coursesList);
        searchBar=(EditText)findViewById(R.id.searchBar);
        searchButton=(ImageButton) findViewById(R.id.searchButton);
        cancel=(ImageButton) findViewById(R.id.cancelButton);
        noCourse=(TextView)findViewById(R.id.noCourse);
    }

    public ArrayList<Course> search(String courseName){
        ArrayList<Course> indexes=new ArrayList<>();
        for (int i=0;i<data.size();i++){
            if (data.get(i).getCourseName().contains(courseName)){
                indexes.add(data.get(i));
            }
        }
        return indexes;
    }

    public void startSingleActivity(int i){
        Toast.makeText(this, data.get(i)+"", Toast.LENGTH_SHORT).show();
        Intent singleCoursePage=new Intent(StudentHome.this, SingleCourse.class);
        singleCoursePage.putExtra("course",(Serializable)data.get(i));
        startActivity(singleCoursePage);
    }
}
