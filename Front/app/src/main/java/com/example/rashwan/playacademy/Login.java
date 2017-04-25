package com.example.rashwan.playacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rashwan.playacademy.Models.Student;
import com.example.rashwan.playacademy.Models.Teacher;
import com.example.rashwan.playacademy.Models.User;


import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    Button register;
    Button login;
    EditText email;
    EditText password;
    public static User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerStudentIntent=new Intent(Login.this,Register.class);
                startActivity(registerStudentIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject loginData=new JSONObject();
                String emailValue=email.getText().toString();
                String passwordValue=password.getText().toString();
                if (emailValue.contains(" ")){
                    Toast.makeText(Login.this, "Email can't contain spaces", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    loginData.put("email",emailValue);
                    loginData.put("password",passwordValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST,ServicesLinks.LOGIN_URL , loginData,new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject userData) {


                        String type= null; // the type will be generated from the JSON
                        try {
                            type = userData.getString("type");
                            if (type.equals("Teacher")){
                                loggedUser =new Teacher();
                                loggedUser=Util.parseTeacher(userData);
                                Intent teacherHome=new Intent(Login.this,TeacherHome.class);
                                startActivity(teacherHome);
                            }
                            else if (type.equals("Student")){
                                loggedUser =new Student();
                                loggedUser=Util.parseStudent(userData);
                                Toast.makeText(Login.this, loggedUser.getUserId()+"", Toast.LENGTH_SHORT).show();
                                Intent studentHome=new Intent(Login.this,StudentHome.class);
                                startActivity(studentHome);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonObjectRequest);
            }
        });
    }

    public void initialize(){
        register=(Button)findViewById(R.id.register);
        login=(Button) findViewById(R.id.login);
        email=(EditText) findViewById(R.id.loginEmail);
        password=(EditText) findViewById(R.id.loginPassword);
    }
}
