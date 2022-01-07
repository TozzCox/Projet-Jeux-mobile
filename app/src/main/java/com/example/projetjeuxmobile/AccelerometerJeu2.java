package com.example.projetjeuxmobile;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.Random;

public class AccelerometerJeu2 extends View implements SensorEventListener {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintScore = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap kartBitmap;
    private Bitmap coinBitmap;
    private Bitmap bombBitmap;

    private int kartWidth;
    private int kartHeight;
    private int currentX;
    private int currentY = 1300;
    private int coinWidth;
    private int coinHeight;
    private int coinX;
    private int coinY;
    private Random rdm = new Random();
    private int score = 0;

    private int previousTime;
    private CountDownTimer countDownTimer;
    String time = "0";

    private Historique myScoreHistorique;
    private Historique opponentRecordHistorique;
    private ArrayList<Integer> listScore;

    public AccelerometerJeu2(Context context) {
        super(context);
    }

    public AccelerometerJeu2(Context context, AttributeSet attrSet) {
        super(context, attrSet);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);

        kartBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kisspng_berg_john_deere_pedal_kart_bfr_3_7_21_tract_5bf10d032e8019_3886830315425241631905);
        kartWidth = kartBitmap.getWidth();
        this.currentX = (w - kartWidth)/2;

        coinBitmap = BitmapFactory.decodeResource(getResources(), R.drawable._a1d22de34f053_8887437415118589102168);
        coinWidth = coinBitmap.getWidth();
        coinHeight = coinBitmap.getHeight();
        this.coinX = rdm.nextInt(getWidth()-coinWidth);;
        this.coinY = 100;

        previousTime = 200;
        //lancer le chrono qu'une seule fois
        countDownTimer = new CountDownTimer(20000, 100) {
            @Override
            public void onTick(long l) {
                if(l/1000>=10){
                    time = "00:" + l/1000;
                }else{
                    time = "00:0" + l/1000;
                }

                if(l != previousTime){
                    previousTime = (int) l;
                    movekart(l);
                }
            }

            @Override
            public void onFinish() {
                Toast.makeText(getContext(), "Votre score : " + score, Toast.LENGTH_LONG).show();

                /*if(listScore.isEmpty()){
                    //la liste est vide
                    listScore.add(score);//mon record
                    listScore.add(score);//tous records confondus
                    myScoreHistorique.getMyScoreJeu1Id().setText(score);
                    opponentRecordHistorique.getOpponentScoreJeu1Id().setText(score);
                }else if(score > listScore.get(1)){
                    //j'ai battu tous les scores confondus
                    listScore.set(0, score);//mon record
                    listScore.set(1, score);//record global
                    myScoreHistorique.getMyScoreJeu1Id().setText(score);
                    opponentRecordHistorique.getOpponentScoreJeu1Id().setText(score);
                }else if(score <= listScore.get(1) && score >= listScore.get(0)){
                    //j'ai battu mon record mais pas tous les records confondus
                    listScore.set(0,score);//mon record
                    myScoreHistorique.getMyScoreJeu1Id().setText(score);
                }*/
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(coinBitmap, coinX, coinY, paint);
        canvas.drawBitmap(kartBitmap, currentX, currentY, paint);

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
        //float y = sensorEvent.values[1];
        //float z = sensorEvent.values[2];
        this.movekart(-x*9f);
        Log.d("DEBUG","x :" + x );
    }

    private void movekart(float x){
        this.currentX += (int) x;

        if (currentX<-200){
            currentX = -200;
        }else if (this.currentX + kartWidth - 200 > getWidth() ){
            currentX = getWidth()-kartWidth + 200;
        }


        //Le kart est dans le trou
        //le +29 et +36 correspondent au décalage entre les coordonnées du kart et du trou
        /*if (currentX >= holeX+29-30 && currentX <= holeX+29+30){
            this.holeX = rdm.nextInt(getWidth()-holeWidth);
            this.holeY = rdm.nextInt(getHeight()-holeHeight);
            score+=1;
        }
*/
        //rafraichissement de l'écran
        this.invalidate();
    }
}