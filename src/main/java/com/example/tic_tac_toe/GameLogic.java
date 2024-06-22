package com.example.tic_tac_toe;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic {
    private int [][] gameBoard ;

    // put static values bec. these values can't be changed ever even by the user
    private String[] playerNames = {"Player 1" , "Player 2"};
    private Button playAgain ;
    private Button home ;
    private TextView playerTurn ;

    // player 1 will always go first (starts the game)
    private int player = 1 ;
    GameLogic(){
        gameBoard = new int [3][3] ;
        for (int i= 0 ; i<3 ; i++){
            for (int j=0 ; j<3 ; j++){
                gameBoard [i][j] = 0 ;
            }
        }
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }
    public void setPlayAgain(Button playAgain) {
        this.playAgain = playAgain;
    }
    public void setHome(Button home) {
        this.home = home;
    }
    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }
    public int[][] getGameBoard() {
        return gameBoard;
    }
    public boolean updateGameBoard(int row , int col){
        // row-1 and col-1 bec. 2D array starts from index 0
        // row-1 and col-1 is the place the user clicked
        if (gameBoard[row-1][col-1] == 0){
            gameBoard[row-1][col-1] = player ;
            if (player == 1){
                playerTurn.setText((playerNames[1]+"'s Turn"));
            }
            else {
                playerTurn.setText((playerNames[0]+"'s Turn"));
            }
            return true ;
        }
        else {
            return false ;
        }
    }

    // To determine if we have a winner or not
    public boolean winnerChecker(){
        boolean isWinner = false ;
        // winner with row
        for (int i=0 ; i<3 ; i++){
            if (gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][0] == gameBoard[i][2] && gameBoard[i][0]!=0){
                isWinner = true ;
            }
        }
        // winner with col
        for (int j=0 ; j<3 ; j++){
            if (gameBoard[0][j] == gameBoard[1][j] && gameBoard[2][j] == gameBoard[0][j] && gameBoard[0][j] != 0){
                isWinner = true ;
            }
        }
        //winner with negative slopping diagonal
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard[0][0] != 0){
            isWinner = true ;
        }
        // winner with positive slopping diagonal
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] && gameBoard[2][0] != 0){
            isWinner = true ;
        }

        int boardFilled = 0 ;
        for (int i= 0 ; i<3 ; i++){
            for (int j=0 ; j<3 ; j++){
                if(gameBoard [i][j] != 0){
                    // increment by one every time it finds a non zero value
                    boardFilled += 1 ;
                }
            }
        }
        if(isWinner){
            playAgain.setVisibility(View.VISIBLE);
            home.setVisibility(View.VISIBLE);
            playerTurn.setText((playerNames[player-1]+ " Won!!!!!!!!!!!!!"));
            return true ;
        }
        else if (boardFilled == 9){
            playAgain.setVisibility(View.VISIBLE);
            home.setVisibility(View.VISIBLE);
            playerTurn.setText("Tie Game !!!!!!!!!!!");
            return true ;
        }
        else {
            return false ;
        }
    }
    public void resetGame (){
        for (int i= 0 ; i<3 ; i++){
            for (int j=0 ; j<3 ; j++){
                gameBoard [i][j] = 0 ;
            }
        }
        player = 1 ;
        playAgain.setVisibility(View.GONE);
        home.setVisibility(View.GONE);
        playerTurn.setText((playerNames[0] + "'s Turn"));
    }
}
