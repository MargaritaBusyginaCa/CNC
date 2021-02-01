package com.example.cnc;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPageActivity extends AppCompatActivity {
    Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        bt_login=findViewById(R.id.login_bt);
        bt_login.setOnClickListener(click->{
            Intent intent=new Intent(this, AccountActivity.class);
            startActivity(intent);
        });


        // Enables Always-on

    }
}