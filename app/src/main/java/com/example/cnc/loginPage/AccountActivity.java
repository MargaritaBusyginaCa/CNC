package com.example.cnc.loginPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.cnc.R;
import com.example.cnc.assignment.MainAssignmentActivity;
import com.example.cnc.assignment.WarningNoAccessActivity;
import com.example.cnc.main.MainActivity;
import com.example.cnc.manual.ManualActivity;
import com.example.cnc.orientation.OrientationActivity;
import com.example.cnc.submit.SubmitActivity;

public class AccountActivity extends AppCompatActivity {
    Button bt_orientation, bt_assignment, bt_submit, bt_manual, bt_logout;
    String studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Intent intentEmail = getIntent();
        studentID = intentEmail.getStringExtra("ID");

        bt_orientation=findViewById(R.id.orientation_bt);
        bt_orientation.setOnClickListener(click->{
            Intent intent=new Intent(this, OrientationActivity.class);
            intent.putExtra("ID", studentID);
            startActivity(intent);
        });

        bt_assignment=findViewById(R.id.assignments_bt);
        bt_assignment.setOnClickListener(click->{
            Intent intent=new Intent(this, MainAssignmentActivity.class);
            intent.putExtra("ID", studentID);
            startActivity(intent);
        });

        //The submission section will be removed from the main menu!

        bt_submit=findViewById(R.id.submission_bt);
        bt_submit.setOnClickListener(click->{
            Intent intent=new Intent(this, SubmitActivity.class);
            intent.putExtra("ID", studentID);
            startActivity(intent);
        });


        bt_manual=findViewById(R.id.manual_bt);
        bt_manual.setOnClickListener(click->{
            Intent intent=new Intent(this, ManualActivity.class);
            intent.putExtra("ID", studentID);
            startActivity(intent);
        });

        bt_logout=findViewById(R.id.logout_bt);
        bt_logout.setOnClickListener(click->{
            Toast.makeText(this, "Bye!", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }
}