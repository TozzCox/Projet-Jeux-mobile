package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvScore, tvQuestionNo, tvTimer;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3;
    private Button btnNext;

    int totalQuestions;
    int qCounter = 0;
    int score;

    ColorStateList dfRbColor;
    boolean answered;

    CountDownTimer countDownTimer;

    private QuestionModel currentQuestion;

    private List<QuestionModel> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionList = new ArrayList<>();
        tvQuestion = findViewById(R.id.textQuestion);
        tvScore = findViewById(R.id.texScore);
        tvQuestionNo = findViewById(R.id.textQuestionNo);
        tvTimer = findViewById(R.id.textTimer);

        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        btnNext = findViewById(R.id.btnNext);

        dfRbColor = rb1.getTextColors();

        addQuestions();
        totalQuestions = questionList.size();
        showNextQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answered == false){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked()){
                        checkAnswer();
                        countDownTimer.cancel();
                    } else {
                        Toast.makeText(QuizActivity.this, "Please Select an option", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });

    }
    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected) + 1;
        if (answerNo == currentQuestion.getCorrectAnsNo()){
            score ++;
            tvScore.setText("Score: "+score);
        }
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        switch (currentQuestion.getCorrectAnsNo()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
        }
        if (qCounter < totalQuestions){
            btnNext.setText("Next");
        }else {
            btnNext.setText("Finish");
        }

    }

    private void showNextQuestion() {

        radioGroup.clearCheck();
        rb1.setTextColor(dfRbColor);
        rb2.setTextColor(dfRbColor);
        rb3.setTextColor(dfRbColor);

        if(qCounter < totalQuestions){
            timer();
            currentQuestion = questionList.get(qCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            qCounter++;
            btnNext.setText("Submit");
            tvQuestionNo.setText("Question: "+qCounter+"/"+totalQuestions);
            answered = false;
        }else {
            //finish();
            if (MainActivity.duel ==true){

                if(P2P.isHost){
                    MainActivity.duelScoreServer += score;
                }else{
                    MainActivity.duelScoreClient += score;
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
                            activity = Game4.class;
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
                finish();
            }
        }
    }

    private void timer() {
        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                tvTimer.setText("00:"+l/1000);
            }

            @Override
            public void onFinish() {
                showNextQuestion();
            }
        }.start();
    }

    private void addQuestions() {
        questionList.add(new QuestionModel("A is correct?", "A", "B", "C", 1));
        questionList.add(new QuestionModel("B is correct?", "A", "B", "C", 2));
        questionList.add(new QuestionModel("C is correct?", "A", "B", "C", 3));
        questionList.add(new QuestionModel("A is correct?", "A", "B", "C", 1));
        questionList.add(new QuestionModel("B is correct?", "A", "B", "C", 2));

    }
}