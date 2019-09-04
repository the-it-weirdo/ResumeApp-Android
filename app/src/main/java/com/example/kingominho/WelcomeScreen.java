package com.example.kingominho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeScreen extends AppCompatActivity {

    TypeWriter typeWriter;
    int delay = 2400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        typeWriter = (TypeWriter) findViewById(R.id.typeWriter);

        typeWriter.setText("");
        typeWriter.setCharecterDelay(200);
        typeWriter.animateText("WELCOME");

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }, delay);

    }
}
