package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

                        if (P2P.list_game.size()>0){
                            Log.d("scoreServer" , ""+ MainActivity.duelScoreServer);
                            Log.d("scoreClient" , ""+ MainActivity.duelScoreClient);

                            // We use intents to start activities
                            Intent intentActivity = new Intent(getBaseContext(), P2P.launchActivity());
                            startActivity(intentActivity);

                        }else {
                            Intent intent = new Intent(getBaseContext(), Score.class);
                            startActivity(intent);
                        }

                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        Handler handler = new Handler(Looper.getMainLooper());

                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                String scoreServer = "termine:"+MainActivity.duelScoreServer;
                                P2P.serverClass.write(scoreServer.getBytes());
                            }});

                    }else {
                        MainActivity.duelScoreClient += image.getScore();
                        if (P2P.list_game.size()>0){
                            Log.d("list_game_size" , ""+ P2P.list_game.size());

                            // We use intents to start activities
                            Intent intentActivity = new Intent(getBaseContext(), P2P.launchActivity());
                            startActivity(intentActivity);

                        }else {
                            Intent intent = new Intent(getBaseContext(), Score.class);
                            startActivity(intent);
                        }

                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        Handler handler = new Handler(Looper.getMainLooper());

                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                String scoreClient = "termine:"+MainActivity.duelScoreClient;
                                P2P.clientClass.write(scoreClient.getBytes());
                            }});
                    }



                }else {
                    Intent intent = new Intent(Jeu2.this, Score.class);
                    startActivity(intent);
                }
            }
        }.start();
    }
}