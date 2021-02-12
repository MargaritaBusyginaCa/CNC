package com.example.cnc.loginPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.cnc.R;
import com.example.cnc.checklist.CheckListActivity;
import com.example.cnc.orientation.OrientationActivity;
import com.example.cnc.submit.SubmitActivity;

public class AccountActivity extends AppCompatActivity {
Button bt_orientation, bt_checklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        bt_orientation=findViewById(R.id.orientation_bt);
        bt_orientation.setOnClickListener(click->{
            Intent intent=new Intent(this, OrientationActivity.class);
            startActivity(intent);
        });

        bt_checklist=findViewById(R.id.check_list_bt);
        bt_checklist.setOnClickListener(click->{
            Intent intent=new Intent(this, CheckListActivity.class);
            startActivity(intent);
        });

        Button submitAct=findViewById(R.id.submission_bt);
        submitAct.setOnClickListener(click->{
            Intent intent=new Intent(this, SubmitActivity.class);
            startActivity(intent);
        });
    }
}