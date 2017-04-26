package com.example.rashwan.playacademy;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rashwan.playacademy.Models.Course;
import com.example.rashwan.playacademy.Models.Student;
import com.example.rashwan.playacademy.Models.Teacher;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;

public class StudentHome extends AppCompatActivity {

    ListView coursesList;
    ArrayList<Course>data;
    TextView noCourse;
    Button goSearch;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this,drawer,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        initialize();
        String link=ServicesLinks.GET_STUDENT_COURSES_URL+"?studentId="+Login.loggedUser.getUserId();
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,link,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray courses=response.getJSONArray("courses");
                            for (int i=0;i<courses.length();i++){
                                Log.i("index",i+"");
                                Course course = Util.parseCourse(courses.getJSONObject(i).getJSONObject("course"));
                                Toast.makeText(StudentHome.this, course.toString(), Toast.LENGTH_SHORT).show();
                                data.add(course);
                            }
                            final CourseAdapter courseAdapter=new CourseAdapter(getApplicationContext(),data);
                            coursesList.setAdapter(courseAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (data.size()==0){
                            noCourse.setVisibility(View.VISIBLE);
                            goSearch.setVisibility(View.VISIBLE);
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

        coursesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startSingleActivity(i);
            }
        });

        goSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSearchIntent=new Intent(getApplicationContext(),AllCourses.class);
                startActivity(goSearchIntent);
            }
        });

        ArrayList<String> navigationItems = new ArrayList<>();
        navigationItems.add("Home");
        navigationItems.add("Courses");
        navigationItems.add("Profile");
        navigationItems.add("Logout");


        ListView listView = (ListView) findViewById(R.id.navList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,navigationItems);
        listView.setAdapter(adapter);
        listView.setSelection(0);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
    public void initialize(){
        data=new ArrayList<>();
        coursesList=(ListView)findViewById(R.id.coursesList);
        noCourse=(TextView)findViewById(R.id.noCourse);
        goSearch=(Button)findViewById(R.id.goSearch);
    }

    public void startSingleActivity(int i){
        Intent singleCoursePage=new Intent(StudentHome.this, SingleCourse.class);
        Gson gson=new Gson();
        String course=gson.toJson(data.get(i));
        singleCoursePage.putExtra("course",course);
        startActivity(singleCoursePage);
    }
}
