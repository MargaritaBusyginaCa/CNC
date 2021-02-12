package com.example.cnc.main;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
//import com.example.cnc.orientation.OrientationActivity;
import com.example.cnc.loginPage.LoginPageActivity;

import com.example.cnc.R;

public class MainActivity extends AppCompatActivity {
    Button get_started;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        get_started=findViewById(R.id.GetStarted_Bt);
        get_started.setOnClickListener(click->{
            Intent intent=new Intent(this, LoginPageActivity.class);
      //      Intent intent=new Intent(this, OrientationActivity.class);
            startActivity(intent);
        });



    }
}