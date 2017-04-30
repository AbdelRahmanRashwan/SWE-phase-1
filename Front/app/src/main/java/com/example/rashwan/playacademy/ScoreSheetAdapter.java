package com.example.rashwan.playacademy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rashwan.playacademy.Models.Course;
import com.example.rashwan.playacademy.Models.GameSheet;
import com.example.rashwan.playacademy.Models.MCQ;

import java.util.ArrayList;

/**
 * Created by pc on 4/30/2017.
 */

public class ScoreSheetAdapter extends ArrayAdapter<GameSheet> {

    public ScoreSheetAdapter(@NonNull Context context, ArrayList<GameSheet> scoreBoard) {
        super(context, 0,scoreBoard);
    }

    public View getView(int position , View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.gamesheet_single_item, parent, false);
        }

        GameSheet gameSheet = getItem(position);

        TextView gameName=(TextView)listItem.findViewById(R.id.gameName);
        ImageView icon=(ImageView) listItem.findViewById(R.id.gameIcon);
        TextView score=(TextView) listItem.findViewById(R.id.score);


        gameName.setText(gameSheet.getGame().getName());

        if (gameSheet.getGame().getQuestions().get(0) instanceof MCQ){
            icon.setImageResource(R.drawable.mcq);
        }
        else {
            icon.setImageResource(R.drawable.tf);
        }
        score.setText(String.valueOf(gameSheet.getScore()));

        return listItem;
    }
}
