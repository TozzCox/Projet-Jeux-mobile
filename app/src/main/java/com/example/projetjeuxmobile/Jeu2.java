package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;

import java.util.Random;

public class Jeu2 extends AppCompatActivity {

    private SensorManager sensorManager = null;
    private AccelerometerJeu2 image;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu2);

        image = (AccelerometerJeu2) findViewById(R.id.kart);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        timer();
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(image, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(image);
    }

    private void timer() {
        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {}

            @Override
            public void onFinish() {
                if(MainActivity.duel){
                    if(P2P.isHost){
                        MainActivity.duelScoreServer += image.getScore();
                    }else{
                        MainActivity.duelScoreClient += image.getScore();
                    }
                }

                if (P2P.list_game.size()>0){
                    String current_game = P2P.list_game.get(0);
                    P2P.list_game.remove(current_game);



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
            }
        }.start();
    }


}