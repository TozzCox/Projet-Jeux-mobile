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
    public static int duelScore = 0;
    ArrayList<String> tactile = new ArrayList<String>();
    ArrayList<String> capteur = new ArrayList<String>();
    public static ArrayList<String> list_game = new ArrayList<String>();


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

        btnJeu2 = findViewById(R.id.btnJeu2);
        btnJeu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Jeu2.class);
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

        btnJeu6 = findViewById(R.id.btnJeu6);
        btnJeu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duel = false;
                Intent intent = new Intent(MainActivity.this, Jeu6.class);
                startActivity(intent);
            }
        });



        btnDuel = findViewById(R.id.btnDuel);
        btnDuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duel = true;

                // adding elements
                //tactile.add("2");
                tactile.add("3");

                capteur.add("4");
                //capteur.add("5");
                //capteur.add("6");

                String tactile_game = tactile.get(new Random().nextInt(tactile.size()));
                String capteur_game = capteur.get(new Random().nextInt(capteur.size()));

                list_game.add("1");
                list_game.add(tactile_game);
                list_game.add(capteur_game);
                Log.d(list_game.get(0), list_game.get(1));
                Log.d(list_game.get(2), list_game.get(2));


                String current_game = list_game.get(new Random().nextInt(list_game.size()));
                list_game.remove(current_game);

                Class activity = null;


                switch(current_game) {
                    case "1":

                        // E.g., if the output is 1, the activity we will open is ActivityOne.class
                        activity = Jeu1.class;
                        break;
                    case "2":
                        activity = Jeu2.class;
                        break;
                    case "3":
                        activity = Jeu3.class;
                        break;
                    case "4":
                        activity = Jeu4.class;
                        break;
                    case "5":
                        activity = Jeu5.class;
                        break;
                    case "6":
                        activity = Jeu6.class;
                        break;
                    default:
                        activity = MainActivity.class;
                        break;
                }
                // We use intents to start activities
                Intent intent = new Intent(getBaseContext(), activity);
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