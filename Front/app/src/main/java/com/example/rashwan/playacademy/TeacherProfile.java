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
import android.widget.ListView;
import android.widget.TextView;

import com.example.rashwan.playacademy.Models.Teacher;

import java.util.ArrayList;

public class TeacherProfile extends AppCompatActivity {

    TextView name;
    TextView email;
    TextView age;
    TextView educationalMail;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
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
        setText();

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
                    startActivity(new Intent(TeacherProfile.this,AllCourses.class));
                }else if (itemPressed.equals("Courses")){
                    startActivity(new Intent(TeacherProfile.this,AllCourses.class));
                }else if(itemPressed.equals("Logout")){
                    Login.loggedUser.setUserId(0);
                    startActivity(new Intent(TeacherProfile.this,Login.class));
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

    public void setText(){
        name.setText(Login.loggedUser.getFirstName()+" "+Login.loggedUser.getLastName());
        email.setText(Login.loggedUser.getEmail());
        age.setText(Login.loggedUser.getAge()+"");
        educationalMail.setText(((Teacher) Login.loggedUser).getEducationalMail());
    }

    public void initialize(){
        name=(TextView)findViewById(R.id.name);
        email=(TextView)findViewById(R.id.email);
        age=(TextView)findViewById(R.id.age);
        educationalMail=(TextView)findViewById(R.id.educationalMail);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        if(drawerLayout.isDrawerOpen(Gravity.START))
            drawerLayout.closeDrawer(Gravity.START);
        else {
            startActivity(new Intent(this, TeacherHome.class));
            finish();
        }
    }
}
