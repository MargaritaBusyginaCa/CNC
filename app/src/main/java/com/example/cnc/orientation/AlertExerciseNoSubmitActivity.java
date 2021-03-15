package com.example.cnc.orientation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.loginPage.AccountActivity;

public class AlertExerciseNoSubmitActivity extends AppCompatActivity {
    Button exit_btn, cancel_btn;
    String studentID;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ori_alert_exercise_no_submit);

        Intent intentAssmnt = getIntent();
        studentID = intentAssmnt.getStringExtra("ID");

        init();
    }

    private void init() {

        exit_btn = findViewById(R.id.exitAnyway);
        exit_btn.setOnClickListener(click->{
            Intent intent=new Intent(this, AccountActivity.class);
            intent.putExtra("ID", studentID);
            startActivity(intent);
        });

        cancel_btn = findViewById(R.id.cancel);
        cancel_btn.setOnClickListener(click->{
            //Intent intent=new Intent(this, OrientationActivity.class);
            //startActivity(intent);
            //exit
            this.finish();
        });


    }




}