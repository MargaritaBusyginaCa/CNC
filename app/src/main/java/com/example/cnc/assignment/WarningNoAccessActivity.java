package com.example.cnc.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.loginPage.AccountActivity;
import com.example.cnc.orientation.OrientationActivity;

public class WarningNoAccessActivity extends AppCompatActivity {
    Button ok_btn;





    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assi_warning_no_access);
        init();
    }

    private void init() {

        ok_btn = findViewById(R.id.ok);
        ok_btn.setOnClickListener(click->{
            Intent intent=new Intent(this, MainAssignmentActivity.class);
            startActivity(intent);
        });

    }




}