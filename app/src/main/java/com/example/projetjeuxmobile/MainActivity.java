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
    ArrayList<String> tactile = new ArrayList<String>();
    ArrayList<String> capteur = new ArrayList<String>();
    public static ArrayList<String> list_game = new ArrayList<String>();


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

                while(!capteur.isEmpty()){
                    capteur.remove(0);
                }
                while(!tactile.isEmpty()){
                    tactile.remove(0);
                }
                while(!list_game.isEmpty()){
                    list_game.remove(0);
                }

                // adding elements
                capteur.add("1");
                capteur.add("2");
                tactile.add("3");
                //capteur.add("4");
                //tactile.add("5"); //Comment envoyer la réponse de l'adversaire en P2P //jeu du morpion
                tactile.add("6");

                String tactile_game = tactile.get(new Random().nextInt(tactile.size()));
                String capteur_game = capteur.get(new Random().nextInt(capteur.size()));

                list_game.add("4"); //quiz
                list_game.add(tactile_game);
                list_game.add(capteur_game);
                Log.d("liste des jeux ",list_game.get(0) + " " + list_game.get(1) + list_game.get(2));

                //Log.d(list_game.get(0), list_game.get(1));
                //Log.d(list_game.get(2), list_game.get(2));

                String current_game = list_game.get(new Random().nextInt(list_game.size()));
                list_game.remove(current_game);

                Class activity = null;


                switch(current_game) {
                    case "1":

                        // E.g., if the output is 1, the activity we will open is ActivityOne.class
                        activity = Game1.class;
                        break;
                    case "2":
                        activity = Game2.class;
                        break;
                    case "3":
                        activity = Game3.class;
                        break;
                    case "4":
                        activity = Jeu4.class;
                        break;
                    case "5":
                        activity = Game5.class;
                        break;
                    case "6":
                        activity = Game6.class;
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
                intent.putExtra("scoreJeu1", getIntent().getIntExtra("scoreJeu1", 0));
                startActivity(intent);
            }
        });
    }
}