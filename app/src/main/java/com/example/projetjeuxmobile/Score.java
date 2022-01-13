package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Score extends AppCompatActivity {

    //TextView playerOneScore, playerTwoScrore;
    Button menu;

    public static boolean finalJoueur1 = false;
    public static boolean finalJoueur2 = false;

    public static TextView scoreJoueur1;
    public static TextView scoreJoueur2;

    @SuppressLint({"CutPasteId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoreJoueur1 = findViewById(R.id.scoreJoueur1);
        scoreJoueur2 = findViewById(R.id.scoreJoueur2);

        scoreJoueur1.setText(MainActivity.duelScoreServer);
        scoreJoueur2.setText(MainActivity.duelScoreClient);

        menu = (Button) findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.duel = false;
                Intent intent = new Intent(Score.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}