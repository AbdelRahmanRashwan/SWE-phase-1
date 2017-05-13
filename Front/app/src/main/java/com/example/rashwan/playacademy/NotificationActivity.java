package com.example.rashwan.playacademy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rashwan.playacademy.Models.Notification;

import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        final ListView listView = (ListView) findViewById(R.id.notificationListView);

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String link="";
        Log.i("Notification","Notification ACtivity create");
        if(Login.loggedUser.getType().equals("Student"))
            link=ServicesLinks.NOTIFICATION_STUDENT_URL+"?id="+Login.loggedUser.getUserId();
        else
            link=ServicesLinks.NOTIFICATION_TEACHER_URL+"?id="+Login.loggedUser.getUserId();


        Log.i("LINK",link);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, link, null,
           new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Notification> n =Util.parseNotification(response);
                NotificationListItemAdapter notificationListItemAdapter = new NotificationListItemAdapter(getApplicationContext(),R.layout.row_item_notification,n);
                listView.setAdapter(notificationListItemAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }
    @Override
    public void onBackPressed() {
        if(Login.loggedUser.getType().equals("Student"))
            startActivity(new Intent(this,StudentHome.class));
        else
            startActivity(new Intent(this,TeacherHome.class));
        finish();

    }
}
