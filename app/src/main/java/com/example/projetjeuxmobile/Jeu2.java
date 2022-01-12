package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

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
                    if(P2P.isHost) {
                        MainActivity.duelScoreServer += image.getScore();
                    }else {
                        MainActivity.duelScoreClient += image.getScore();
                    }

                    if (P2P.list_game.size()>0){

                        for(int i = 0; i<3; i++){
                            Log.d("list_game" , ""+ P2P.list_game.get(i));
                        }

                        // We use intents to start activities
                        Intent intentActivity = new Intent(getBaseContext(), P2P.launchActivity());
                        startActivity(intentActivity);

                    }else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                }else {
                    Intent intent = new Intent(Jeu2.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }.start();
    }
}