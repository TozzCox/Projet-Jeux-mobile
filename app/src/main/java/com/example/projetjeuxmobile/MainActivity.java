package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btnJeu1;
    private Button btnJeu2;
    private Button btnJeu3;
    private Button btnJeu4;
    private Button btnJeu5;
    private Button btnJeu6;
    private Button btnDuel;
    private Button btnHistorique;


    public static Boolean duel = false;
    public static int duelScoreServer;
    public static int duelScoreClient;

    public static Class activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        duelScoreServer = 0;
        duelScoreClient = 0;

        btnJeu1 = (Button) findViewById(R.id.btnJeu1);
        btnJeu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duel = false;
                Intent intent = new Intent(MainActivity.this, Game1.class);
                startActivity(intent);
            }
        });

        btnJeu2 = findViewById(R.id.btnJeu2);
        btnJeu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duel = false;
                Intent intent = new Intent(MainActivity.this, Game2.class);
                startActivity(intent);
            }
        });

        btnJeu3 = findViewById(R.id.btnJeu3);
        btnJeu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duel = false;
                Intent intent = new Intent(MainActivity.this, Game3.class);
                startActivity(intent);
            }
        });

        btnJeu4 = findViewById(R.id.btnJeu4);
        btnJeu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duel = false;
                Intent intent = new Intent(MainActivity.this, Jeu4.class);
                startActivity(intent);
            }
        });

        btnJeu5 = findViewById(R.id.btnJeu5);
        btnJeu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duel = false;
                Intent intent = new Intent(MainActivity.this, Game5.class);
                startActivity(intent);
            }
        });

        btnJeu6 = findViewById(R.id.btnJeu6);
        btnJeu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duel = false;
                Intent intent = new Intent(MainActivity.this, Game6.class);
                startActivity(intent);
            }
        });



        btnDuel = findViewById(R.id.btnDuel);
        btnDuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duel = true;
                // We use intents to start activities
                //getBaseContext() recupère le contexte de l'activité où l'on est
                Intent intent = new Intent(getBaseContext(), P2P.class);
                startActivity(intent);
            }
        });

        btnHistorique = findViewById(R.id.btnHistorique);
        btnHistorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Historique.class);
                intent.putExtra("scoreJeu1", getIntent().getIntExtra("scoreJeu1", 0));
                startActivity(intent);
            }
        });
    }
}