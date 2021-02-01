package com.example.cnc;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button get_started;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        get_started=findViewById(R.id.GetStarted_Bt);
        get_started.setOnClickListener(click->{
            Intent intent=new Intent(this,LoginPageActivity.class);
            startActivity(intent);
        });

    }
}