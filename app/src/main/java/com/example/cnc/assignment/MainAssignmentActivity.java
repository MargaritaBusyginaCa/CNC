package com.example.cnc.assignment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.checklist.CheckListActivity;
import com.example.cnc.orientation.OrientationActivity;
import com.example.cnc.sql.TimestampDBHelper;
import com.example.cnc.submit.SubmitActivity;
import com.example.cnc.supporters.Timestamp;

import static com.example.cnc.checklist.ChecklistActivity_2.checklistcompleted;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainAssignmentActivity extends AppCompatActivity {
    Button bt_c1, bt_c2, bt_c3, bt_a1, bt_a2, bt_a3;
    String studentID, code, ck1_ts;
    private TimestampDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assi_activity_main);

        Intent intentOri = getIntent();
        studentID = intentOri.getStringExtra("ID");
        ck1_ts = intentOri.getStringExtra("CK_TS");
        init();
    }

    private void init() {
        // Checklist 1
        bt_c1=findViewById(R.id.check1Button);
        bt_c1.setOnClickListener(click->{
            code = "00";
            if((isCompleted(studentID, code)) == true){
                String s_timestamp = getTimestamp();
                Intent intent=new Intent(this, CheckListActivity.class);
                intent.putExtra("ID", studentID);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"You have to complete the Orientation first",Toast.LENGTH_LONG).show();
            }


        });
        // Checklist 2
        bt_c2=findViewById(R.id.check2Button);
        bt_c2.setOnClickListener(click-> {
            Intent intent=new Intent(this, WarningNoAccessActivity.class);
            startActivity(intent);
            // Toast.makeText(this, "ACCESS UNAVAILABLE!", Toast.LENGTH_LONG).show();
        });

        // Checklist 3
        bt_c3 = findViewById(R.id.check3Button);
        bt_c3.setOnClickListener(click -> {

        //    Intent intent = new Intent(this, CheckList3Activity.class);
        //    startActivity(intent);
            Intent intent=new Intent(this, WarningNoAccessActivity.class);
            startActivity(intent);
        });

        // Assignment 1
        bt_a1 = findViewById(R.id.assig1Button);
        bt_a1.setOnClickListener(click -> {
            //if the cheklist was completed, it calls Intent object
            if(checklistcompleted==true){
                String s_timestamp = getTimestamp();
                Intent intent = new Intent(this, Assignment1Activity.class);
                intent.putExtra("ID", studentID);
                intent.putExtra("CK_TS", ck1_ts);
                intent.putExtra("START_TS", s_timestamp);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"You have to complete the Cheklist 1 first",Toast.LENGTH_LONG).show();
            }
        });

        // Assignment 2
        bt_a2 = findViewById(R.id.assig2Button);
        bt_a2.setOnClickListener(click -> {
            //    Intent intent = new Intent(this, Assignment2Activity.class);
            //    startActivity(intent);
            Intent intent=new Intent(this, WarningNoAccessActivity.class);
            startActivity(intent);
        });

        // Assignment 3
        bt_a3 = findViewById(R.id.assig3Button);
        bt_a3.setOnClickListener(click -> {
            //    Intent intent = new Intent(this, Assignment3Activity.class);
            //    startActivity(intent);
            Intent intent=new Intent(this, WarningNoAccessActivity.class);
            startActivity(intent);
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

        return (dbHelper.isExist(id, code));
        //String ts = dbHelper.getTimestamp(id, code);
    }


}