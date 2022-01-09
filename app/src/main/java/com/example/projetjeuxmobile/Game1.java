package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class Game1 extends AppCompatActivity {
    private TextView tvTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        tvTimer = findViewById(R.id.textTimer);
        timer();
    }

    private void timer() {
        CountDownTimer countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                tvTimer.setText(""+ l / 1000);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(Game1.this, Jeu1.class);
                startActivity(intent);
            }
        }.start();
    }
}