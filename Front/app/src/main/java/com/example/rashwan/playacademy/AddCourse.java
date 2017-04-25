package com.example.rashwan.playacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.example.rashwan.playacademy.Models.Course;
import com.example.rashwan.playacademy.Models.Teacher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddCourse extends AppCompatActivity {

    EditText name;
    EditText description;
    Button submit;
    String nameValue, descriptionValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        initialize();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameValue = name.getText().toString();
                descriptionValue = description.getText().toString();

                nameValue=nameValue.replaceAll(" ","%20");
                descriptionValue=descriptionValue.replaceAll(" ","%20");

                if (nameValue.trim().equals("")||descriptionValue.trim().equals("")){
                    Toast.makeText(AddCourse.this, "Please Enter all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                String link = ServicesLinks.ADD_COURSE_URL + "?courseName=" + nameValue + "&courseDescription=" + descriptionValue +
                        "&teacherId=" + Login.loggedUser.getUserId();
                Log.i("link",link);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(AddCourse.this, "before try", Toast.LENGTH_SHORT).show();
                        try {
                            String sc = response.getString("ack");
                            if (!sc.equals("Course created successfully")){
                                Toast.makeText(AddCourse.this, sc, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(AddCourse.this, sc, Toast.LENGTH_SHORT).show();
                                Intent home=new Intent(getApplicationContext(),TeacherHome.class);
                                startActivity(home);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddCourse.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                );
                queue.add(jsonObjectRequest);
            }
        });
    }

    public void initialize(){
        name=(EditText) findViewById(R.id.courseName);
        description=(EditText)findViewById(R.id.description);
        submit=(Button) findViewById(R.id.submit);
    }

}
