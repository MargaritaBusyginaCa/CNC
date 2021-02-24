package com.example.cnc.main;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.cnc.loginPage.LoginActivity;

import com.example.cnc.R;
import com.example.cnc.loginPage.RegisterActivity;

public class EntranceActivity extends AppCompatActivity {
    /*Button get_started;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        get_started=findViewById(R.id.GetStarted_Bt);
        get_started.setOnClickListener(click->{
            Intent intent=new Intent(this, LoginActivity.class);
      //      Intent intent=new Intent(this, OrientationActivity.class);
            startActivity(intent);
        });
*/
    /* adjusted by NyNguyen Feb 15, 2021*/
    Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        // for Login Button to navigate to LoginActivity
        login = findViewById(R.id.loginButton);
        login.setOnClickListener(click -> {
            Intent intentLogin = new Intent(this, LoginActivity.class);
            startActivity(intentLogin);
        });

        // for Register Button to navigate to RegisterActivity
        register = findViewById(R.id.registerButton);
        register.setOnClickListener(e -> {
            Intent intentRegister = new Intent(this, RegisterActivity.class);
            startActivity(intentRegister);
        });



    }
}