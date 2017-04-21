package com.example.rashwan.playacademy;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rashwan.playacademy.Models.Course;
import com.example.rashwan.playacademy.Models.Game;
import com.example.rashwan.playacademy.Models.MCQ;

import java.util.ArrayList;

/**
 * Created by Rashwan on 4/21/2017.
 */

public class GameAdapter extends ArrayAdapter <Game> {
    public GameAdapter(Context context, ArrayList<Game> resource) {
        super(context,0 ,resource);
    }

    public View getView(int position , View convertView, ViewGroup parent){
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.game_single_item, parent, false);
        }

        Game game=getItem(position);
        TextView gameName=(TextView)listItem.findViewById(R.id.gameName);
        gameName.setText(game.getName());
        ImageView icon=(ImageView) listItem.findViewById(R.id.gameIcon);
        if (game.getQuestions().get(0) instanceof MCQ){
            icon.setImageResource(R.drawable.mcq);
        }
        else {
            icon.setImageResource(R.drawable.tf);
        }
        return listItem;
    }
}
