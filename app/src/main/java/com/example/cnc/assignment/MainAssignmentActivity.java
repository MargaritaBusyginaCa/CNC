package com.example.cnc.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.checklist.Checklist2Activity;
import com.example.cnc.checklist.Checklist1Activity;
import com.example.cnc.sql.TimestampDBHelper;

import static com.example.cnc.checklist.Checklist1Activity_2.checklistcompleted;
import static com.example.cnc.checklist.Checklist2Activity_2.checklist2completed;
import static com.example.cnc.loginPage.LoginActivity.studentID_def;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainAssignmentActivity extends AppCompatActivity {
    Button bt_c1, bt_c2, bt_c3, bt_a1, bt_a2, bt_a3;
    String studentID, code, ck_ts;
    private TimestampDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assi_activity_main);

        Intent intentOri = getIntent();
        ck_ts = intentOri.getStringExtra("CK_TS");
        studentID = studentID_def;
        init();
    }

    private void init() {
        // Checklist 1
        bt_c1=findViewById(R.id.check1Button);
        bt_c1.setOnClickListener(click->{
            code = "01";
           if((isCompleted(studentID, code)) == true){
            //don't use it!!
            //if(orientationCompleted==true){
      String s_timestamp = getTimestamp();
                Intent intent=new Intent(this, Checklist1Activity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"You have to complete the Orientation first",Toast.LENGTH_LONG).show();
            }
        });

        // Checklist 2
        bt_c2=findViewById(R.id.check2Button);
        bt_c2.setOnClickListener(click-> {
            code = "13";
            if((isCompleted(studentID, code)) == true){
                Intent intent=new Intent(this, Checklist2Activity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"You have to complete the Lab Assignment 1 first",Toast.LENGTH_LONG).show();
            }
        });


        // Assignment 1
        bt_a1 = findViewById(R.id.assig1Button);
        bt_a1.setOnClickListener(click -> {
            //if the cheklist was completed, it calls Intent object
            if(checklistcompleted==true){
                String s_timestamp = getTimestamp();
                Intent intent = new Intent(this, Assignment1Activity.class);
                intent.putExtra("CK_TS", ck_ts);
                intent.putExtra("START_TS", s_timestamp);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"You have to complete the Cheklist 1 first",Toast.LENGTH_LONG).show();
            }
        });

        // Assignment 2
        bt_a2 = findViewById(R.id.assig2Button);
        bt_a2.setOnClickListener(click -> {
            //if the cheklist was completed, it calls Intent object
            if(checklist2completed==true){
                String s_timestamp = getTimestamp();
                Intent intent = new Intent(this, Assignment2Activity.class);
                intent.putExtra("CK_TS", ck_ts);
                intent.putExtra("START_TS", s_timestamp);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"You have to complete the Cheklist 2 first",Toast.LENGTH_LONG).show();
            }
        });

    }
    // create Timestamp
    private String getTimestamp(){

        Calendar calendar = Calendar.getInstance();
        //String timestamp = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = df.format(calendar.getTime());
        return timestamp;
    }

    // -- check status from the database
    private boolean isCompleted(String id, String code) {
        dbHelper = new TimestampDBHelper(this);
        String priKey = id + code;
        return (dbHelper.isExist(priKey, code));
        //String ts = dbHelper.getTimestamp(id, code);
    }


}