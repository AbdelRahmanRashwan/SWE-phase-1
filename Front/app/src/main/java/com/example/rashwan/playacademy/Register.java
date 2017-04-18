package com.example.rashwan.playacademy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    CheckBox teacherCheck;
    EditText educationalMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        teacherCheck=(CheckBox)findViewById(R.id.teacherCheck);
        educationalMail=(EditText)findViewById(R.id.educationalMail);

        teacherCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (teacherCheck.isChecked()){
                    educationalMail.setVisibility(View.VISIBLE);
                }
                else{
                    educationalMail.setVisibility(View.GONE);
                }
            }
        });
    }
}
