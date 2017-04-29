package com.example.rashwan.playacademy;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UTFDataFormatException;
import java.util.ArrayList;

public class AllCourses extends AppCompatActivity {

    ListView coursesList;
    ArrayList<Course> courses;
    EditText searchBar;
    ImageButton searchButton;
    ImageButton cancel;
    TextView noCourse;
    ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_courses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this,drawer,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        TextView userName = (TextView) findViewById(R.id.userName);
        TextView userEmail = (TextView) findViewById(R.id.userEmail);
        userName.setText(Login.loggedUser.getFirstName());
        userEmail.setText(Login.loggedUser.getEmail());

        initialize();

        String link = ServicesLinks.GET_ALL_COURSES_URL;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                courses = Util.parseCourses(response);
                CourseAdapter adapter = new CourseAdapter(getApplicationContext(), courses);
                coursesList.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);

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
                CourseAdapter searchAdapter=new CourseAdapter(getApplicationContext(),courses);
                coursesList.setAdapter(searchAdapter);
            }
        });

        coursesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Course course= (Course) coursesList.getAdapter().getItem(i);
                startSingleActivity(course);
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
                if(itemPressed.equals("Home")){
                    if(Login.loggedUser.getType().equals("Student"))
                        startActivity(new Intent(AllCourses.this,StudentHome.class));
                    else
                        startActivity(new Intent(AllCourses.this,TeacherHome.class));
                }else if(itemPressed.equals("Profile")){
                    if(Login.loggedUser.getType().equals("Student"))
                        startActivity(new Intent(AllCourses.this,StudentProfile.class));
                    else
                        startActivity(new Intent(AllCourses.this,TeacherProfile.class));
                }else if(itemPressed.equals("Logout")){
                    Login.loggedUser.setUserId(0);
                    startActivity(new Intent(AllCourses.this,Login.class));
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
        coursesList=(ListView) findViewById(R.id.coursesList);
        searchBar=(EditText)findViewById(R.id.searchBar);
        searchButton=(ImageButton) findViewById(R.id.searchButton);
        cancel=(ImageButton) findViewById(R.id.cancelButton);
        noCourse=(TextView)findViewById(R.id.noCourse);
    }

    public ArrayList<Course> search(String courseName){
        ArrayList<Course> indexes=new ArrayList<>();
        for (int i=0;i<courses.size();i++){
            if (courses.get(i).getCourseName().contains(courseName)){
                indexes.add(courses.get(i));
            }
        }
        return indexes;
    }

    public void startSingleActivity(Course  courseData){
        Intent singleCoursePage=new Intent(AllCourses.this, SingleCourse.class);
        Gson gson=new Gson();
        String course=gson.toJson(courseData);
        singleCoursePage.putExtra("course",course);
        startActivity(singleCoursePage);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        if(drawerLayout.isDrawerOpen(Gravity.START))
            drawerLayout.closeDrawer(Gravity.START);
        else {
            if(Login.loggedUser.getType().equals("Student"))
                startActivity(new Intent(AllCourses.this,StudentHome.class));
            else
                startActivity(new Intent(AllCourses.this,TeacherHome.class));
            finish();
        }
    }
}
