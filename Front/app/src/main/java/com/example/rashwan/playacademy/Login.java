package com.example.rashwan.playacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    Button register;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerStudentIntent=new Intent(Login.this,Register.class);
                startActivity(registerStudentIntent);
            }
        });

        login=(Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data=ServicesLinks.LOGIN_URL+"?"; // add data

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,ServicesLinks.LOGIN_URL +data, null,                        new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject userData) {

                        String type= null; // the type will be generated from the JSON
                        try {
                            type = userData.getString("userType");
                            if (type.equals("teacher")){
                                Intent homeTeacher=new Intent(Login.this,TeacherHome.class);

                                Bundle teacherData=new Bundle();
                                teacherData.putInt("ID",userData.getInt("ID"));
                                teacherData.putString("firstName",userData.getString("firstName"));
                                teacherData.putString("lastName",userData.getString("lastName"));
                                teacherData.putString("email",userData.getString("email"));
                                teacherData.putString("educationalMail",userData.getString("educationalMail"));
                                teacherData.putInt("age",userData.getInt("age"));

                                homeTeacher.putExtras(teacherData);
                                startActivity(homeTeacher);
                            }
                            else if (type.equals("student")){
                                Intent homeStudent=new Intent(Login.this,StudentHome.class);

                                Bundle studentData=new Bundle();
                                studentData.putInt("ID",userData.getInt("ID"));
                                studentData.putString("firstName",userData.getString("firstName"));
                                studentData.putString("lastName",userData.getString("lastName"));
                                studentData.putString("email",userData.getString("email"));
                                studentData.putInt("age",userData.getInt("age"));

                                homeStudent.putExtras(studentData);
                                startActivity(homeStudent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Intent homeStudent=new Intent(Login.this,StudentHome.class);

                        startActivity(homeStudent);
                        Toast.makeText(Login.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonObjectRequest);
            }
        });
    }
}
