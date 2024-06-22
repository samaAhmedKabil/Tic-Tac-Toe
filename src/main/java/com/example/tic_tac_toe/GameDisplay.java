package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tic_tac_toe.databinding.GameDisplayBinding;

import java.util.Arrays;

public class GameDisplay extends AppCompatActivity {
    GameDisplayBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = GameDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.playAgainBtn.setVisibility(View.GONE);
        binding.homeBtn.setVisibility(View.GONE);
        String[] playerNames = getIntent().getStringArrayExtra("playerNames");
        if (playerNames != null){
            binding.playerTurn.setText(playerNames[0] + "'s Turn");
        }
        homeClick();
        playAgainClick();
        binding.ticTacToeBoard.setUpGame(binding.playAgainBtn , binding.homeBtn , binding.playerTurn , playerNames);
    }
    public void playAgainClick(){
        binding.playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ticTacToeBoard.resetGame();
                binding.ticTacToeBoard.invalidate();
            }
        });
    }
    public void homeClick(){
        binding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameDisplay.this , MainActivity.class));
            }
        });
    }
}