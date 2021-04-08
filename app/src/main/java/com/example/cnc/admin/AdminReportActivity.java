package com.example.cnc.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.supporters.User;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.cnc.loginPage.LoginActivity.studentID_def;


public class AdminReportActivity extends AppCompatActivity{
    private final AppCompatActivity activity = AdminReportActivity.this;
    Button bt_exit;
    String ori, a1, a2;
    DatabaseHelper databaseHelper;
    List<User> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_report);

        Intent intent = getIntent();
        ori = intent.getStringExtra("ORI");
        a1 = intent.getStringExtra("A1");
        a2 = intent.getStringExtra("A2");

        init();
    }

    private void init() {

        int count = getCount();
        TextView typedCount = findViewById(R.id.totalNumber);
        typedCount.setText(String.valueOf(count));
        TextView typedOri = findViewById(R.id.ori_timestamp);
        typedOri.setText(ori);
        TextView typeda1 = findViewById(R.id.a1_timestamp);
        typeda1.setText(a1);
        TextView typeda2 = findViewById(R.id.a2_timestamp);
        typeda2.setText(a1);

        //--- Exit ---
        bt_exit = findViewById(R.id.exit);
        bt_exit.setOnClickListener(click->{
            Intent intent=new Intent(this, AdminActivity.class);
            startActivity(intent);
        });
    }

    //total students
    private int getCount() {
        databaseHelper = new DatabaseHelper(this);
        users = databaseHelper.getAllUser();
        return (users.size()-3);
    }


}
