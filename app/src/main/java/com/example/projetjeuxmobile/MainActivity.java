package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnJeu1;
    private Button btnJeu3;
    private Button btnJeu4;
    private Button btnJeu5;
    private Button btnDuel;
    private Button btnHistorique;


    public static Boolean duel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnJeu1 = (Button) findViewById(R.id.btnJeu1);
        btnJeu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duel = false;
                Intent intent = new Intent(MainActivity.this, Jeu1.class);
                startActivity(intent);
            }
        });

        btnJeu3 = findViewById(R.id.btnJeu3);
        btnJeu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duel = false;
                Intent intent = new Intent(MainActivity.this, Jeu3.class);
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
                Intent intent = new Intent(MainActivity.this, Jeu5.class);
                startActivity(intent);
            }
        });

        btnDuel = findViewById(R.id.btnDuel);
        btnDuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duel = true;
                Intent intent = new Intent(MainActivity.this, Jeu1.class);
                startActivity(intent);
            }
        });

        btnHistorique = findViewById(R.id.btnHistorique);
        btnHistorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Historique.class);
                startActivity(intent);
            }
        });
    }
}