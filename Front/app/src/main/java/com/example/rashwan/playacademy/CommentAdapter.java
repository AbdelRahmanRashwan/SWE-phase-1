package com.example.rashwan.playacademy;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rashwan.playacademy.Models.Comment;
import com.example.rashwan.playacademy.Models.Course;

import java.util.ArrayList;

/**
 * Created by Rashwan on 5/13/2017.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {
    public CommentAdapter(Context context, ArrayList<Comment> objects) {
        super(context, 0, objects);
    }

    public View getView(int position , View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.comment_single_item, parent, false);
        }

        Comment comment=getItem(position);
        TextView commentorName=(TextView)listItem.findViewById(R.id.commentorName);
        commentorName.setText(comment.getCommentor().getFirstName()+" "+comment.getCommentor().getLastName());
        TextView desc=(TextView) listItem.findViewById(R.id.commentDescription);
        desc.setText(comment.getDescription());

        return listItem;
    }
}
