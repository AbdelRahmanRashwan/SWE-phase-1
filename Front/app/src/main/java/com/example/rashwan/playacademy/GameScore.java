package com.example.rashwan.playacademy;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class GameScore extends DialogFragment {
    Button btnTryAgain,btnBack;
    static String DialogBoxTitle;

    public interface DialogListener {
        void onFinishYesNoDialog(int choice);
    }

    //---empty constructor required
    public GameScore(){

    }
    //---set the title of the dialog window---
    public void setDialogTitle(String title) {
        DialogBoxTitle= title;
    }

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState ) {

        View view= inflater.inflate(R.layout.fragment_game_score, container);
        //---get the Button views---
        btnTryAgain = (Button) view.findViewById(R.id.btnTryAgain);
        btnBack = (Button) view.findViewById(R.id.btnBack);

        // Button listener
        btnTryAgain.setOnClickListener(btnListener);
        btnBack.setOnClickListener(btnListener);

        //---set the title for the dialog
        getDialog().setTitle(DialogBoxTitle);

        return view;
    }

    //---create an anonymous class to act as a button click listener
    private OnClickListener btnListener = new OnClickListener()
    {
        public void onClick(View v)
        {
            int choice;
            switch (v.getId()){
                case R.id.btnTryAgain:
                    choice = 2;
                    break;
                case R.id.btnBack:
                    choice = 1;
                    break;
            }
            DialogListener dialogListener = (DialogListener)getActivity();
            dismiss();
        }
    };
}
