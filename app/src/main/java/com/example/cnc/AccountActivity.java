package com.example.cnc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AccountActivity extends AppCompatActivity {
Button bt_checklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        bt_checklist=findViewById(R.id.check_list_bt);
        bt_checklist.setOnClickListener(click->{
            Intent intent=new Intent(this, CheckList_pdf_reader.class);
            startActivity(intent);
        });

        Button submitAct=findViewById(R.id.submission_bt);
        submitAct.setOnClickListener(click->{
            Intent intent=new Intent(this,SubmitActivity.class);
            startActivity(intent);
        });
    }
}