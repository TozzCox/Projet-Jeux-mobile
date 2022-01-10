package com.example.projetjeuxmobile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Jeu3 extends AppCompatActivity {

    private ImageView target;
    private ImageView troll1;
    private ImageView troll2;
    private ImageView troll3;
    private ImageView troll4;
    private ImageView troll11;
    private ImageView troll22;
    private ImageView troll33;
    private ImageView troll44;

    private int currentScore;

    private TextView score;
    private TextView timer;

    private Random rdm;
    private CountDownTimer countDownTimer;
    private int delay = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu3);

        score = (TextView) findViewById(R.id.score);

        target = findViewById(R.id.target);
        troll1 = findViewById(R.id.troll1);
        troll2 = findViewById(R.id.troll2);
        troll3 = findViewById(R.id.troll3);
        troll4 = findViewById(R.id.troll4);

        currentScore = 0;
        rdm = new Random();
        timer = findViewById(R.id.timer);

        target.setX(rdm.nextInt(1000));
        target.setY(rdm.nextInt(2000));
        troll1.setVisibility(View.INVISIBLE);
        troll1.setX(rdm.nextInt(1000));
        troll1.setY(rdm.nextInt(2000));
        troll2.setVisibility(View.INVISIBLE);
        troll2.setX(rdm.nextInt(1000));
        troll2.setY(rdm.nextInt(2000));
        troll3.setVisibility(View.INVISIBLE);
        troll3.setX(rdm.nextInt(1000));
        troll3.setY(rdm.nextInt(2000));
        troll4.setVisibility(View.INVISIBLE);
        troll4.setX(rdm.nextInt(1000));
        troll4.setY(rdm.nextInt(2000));

        timer();

        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                target.setVisibility(View.INVISIBLE);
                currentScore+=1;
                score.setText("Score : " + currentScore);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(currentScore == 3){
                            troll1.setVisibility(View.VISIBLE);
                        }else if(currentScore == 5) {
                            troll2.setVisibility(View.VISIBLE);
                        }else if(currentScore == 7) {
                            troll3.setVisibility(View.VISIBLE);
                        }else if(currentScore == 9) {
                            troll4.setVisibility(View.VISIBLE);
                        }

                        target.setX(rdm.nextInt(1000));
                        target.setY(rdm.nextInt(2000));

                        troll1.setX(rdm.nextInt(1000));
                        troll1.setY(rdm.nextInt(2000));

                        troll2.setX(rdm.nextInt(1000));
                        troll2.setY(rdm.nextInt(2000));

                        troll3.setX(rdm.nextInt(1000));
                        troll3.setY(rdm.nextInt(2000));

                        troll4.setX(rdm.nextInt(1000));
                        troll4.setY(rdm.nextInt(2000));

                        target.setVisibility(View.VISIBLE);
                    }
                }, delay);
            }
        });

        troll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentScore-=2;
                score.setText("Score : " + currentScore);
            }
        });

        troll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentScore-=2;
                score.setText("Score : " + currentScore);
            }
        });

        troll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentScore-=2;
                score.setText("Score : " + currentScore);
            }
        });

        troll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentScore-=2;
                score.setText("Score : " + currentScore);
            }
        });
    }

    private void timer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                if(l/1000>=10){
                    timer.setText("00:" + l/1000);
                }else{
                    delay = 300;
                    timer.setText("00:0" + l/1000);
                }
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Votre score : " + currentScore, Toast.LENGTH_LONG).show();
                if (MainActivity.duel ==true){
                    if(P2P.isHost){
                        MainActivity.duelScoreServer += currentScore;
                    }else{
                        MainActivity.duelScoreClient += currentScore;
                    }

                    if (MainActivity.list_game.size()>0){
                        String current_game = MainActivity.list_game.get(new Random().nextInt(MainActivity.list_game.size()));
                        MainActivity.list_game.remove(current_game);


                        Class activity = null;

                        // Here, we are checking to see what the output of the random was
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
                    } else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        }.start();
    }
}