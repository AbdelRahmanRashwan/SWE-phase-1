package com.example.rashwan.playacademy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentProfile extends AppCompatActivity {

    TextView name;
    TextView email;
    TextView age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
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
        listView.setSelection(2);

    }

    public void setText(){
        name.setText(Login.loggedUser.getFirstName()+" "+Login.loggedUser.getLastName());
        email.setText(Login.loggedUser.getEmail());
        age.setText(Login.loggedUser.getAge()+"");
    }

    public void initialize(){
        name=(TextView)findViewById(R.id.name);
        email=(TextView)findViewById(R.id.email);
        age=(TextView)findViewById(R.id.age);
    }
}
