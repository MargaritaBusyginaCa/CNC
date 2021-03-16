package com.example.cnc.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.checklist.ChecklistActivity_2;
import com.example.cnc.sql.TimestampDBHelper;
import com.example.cnc.submit.SubmitChoiceActivity;
import com.example.cnc.submit.SubmitActivity1;
import com.example.cnc.supporters.Timestamp;
import static com.example.cnc.checklist.ChecklistActivity_2.checklistcompleted;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Assignment1Activity extends AppCompatActivity {
    Button submitBtn, noSubmitBtn;
    String studentID, s_timestamp, ck_timestamp, ts;
    String code = "11";
    private TimestampDBHelper dbHelper;
    Timestamp ts_new;
    //ChecklistActivity_2 ck2;

    //here you have to give image name which you already pasted it in /res/drawable/

    int[] flags = new int[]{
            R.drawable.assi_ass1_header, R.drawable.ori_p18, R.drawable.ori_p19, R.drawable.ori_p20,
            R.drawable.ori_p21, R.drawable.ori_p22, R.drawable.ori_p23, R.drawable.ori_p24,
            R.drawable.ori_p25, R.drawable.ori_p26a, R.drawable.assi_end,
    };



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assi_activity_ass1);

        Intent intentAssmnt = getIntent();
        studentID = intentAssmnt.getStringExtra("ID");
        s_timestamp = intentAssmnt.getStringExtra("START_TS");
        ck_timestamp = intentAssmnt.getStringExtra("CK_TS");

        checklistcompleted = false;
        init();
    }

    private void init() {
        // Each row in the list stores title, page num and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<11;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
             hm.put("flag", Integer.toString(flags[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "flag"};

        // Ids of views in listview_layout
        int[] to = { R.id.flag};

        // Instantiating an adapter to store each items
        // R.layout.ori_listview_layout defines the layout of each item
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
            intent.putExtra("ID", studentID);
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
            String desc = "Checklist 1 & Assignment 1 are completed.";
            Intent intent = new Intent(this, SubmitChoiceActivity.class);

            //Intent intent = new Intent(this, SubmitChoiceActivity.class);
            intent.putExtra("TITLE", "Assignment 1");
            intent.putExtra("ID", studentID);
            intent.putExtra("DESC", desc);
            intent.putExtra("CK_TS", ck_timestamp);
            intent.putExtra("START_TS", s_timestamp);
            intent.putExtra("END_TS", e_timestamp);
            intent.putExtra("CK_CODE", "11");
            intent.putExtra("S_CODE", "12");
            intent.putExtra("E_CODE", "13");
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
        return (ts = dbHelper.getTimestamp(id, code));

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