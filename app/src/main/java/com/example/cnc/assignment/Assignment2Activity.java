package com.example.cnc.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.sql.TimestampDBHelper;
import com.example.cnc.submit.SubmitChoiceActivity;
import com.example.cnc.supporters.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.example.cnc.checklist.Checklist2Activity_2.checklist2completed;

public class Assignment2Activity extends AppCompatActivity {
    Button submitBtn, noSubmitBtn;
    String s_timestamp, ck_timestamp, ts;
    private TimestampDBHelper dbHelper;
    Timestamp ts_new;

    //here you have to give image name which you already pasted it in /res/drawable/

    int[] flags = new int[]{
            R.drawable.assi_ass2_header, R.drawable.ass2_p1, R.drawable.ass2_p2, R.drawable.ass2_p3, R.drawable.ass2_p4, R.drawable.ass2_p5,
            R.drawable.ass2_p6, R.drawable.ass2_p7, R.drawable.ass2_p8,R.drawable.assi_end,
    };



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assi_activity_ass2);

        Intent intentAssmnt = getIntent();
         s_timestamp = intentAssmnt.getStringExtra("START_TS");
        ck_timestamp = intentAssmnt.getStringExtra("CK_TS");

        checklist2completed = false;
        init();
    }

    private void init() {
        // Each row in the list stores title, page num and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<10;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
             hm.put("flag", Integer.toString(flags[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "flag"};

        // Ids of views in listview_layout
        int[] to = { R.id.flag};

        // Instantiating an adapter to store each items
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.ori_listview_layout, from, to);

        ListView listView = ( ListView ) findViewById(R.id.ex1_listview);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);


        View footer = getLayoutInflater().inflate(R.layout.ori_submit_footerview, null);


        // listView.addFooterView(footer);
        listView.addFooterView(footer, null, false);



        //--- No submit ---
        noSubmitBtn = footer.findViewById(R.id.exitNotSubmit);
        noSubmitBtn.setOnClickListener(click -> {
            Intent intent = new Intent(this, AlertAssignmentNoSubmitActivity.class);
            startActivity(intent);
        });

        //--- submit ---
        submitBtn = footer.findViewById(R.id.goToSubmit);
        submitBtn.setOnClickListener(click->{

            String e_timestamp = getNewTimestamp();
            //add_Timestamp(studentID, "11", ck_timestamp);
            //add_Timestamp(studentID, "12", s_timestamp);
            //add_Timestamp(studentID, "13", e_timestamp);
            //String ck_timestamp = tsGetFromDB(studentID, code);
            String desc = "Checklist 2 & Assignment 2 are completed.";
            //Intent intent = new Intent(this, SubmitActivity1.class);
            Intent intent = new Intent(this, SubmitChoiceActivity.class);
            intent.putExtra("TITLE", "Assignment 2");
            intent.putExtra("DESC", desc);
            intent.putExtra("CK_TS", ck_timestamp);
            intent.putExtra("START_TS", s_timestamp);
            intent.putExtra("END_TS", e_timestamp);
            intent.putExtra("CK_CODE", "21");
            intent.putExtra("S_CODE", "22");
            intent.putExtra("E_CODE", "23");
            startActivity(intent);
        });
    }

    private String getNewTimestamp(){

        Calendar calendar = Calendar.getInstance();
        //String timestamp = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = df.format(calendar.getTime());
        return timestamp;
    }

    // -- get timestamp from the database
    private String tsGetFromDB(String id, String code) {

        dbHelper = new TimestampDBHelper(this);
        return (ts = dbHelper.getTimestamp((id+code), code));

    }
/*
    // -- save Timestamp into the database
    private void add_Timestamp(String sID, String code, String timestamp) {
        dbHelper = new TimestampDBHelper(this);
        //dbHelper.check();
        ts_new = new Timestamp();

        ts_new.setStudentID(sID);
        ts_new.setAssmntCode(code);
        ts_new.setTimestamp(timestamp);

        if (dbHelper.isExist(sID, code)){
            dbHelper.updateTimestamp(ts_new);
        }else {
            dbHelper.addTimestamp(ts_new);
        }

    }
*/

}