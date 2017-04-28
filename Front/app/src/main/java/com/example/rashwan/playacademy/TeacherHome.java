package com.example.rashwan.playacademy;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rashwan.playacademy.Models.Course;
import com.example.rashwan.playacademy.Models.Teacher;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class TeacherHome extends AppCompatActivity {

    ListView listView;
    Button addCourse;
    ArrayList<Course> courses;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this,drawer,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        TextView userName = (TextView) findViewById(R.id.userName);
        TextView userEmail = (TextView) findViewById(R.id.userEmail);
        userName.setText(Login.loggedUser.getFirstName());
        userEmail.setText(Login.loggedUser.getEmail());

        initialize();

        String link=ServicesLinks.GET_COURSES_BY_TEACHER_URL+"?teacherId="+Login.loggedUser.getUserId();
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                courses=Util.parseCourses(response);
                CourseAdapter adapter=new CourseAdapter(getApplicationContext(),courses);
                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AddCourse
                        .class);
                startActivity(intent);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startSingleActivity(i);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemPressed = ((TextView)view.findViewById(android.R.id.text1)).getText().toString();
                if (itemPressed.equals("Courses")){
                    startActivity(new Intent(TeacherHome.this,AllCourses.class));
                }else if(itemPressed.equals("Profile")){
                    startActivity(new Intent(TeacherHome.this,TeacherProfile.class));
                }else if(itemPressed.equals("Logout")){
                    Login.loggedUser.setUserId(0);
                    startActivity(new Intent(TeacherHome.this,Login.class));
                }
                drawer.closeDrawer(Gravity.START);
            }
        });
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
        listView=(ListView)findViewById(R.id.coursesList);
        addCourse=(Button)findViewById(R.id.addCourse);
    }

    public void startSingleActivity(int i){
        Intent singleCoursePage=new Intent(TeacherHome.this, SingleCourse.class);
        Gson gson=new Gson();
        String course=gson.toJson(courses.get(i));
        singleCoursePage.putExtra("course",course);
        startActivity(singleCoursePage);
    }
}
