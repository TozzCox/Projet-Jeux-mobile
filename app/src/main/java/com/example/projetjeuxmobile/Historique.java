package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Historique extends AppCompatActivity {

    TextView scoreJeu1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        scoreJeu1 = findViewById(R.id.myRecordJeu1);
        int score = getIntent().getIntExtra("scoreJeu1", 0);
        scoreJeu1.setText(score+"");
    }

    /*//Get my record
    public TextView getMyScoreJeu1Id(){
        return findViewById(R.id.myRecordJeu1);
    }
    public TextView getMyScoreJeu2Id(){
        return findViewById(R.id.myRecordJeu2);
    }
    public TextView getMyScoreJeu3Id(){
        return findViewById(R.id.myRecordJeu3);
    }
    public TextView getMyScoreJeu4Id(){
        return findViewById(R.id.myRecordJeu4);
    }
    public TextView getMyScoreJeu5Id(){
        return findViewById(R.id.myRecordJeu5);
    }
    public TextView getMyScoreJeu6Id(){
        return findViewById(R.id.myRecordJeu6);
    }

    //Get opponents record
    public TextView getOpponentScoreJeu1Id(){
        return findViewById(R.id.opponentRecordJeu1);
    }
    public TextView getOpponentScoreJeu2Id(){
        return findViewById(R.id.opponentRecordJeu2);
    }
    public TextView getOpponentScoreJeu3Id(){
        return findViewById(R.id.opponentRecordJeu3);
    }
    public TextView getOpponentScoreJeu4Id(){
        return findViewById(R.id.opponentRecordJeu4);
    }
    public TextView getOpponentScoreJeu5Id(){
        return findViewById(R.id.opponentRecordJeu5);
    }
    public TextView getOpponentScoreJeu6Id(){
        return findViewById(R.id.opponentRecordJeu6);
    }
*/
}