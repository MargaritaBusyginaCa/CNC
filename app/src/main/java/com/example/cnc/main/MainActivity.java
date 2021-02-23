package com.example.cnc.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;



/**
 * Created by NyNguyen on Feb 15, 2021
 */
public class MainActivity extends AppCompatActivity {
    Button getStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        // for Login Button to navigate to LoginPage
        getStart = findViewById(R.id.GetStarted_Bt);
        getStart.setOnClickListener(click -> {
            Intent intentGetStart = new Intent(this, EntranceActivity.class);
            startActivity(intentGetStart);
        });


    }



}
