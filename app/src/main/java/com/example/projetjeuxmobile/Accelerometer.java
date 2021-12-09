package com.example.projetjeuxmobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class Accelerometer extends View implements SensorEventListener {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintScore = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap ballBitmap;
    private Bitmap holeBitmap;

    private int ballWidth;
    private int ballHeight;
    private int currentX;
    private int currentY;
    private int holeWidth;
    private int holeHeight;
    private int holeX;
    private int holeY;
    private Random rdm = new Random();
    private int score = 0;

    private CountDownTimer countDownTimer;
    String time = "0";

    public Accelerometer(Context context) {
        super(context);
    }

    public Accelerometer(Context context, AttributeSet attrSet) {
        super(context, attrSet);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);

        ballBitmap = BitmapFactory.decodeResource(getResources(), R.drawable._a1cae851413e8_1843016715118291250823);
        ballWidth = ballBitmap.getWidth();
        ballHeight = ballBitmap.getHeight();

        this.currentX = (w - ballWidth)/2;
        this.currentY = (h - ballHeight)/2;

        holeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kisspng_blue_circle_wallpaper_black_hole_png_photos_5a77eb2089df21_0786027015178084165647);
        holeHeight = holeBitmap.getHeight();
        holeWidth = holeBitmap.getWidth();

        this.holeX = rdm.nextInt(getWidth()-holeWidth); //génère un nombre aléatoire entre 0 et 1080
        this.holeY = rdm.nextInt(getHeight()-holeHeight); //génère un nombre aléatoire entre 0 et 2340

        //lancer le chrono qu'une seule fois
        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                if(l/1000>=10){
                    time = "00:" + l/1000;
                }else{
                    time = "00:0" + l/1000;
                }
            }

            @Override
            public void onFinish() {
                Toast.makeText(getContext(), "Votre score : " + score, Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(ballBitmap, currentX, currentY, paint);
        canvas.drawBitmap(holeBitmap, holeX, holeY, paint);

        paintScore.setTextSize(70);
        paintScore.setARGB(255, 127, 0, 255);
        canvas.drawText("Score : "+ score, 700, 120, paintScore);
        canvas.drawText(time, 300, 120, paintScore);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        //float z = sensorEvent.values[2];
        this.moveBall(-x*4f, y*4f);
        Log.d("DEBUG","x :" + x + " y :" + y);
    }

    private void moveBall(float x, float y){
        this.currentX += (int) x;
        this.currentY += (int) y;

        if (currentX<0){
            currentX = 0;
        }else if (this.currentX + ballWidth > getWidth() ){
            currentX = getWidth()-ballWidth;
        }

        if (currentY<0){
            currentY = 0;
        }else if (this.currentY + ballHeight > getHeight() ) {
            currentY = getHeight() - ballHeight;
        }

        //La balle est dans le trou
        //le +29 et +36 correspondent au décalage entre les coordonnées de la balle et du trou
        if (currentX >= holeX+29-30 && currentX <= holeX+29+30 && currentY <= holeY+36+30 && currentY >= holeY+36-30){
            this.holeX = rdm.nextInt(getWidth()-holeWidth);
            this.holeY = rdm.nextInt(getHeight()-holeHeight);
            score+=1;
        }

        //rafraichissement de l'écran
        this.invalidate();
    }

    public int getScore(){
        return score;
    }

}