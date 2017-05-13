package com.example.rashwan.playacademy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rashwan.playacademy.Models.Notification;

import java.util.ArrayList;

/**
 * Created by Ahmed on 13/5/2017.
 */

public class NotificationListItemAdapter extends ArrayAdapter<Notification> {

    ArrayList<Notification> notifications;
    Context mContext;
    public NotificationListItemAdapter(Context context,int resource, ArrayList<Notification> objects) {
        super(context, resource, objects);
        notifications=objects;
        mContext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item_notification, parent, false);
        Notification notification = getItem(position);
        ((TextView)convertView.findViewById(R.id.notificationTitle)).setText(notification.getNotificationTitle());
        ((TextView)convertView.findViewById(R.id.notificationDescription)).setText(notification.getNotificationDescription());

        return convertView;
    }
}
