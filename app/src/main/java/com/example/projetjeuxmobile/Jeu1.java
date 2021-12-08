package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Jeu1 extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager = null;
    private Accelerometer image;
    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu1);

        image = (Accelerometer) findViewById(R.id.ball);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        score = findViewById(R.id.scoreText);
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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        score.setText(image.getScore());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }

}