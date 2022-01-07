package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

public class Jeu1 extends AppCompatActivity{

    private SensorManager sensorManager = null;
    private Accelerometer image;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu1);

        image = (Accelerometer) findViewById(R.id.ball);
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

                if (MainActivity.duel == true){
                    Intent intent = new Intent(Jeu1.this, Jeu3.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            }
        }.start();
    }


}