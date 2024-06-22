package com.example.tic_tac_toe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {
    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;
    private boolean winningLine = false ;
    private final Paint paint = new Paint();
    private final GameLogic game ;
    private int cellSize = getWidth() / 3 ;


    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        game = new GameLogic();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs , R.styleable.TicTacToeBoard , 0 , 0);
        try{
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor , 0);
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor , 0);
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor , 0);
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor , 0);
        }finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        int dim = Math.min(getMeasuredWidth() , getMeasuredHeight());
        cellSize = dim/3 ;
        setMeasuredDimension(dim , dim);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        drawGameBoard(canvas);
        drawMarkers(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil((y/cellSize));
            int col = (int) Math.ceil((x/cellSize));
            if (!winningLine){
                if (game.updateGameBoard(row , col)){
                    invalidate();
                    // prevents the user to replace the marker
                    if (game.winnerChecker()){
                        winningLine = true ;
                        invalidate();
                    }
                    // updating the players turn
                    //player 2 turn , switch it to player 1
                    if (game.getPlayer()%2 == 0){
                        game.setPlayer(game.getPlayer()-1);
                    }
                    // player 1 turn , switch it to player 2 by + 1
                    else {
                        game.setPlayer(game.getPlayer()+1);
                    }
                }
            }
            invalidate();
            return true ;
        }
        return false ;
    }

    private void drawGameBoard(Canvas canvas){
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);
        for (int c=1 ; c<3 ; c++){
            canvas.drawLine(cellSize*c , 0 , cellSize*c , canvas.getWidth() , paint);
        }
        for (int r=1 ; r<3 ; r++){
            canvas.drawLine( 0 , cellSize*r , canvas.getWidth() , cellSize*r , paint);
        }
    }

    // determine whether to place x or o
    private void drawMarkers(Canvas canvas){
        // define x = 1      o = 2         empty = 0
        for (int i= 0 ; i<3 ; i++){
            for (int j=0 ; j<3 ; j++){
                if(game.getGameBoard()[i][j] != 0){
                    if(game.getGameBoard()[i][j] == 1){
                        drawX(canvas , i , j);
                    }
                    else {
                        drawO(canvas , i , j);
                    }
                }
            }
        }
    }
    private void drawX(Canvas canvas , int row , int col){
        // reduction value is 0.2 away from the borders of the board
        paint.setColor(XColor);
        canvas.drawLine((float) ((col+1)*cellSize - cellSize*0.2), (float) (row*cellSize + cellSize*0.2), (float) (col*cellSize+cellSize*0.2), (float) ((row+1)*cellSize-cellSize*0.2), paint);
        canvas.drawLine((float) (col*cellSize+ cellSize*0.2), (float) (row*cellSize+ cellSize*0.2), (float) ((col+1)*cellSize- cellSize*0.2), (float) ((row+1)*cellSize- cellSize*0.2), paint);
    }
    private void drawO(Canvas canvas , int row , int col){
        // reduction value is 0.2 away from the borders of the board
        paint.setColor(OColor);
        canvas.drawOval((float) (col*cellSize+ cellSize*0.2), (float) (row*cellSize+cellSize*0.2), (float) ((col*cellSize+cellSize)-cellSize*0.2), (float) ((row*cellSize+cellSize)-cellSize*0.2), paint);
    }
    // To give access to the GameLogic class to the view so it can be able to change the attributes
    public void setUpGame(Button playAgain , Button home , TextView playerDisplay , String[] names){
        game.setPlayAgain(playAgain);
        game.setHome(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(names);
    }
    public  void resetGame (){
        game.resetGame();
        // to start a new game , setting winning line to false is necessary because it's always true if not
        winningLine = false ;
    }
}
