package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tic_tac_toe.databinding.PlayerNameBinding;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PlayerName extends AppCompatActivity {
    PlayerNameBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PlayerNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        submitNamesClick();
    }
    public void submitNamesClick(){

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player1Name = binding.editPlayer1.getText().toString().trim();
                String player2Name = binding.editPlayer2.getText().toString().trim();
                Intent intent = new Intent(PlayerName.this , GameDisplay.class);
                intent.putExtra("playerNames" , new String[] {player1Name , player2Name});
                startActivity(intent);
            }
        });
    }
}