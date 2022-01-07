package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class Jeu6 extends AppCompatActivity {

    RelativeLayout relativeLayout;
    ImageView arrow;
    TextView scoreDisplay;
    SwipeListener swipeListener;
    int score;
    int value = 0;
    Random rdm = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu6);

        relativeLayout = findViewById(R.id.drawArea);
        arrow = findViewById(R.id.arrow);
        score = 0;
        scoreDisplay = (TextView) findViewById(R.id.scoreJeu6);

        swipeListener = new SwipeListener(relativeLayout);
    }

    public class SwipeListener implements View.OnTouchListener{
        GestureDetector gestureDetector;
        SwipeListener(View view){
            int threshold = 100;
            int velocity_threshold = 100;

            GestureDetector.SimpleOnGestureListener listener =
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onDown(MotionEvent e){
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
                        float xDiff = e2.getX() - e1.getX();
                        float yDiff = e2.getY() - e1.getY();

                        try{
                            if(Math.abs(xDiff)>Math.abs(yDiff)){
                                if(Math.abs(xDiff) > threshold && Math.abs(velocityX) > velocity_threshold){
                                    if(xDiff>0){
                                        //swipe right
                                        if(value==3){
                                            score -= 1;
                                        }else{
                                            score+=1;
                                        }

                                    }else{
                                        //swipe left
                                        if(value==1){
                                            score-=1;
                                        }else{
                                            score+=1;
                                        }
                                    }
                                    value = rdm.nextInt(4);
                                    arrow.setRotation(value*90);
                                    scoreDisplay.setText("Score : " + score);
                                    if(score==20){
                                        Intent intent = new Intent(Jeu6.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    return true;
                                }
                            }else{
                                if(Math.abs(yDiff) > threshold && Math.abs(velocityY) > velocity_threshold){
                                    if (yDiff > 0){
                                        //swipe down
                                        if(value==0){
                                            score-=1;
                                        }else{
                                            score+=1;
                                        }
                                    }else{
                                        //swipe up
                                        if(value==2){
                                            score-=1;
                                        }else{
                                            score+=1;
                                        }
                                    }
                                    value = rdm.nextInt(4);
                                    arrow.setRotation(value*90);
                                    scoreDisplay.setText("Score : " + score);
                                    if(score==20){
                                        Intent intent = new Intent(Jeu6.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    return true;
                                }
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        return false;
                    }
                };

            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }
    }
}