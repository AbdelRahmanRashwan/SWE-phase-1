package com.example.rashwan.playacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StudentHome extends AppCompatActivity {

    Bundle studentData;
    TextView name;
    LinearLayout profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        studentData=getIntent().getExtras();
        name=(TextView)findViewById(R.id.name);
        profile=(LinearLayout)findViewById(R.id.profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent=new Intent(StudentHome.this,StudentProfile.class);
                if (studentData!=null)
                    profileIntent.putExtras(studentData);
                startActivity(profileIntent);
            }
        });

        if (studentData!=null){
            name.setText(studentData.getString("firstName")+" "+studentData.getString("lastName"));
        }

    }
}
