package com.example.rashwan.playacademy;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GameScore extends DialogFragment {
    Button btnTryAgain,btnBack;
    TextView scoreText;
    static String DialogBoxTitle;
    int score=0;
    public GameScore(){}

    public interface DialogListener {
        void onFinishYesNoDialog(int choice);
    }

    public void setDialogTitle(String title) {
        DialogBoxTitle= title;
    }

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState ) {

        View view= inflater.inflate(R.layout.fragment_game_score, container);
        btnTryAgain = (Button) view.findViewById(R.id.btnTryAgain);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        scoreText = (TextView) view.findViewById(R.id.score);

        scoreText.setText(Integer.toString(score));

        btnTryAgain.setOnClickListener(btnListener);
        btnBack.setOnClickListener(btnListener);

        getDialog().setTitle(DialogBoxTitle);

        return view;
    }

    public void setScore(int score){
        this.score = score;
    }

    @Override
    public void setArguments(Bundle args) {
        score = args.getInt("score");
    }

    private OnClickListener btnListener = new OnClickListener()
    {
        public void onClick(View v)
        {
            int choice = 1;
            switch (v.getId()){
                case R.id.btnTryAgain:
                    choice = 2;
                    break;
                case R.id.btnBack:
                    choice = 1;
                    break;
            }
            DialogListener dialogListener = (DialogListener)getActivity();
            dialogListener.onFinishYesNoDialog(choice);
            dismiss();
        }
    };

}
