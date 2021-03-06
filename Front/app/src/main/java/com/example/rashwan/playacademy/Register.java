package com.example.rashwan.playacademy;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    CheckBox teacherCheck;
    EditText educationalMail;
    EditText firstName;
    EditText lastName;
    EditText age;
    EditText email;
    EditText password;
    Button register;
    String firstNameString, lastNameString, emailString, passwordString, educationalMailString,link;
    int ageValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //linking the edit texts
        initialize();

        //what to do when clicking register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting data from the edit texts
                if (isEmpty()){
                    Toast.makeText(Register.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                getText();

                if (checkSpaces()){
                    Toast.makeText(Register.this, "Email can't contain spaces", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (containsAt()){
                    Toast.makeText(Register.this, "Invalid email Address", Toast.LENGTH_SHORT).show();
                }

                JSONObject userData=new JSONObject();
                //adding data to the JSON object
                try {
                    Log.i("email",emailString);

                    userData.put("firstName",firstNameString);
                    userData.put("lastName",lastNameString);
                    userData.put("email",emailString);
                    userData.put("age",ageValue);
                    userData.put("password",passwordString);

                    if (teacherCheck.isChecked()) {
                        userData.put("educationalMail", educationalMailString);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (teacherCheck.isChecked()){
                    link=ServicesLinks.REGISTER_TEACHER_URL;
                }
                else
                    link=ServicesLinks.REGISTER_STUDENT_URL;

                //starting the volley and service call
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,link,userData,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                            // change the confirm from postman
                            String error ="";
                            int id;
                            try {
                                id=response.getInt("userId");
                                if (teacherCheck.isChecked()){
                                    Login.loggedUser =new Teacher();
                                    ((Teacher)Login.loggedUser).setEducationalMail(educationalMailString);
                                }
                                else {
                                    Login.loggedUser =new Student();
                                }
                                Login.loggedUser.setFirstName(firstNameString);
                                Login.loggedUser.setLastName(lastNameString);
                                Login.loggedUser.setEmail(emailString);
                                Login.loggedUser.setAge(ageValue);
                                Login.loggedUser.setUserId(id);

                                if (teacherCheck.isChecked()){
                                    Intent homeTeacher=new Intent(Register.this,TeacherHome.class);
                                    startActivity(homeTeacher);
                                }
                                else{
                                    Intent homeStudent= new Intent(Register.this,StudentHome.class);
                                    startActivity(homeStudent);
                                }

                            }catch (Exception e){
                                try {
                                    error = response.getString("Error");
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                                Toast.makeText(Register.this, error, Toast.LENGTH_SHORT).show();
                            }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Register.this, error.toString() , Toast.LENGTH_SHORT).show();
                            }
                        });
                queue.add(jsonObjectRequest);
            }
        });
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

    private void initialize() {
        teacherCheck=(CheckBox)findViewById(R.id.teacherCheck);
        educationalMail=(EditText)findViewById(R.id.educationalMail);
        firstName=(EditText) findViewById(R.id.firstName);
        lastName=(EditText) findViewById(R.id.lastName);
        age=(EditText) findViewById(R.id.age);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        register=(Button) findViewById(R.id.register);
    }

    private void getText(){
        firstNameString=firstName.getText().toString();
        lastNameString=firstName.getText().toString();
        emailString=email.getText().toString();
        ageValue=Integer.parseInt(age.getText().toString());
        passwordString=password.getText().toString();
        educationalMailString=educationalMail.getText().toString();
    }

    private boolean checkSpaces(){
        return emailString.contains(" ")||(teacherCheck.isChecked()&&educationalMailString.contains(" "));
    }

    private boolean isEmpty() {
        return email.getText().toString().trim().equals("") ||
                firstName.getText().toString().trim().equals("") ||
                lastName.getText().toString().trim().equals("") ||
                password.getText().toString().trim().equals("") ||
                age.getText().toString().trim().equals("");
    }

    private boolean containsAt(){
        return !emailString.contains("@")||(teacherCheck.isChecked()&&!educationalMailString.contains("@"));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Login.class));
        finish();
    }
}
