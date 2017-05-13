package com.example.rashwan.playacademy;

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

import org.json.JSONException;
import org.json.JSONObject;

public class AddComment extends AppCompatActivity {

    EditText comment;
    Button submit;
    String commentValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        initialize();
        final int gameId=getIntent().getExtras().getInt("gameId");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentValue=comment.getText().toString();
                String link=ServicesLinks.ADD_COMMENT_URL+"?gameID="+gameId+"&description="+commentValue.replaceAll(" ","%20")+"&commentorID="+Login.loggedUser.getUserId();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(AddComment.this, response.getString("confirmation"), Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddComment.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonObjectRequest);
            }
        });
    }

    public void initialize(){
        comment=(EditText) findViewById(R.id.comment);
        submit=(Button) findViewById(R.id.submit);
    }
}
