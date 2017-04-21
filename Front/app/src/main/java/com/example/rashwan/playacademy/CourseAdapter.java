package com.example.rashwan.playacademy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rashwan.playacademy.Models.Course;

import java.util.ArrayList;

/**
 * Created by Rashwan on 4/19/2017.
 */

public class CourseAdapter extends ArrayAdapter<Course> {

    public CourseAdapter(@NonNull Context context, ArrayList<Course> courses) {
        super(context, 0,courses);
    }

    public View getView(int position , View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.course_single_item, parent, false);
        }

        Course course=getItem(position);
        TextView courseName=(TextView)listItem.findViewById(R.id.courseName);
        courseName.setText(course.getCourseName());
        TextView publisherName=(TextView) listItem.findViewById(R.id.publisherName);
        publisherName.setText("By "+course.getCreator().getFirstName()+" "+course.getCreator().getLastName());

        return listItem;
    }
}
