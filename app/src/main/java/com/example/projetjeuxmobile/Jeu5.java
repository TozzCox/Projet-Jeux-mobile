package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Jeu5 extends AppCompatActivity implements View.OnClickListener {


    private TextView playerOneScore, playerTwoScore, playerStaus;
    private Button[] buttons = new Button[9];
    private Button resetGame;

    private int playerOneScoreCount, playerTwoScoreCount, rountCount;
    boolean activePlayer;

    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions ={
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu5);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStaus = (TextView) findViewById(R.id.playerStatus);

        resetGame = (Button)  findViewById(R.id.reserGame);
        for (int i=0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int ressourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(ressourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View view) {
        if(!((Button)view).getText().toString().equals("")){
            return;
        }
        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length())); //2

        if (activePlayer){
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        }else{
            ((Button)view).setText("0");
            ((Button)view).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        rountCount++;


        if (checkWinner()){
            if(activePlayer){
                playerOneScoreCount++;
                updatPlayerScore();
                Toast.makeText(this, "player One Won", Toast.LENGTH_SHORT).show();
                playAgain();
            }
            else{
                playerTwoScoreCount++;
                updatPlayerScore();
                Toast.makeText(this, "player Two Won", Toast.LENGTH_SHORT).show();
                playAgain();
            }

        }else if(rountCount ==9){
            playAgain();
            Toast.makeText(this, "No winner", Toast.LENGTH_SHORT).show();
        }else{
            activePlayer = !activePlayer;
        }
        if(playerOneScoreCount > playerTwoScoreCount){
            playerStaus.setText("player One is Winning");
        }else if(playerOneScoreCount < playerTwoScoreCount){
            playerStaus.setText("player Two is Winning");
        } else {
            playerStaus.setText("");
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount =0;
                playerOneScoreCount =0;
                playerStaus.setText("");
                updatPlayerScore();
            }
        });
    }

    public boolean checkWinner(){
        boolean winnerResult = false;

        for(int [] winningPosition : winningPositions){
            if(gameState[winningPosition[0]]==gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]]==gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]]!=2){
                winnerResult = true;
            }
        }
        return winnerResult;
    }
    public void updatPlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain(){
        rountCount = 0;
        activePlayer = true;

        for(int i = 0; i<buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");

        }
    }
}