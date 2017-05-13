package com.example.rashwan.playacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rashwan.playacademy.Models.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Comments extends AppCompatActivity {

    ListView commentsList;
    Button addComment;
    ArrayList<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initialize();


        final int gameId=getIntent().getExtras().getInt("gameId");
        String link=ServicesLinks.GET_COMMENTS_URL+"?gameID="+gameId;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray commentsJSON =response.getJSONArray("comments");
                    comments=Util.parseComments(commentsJSON);
                    CommentAdapter commentAdapter=new CommentAdapter(getApplicationContext(),comments);
                    commentsList.setAdapter(commentAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Comments.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCommentIntent=new Intent(getApplicationContext(),AddComment.class);
                addCommentIntent.putExtra("gameId",gameId);
                startActivity(addCommentIntent);
            }
        });
    }

    private void initialize() {
        commentsList=(ListView) findViewById(R.id.commentsList);
        addComment=(Button) findViewById(R.id.addComment);
    }
}
