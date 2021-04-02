package com.example.cnc.loginPage;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cnc.R;
import com.example.cnc.admin.AdminActivity;
import com.example.cnc.assignment.MainAssignmentActivity;
import com.example.cnc.main.MainActivity;
import com.example.cnc.manual.ManualActivity;
import com.example.cnc.orientation.OrientationActivity;
import com.example.cnc.status.StatusActivity;

import static com.example.cnc.loginPage.LoginActivity.studentID_def;

public class AccountActivity extends AppCompatActivity {
    Button bt_orientation, bt_assignment, bt_submit, bt_manual, bt_status, bt_logout, bt_admin;
    String adminID = "999999";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        bt_orientation=findViewById(R.id.orientation_bt);
        bt_assignment=findViewById(R.id.assignments_bt);
        bt_manual=findViewById(R.id.manual_bt);
        bt_status=findViewById(R.id.status_bt);
        bt_logout=findViewById(R.id.logout_bt);
        bt_admin=findViewById(R.id.admin_bt);

        if(studentID_def.equals(adminID)){
             bt_admin.setVisibility(View.VISIBLE);
        }else{
            bt_admin.setVisibility(View.GONE);
        }
        init();
    }

    private void init() {
         bt_orientation.setOnClickListener(click->{

            Intent intent=new Intent(this, OrientationActivity.class);
           startActivity(intent);
        });

        bt_assignment.setOnClickListener(click->{
            Intent intent=new Intent(this, MainAssignmentActivity.class);
            startActivity(intent);
        });

        bt_manual.setOnClickListener(click->{
            Intent intent=new Intent(this, ManualActivity.class);
            startActivity(intent);
        });

        bt_status.setOnClickListener(click->{
            Intent intent=new Intent(this, StatusActivity.class);
            startActivity(intent);
        });

         bt_logout.setOnClickListener(click->{
            Toast.makeText(this, "Bye!", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        bt_admin.setOnClickListener(click->{
            Intent intent=new Intent(this, AdminActivity.class);
            startActivity(intent);
        });

    }

}