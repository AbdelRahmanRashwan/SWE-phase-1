package com.example.rashwan.playacademy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
