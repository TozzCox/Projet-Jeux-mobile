package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.LauncherActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Score1 extends AppCompatActivity {

    Button menu;
    Button actualiser;

    public static boolean finalJoueur1 = false;
    public static boolean finalJoueur2 = false;

    public static TextView scoreJoueur1;
    public static TextView scoreJoueur2;
    private MediaPlayer mediaPlayerWinner;
    private MediaPlayer mediaPlayerLoser;

    public ImageView winner;
    public ImageView loser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score1);

        winner = (ImageView) findViewById(R.id.winner);
        loser = (ImageView) findViewById(R.id.loser);

        this.mediaPlayerWinner = MediaPlayer.create(getApplicationContext(), R.raw.winner1);
        this.mediaPlayerLoser = MediaPlayer.create(getApplicationContext(), R.raw.loser2);

        scoreJoueur1 = findViewById(R.id.scoreJoueur1);
        scoreJoueur2 = findViewById(R.id.scoreJoueur2);


        menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MainActivity.duel = false;
                Intent intent = new Intent(Score1.this, MainActivity.class);
                startActivity(intent);
            }
        });

        actualiser = findViewById(R.id.actualiser);
        actualiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if(!P2P.isHost) {
                            String scoreClient = "termine:" + MainActivity.duelScoreClient;
                            P2P.clientClass.write(scoreClient.getBytes());
                        }
                    }
                });
                if (P2P.isHost){
                    if(MainActivity.duelScoreClient!=0){
                        scoreJoueur1.setText(""+MainActivity.duelScoreServer);
                        scoreJoueur2.setText(""+MainActivity.duelScoreClient);
                        if(P2P.isHost && MainActivity.duelScoreServer > MainActivity.duelScoreClient){
                            playSoundWinner();
                            winner.setVisibility(View.VISIBLE);
                            loser.setVisibility(View.INVISIBLE);
                        }
                        else if(P2P.isHost && MainActivity.duelScoreServer < MainActivity.duelScoreClient){
                            playSoundLoser();
                            loser.setVisibility(View.VISIBLE);
                            winner.setVisibility(View.INVISIBLE);
                        }
                        else if(!P2P.isHost && MainActivity.duelScoreServer < MainActivity.duelScoreClient){
                            playSoundWinner();
                            winner.setVisibility(View.VISIBLE);
                            loser.setVisibility(View.INVISIBLE);
                        }
                        else if(!P2P.isHost && MainActivity.duelScoreServer > MainActivity.duelScoreClient){
                            playSoundLoser();
                            loser.setVisibility(View.VISIBLE);
                            winner.setVisibility(View.INVISIBLE);
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Veuillez attendre que votre adversaire termine", Toast.LENGTH_SHORT).show();
                    }
                }else if(!P2P.isHost){
                    if(MainActivity.duelScoreServer!=0){
                        scoreJoueur1.setText(""+MainActivity.duelScoreServer);
                        scoreJoueur2.setText(""+MainActivity.duelScoreClient);
                        if(P2P.isHost && MainActivity.duelScoreServer > MainActivity.duelScoreClient){
                            playSoundWinner();
                            winner.setVisibility(View.VISIBLE);
                            loser.setVisibility(View.INVISIBLE);
                        }
                        else if(P2P.isHost && MainActivity.duelScoreServer < MainActivity.duelScoreClient){
                            playSoundLoser();
                            loser.setVisibility(View.VISIBLE);
                            winner.setVisibility(View.INVISIBLE);
                        }
                        else if(!P2P.isHost && MainActivity.duelScoreServer < MainActivity.duelScoreClient){
                            playSoundWinner();
                            winner.setVisibility(View.VISIBLE);
                            loser.setVisibility(View.INVISIBLE);
                        }
                        else if(!P2P.isHost && MainActivity.duelScoreServer > MainActivity.duelScoreClient){
                            playSoundLoser();
                            loser.setVisibility(View.VISIBLE);
                            winner.setVisibility(View.INVISIBLE);
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Veuillez attendre que votre adversaire termine", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(P2P.isHost) {
                    String scoreServer = "termine:" + MainActivity.duelScoreServer;
                    P2P.serverClass.write(scoreServer.getBytes());
                }/*else{
                    String scoreClient = "termine:" + MainActivity.duelScoreClient;
                    P2P.clientClass.write(scoreClient.getBytes());
                }*/
            }
        });
    }
    public void playSoundWinner(){
        mediaPlayerWinner.start();
    }
    public void playSoundLoser(){
        mediaPlayerLoser.start();
    }
}
