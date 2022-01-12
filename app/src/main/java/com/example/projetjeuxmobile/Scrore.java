package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Scrore extends AppCompatActivity {
    private TextView playerOneScore, playerTwoScrore;
    public Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrore);

        playerOneScore = (TextView) findViewById(R.id.Joueur1);
        playerTwoScrore = (TextView) findViewById(R.id.Joueur2);
        menu = (Button) findViewById(R.id.menu);

        

        playerOneScore.setText("Joueur 1 : " + MainActivity.duelScoreServer );
        playerTwoScrore.setText("Joueur 2 : " + MainActivity.duelScoreClient);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.duel = false;
                Intent intent = new Intent(Scrore.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}