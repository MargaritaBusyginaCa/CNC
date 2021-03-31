package com.example.cnc.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.loginPage.AccountActivity;
import com.example.cnc.loginPage.RegisterActivity;
import com.example.cnc.loginPage.VerificationActivity;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.supporters.InputValidation;
import com.example.cnc.supporters.User;
import com.google.android.material.snackbar.Snackbar;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class AdminActivity extends AppCompatActivity{
    private final AppCompatActivity activity = AdminActivity.this;
    Button bt_changeEmail, bt_update, bt_cancel;
    String email;

    private DatabaseHelper databaseHelper;

    public static String studentID_def;
    public List<User> users = new ArrayList<>();
    String email_def;
    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        init();
    }

    private void init() {

        bt_changeEmail=findViewById(R.id.admin_changeEmail_bt);
        bt_changeEmail.setOnClickListener(click->{
       //    email =  getEmail(000000);

                });

    }

}
