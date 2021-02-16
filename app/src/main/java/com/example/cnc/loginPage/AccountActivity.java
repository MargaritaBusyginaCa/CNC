package com.example.cnc.loginPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.cnc.R;
import com.example.cnc.assignment.Assignment1Activity;
import com.example.cnc.assignment.MainAssignmentActivity;
import com.example.cnc.manual.ManualActivity;
import com.example.cnc.orientation.OrientationActivity;
import com.example.cnc.submit.SubmitActivity;

public class AccountActivity extends AppCompatActivity {
Button bt_orientation, bt_assignment, bt_submit, bt_manual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        bt_orientation=findViewById(R.id.orientation_bt);
        bt_orientation.setOnClickListener(click->{
            Intent intent=new Intent(this, OrientationActivity.class);
            startActivity(intent);
        });

        bt_assignment=findViewById(R.id.assignments_bt);
        bt_assignment.setOnClickListener(click->{
            Intent intent=new Intent(this, MainAssignmentActivity.class);
            startActivity(intent);
        });


        bt_submit=findViewById(R.id.submission_bt);
        bt_submit.setOnClickListener(click->{
            Intent intent=new Intent(this, SubmitActivity.class);
            startActivity(intent);
        });


        bt_manual=findViewById(R.id.manual_bt);
        bt_manual.setOnClickListener(click->{
            Intent intent=new Intent(this, ManualActivity.class);
            startActivity(intent);
        });
    }
}