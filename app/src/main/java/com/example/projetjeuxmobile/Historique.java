package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Historique extends AppCompatActivity {

    private Accelerometer recupScore;
    private TextView myScore;
    private TextView opponentRecord;
    private ArrayList<Integer> listScore = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        myScore = findViewById(R.id.myRecord);
        opponentRecord = findViewById(R.id.opponentRecord);
        int score = recupScore.getScore();

        if(listScore.isEmpty()){
            //la liste est vide
            listScore.add(score);//mon record
            listScore.add(score);//tous records confondus
            myScore.setText(score);
            opponentRecord.setText(score);
        }else if(score > listScore.get(1)){
            //j'ai battu tous les scores confondus
            listScore.set(0,score);//mon record
            listScore.set(1, score);//record global
            myScore.setText(score);
            opponentRecord.setText(score);
        }else if(score <= listScore.get(1) && score >= listScore.get(0)){
            //j'ai battu mon record mais pas tous les records confondus
            listScore.set(0,score);//mon record
        }
    }


}