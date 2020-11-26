package com.example.memorina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    TilesView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.view);
    }

//    public void onNewGameClick(View v) {
//        view.newGame(); // запустить игру заново
//        // very useful comment
//
//    }
}