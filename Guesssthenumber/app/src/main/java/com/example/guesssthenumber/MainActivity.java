package com.example.guesssthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText first;
    EditText second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
    }

    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, GuessActivity.class);
        int fst = Integer.parseInt(first.getText().toString());
        int sec = Integer.parseInt(second.getText().toString());

        intent.putExtra("first", fst );
        intent.putExtra("second", sec);
        startActivity(intent);
    }
}