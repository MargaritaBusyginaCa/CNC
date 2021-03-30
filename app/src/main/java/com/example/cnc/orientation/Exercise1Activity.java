package com.example.cnc.orientation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.sql.TimestampDBHelper;
import com.example.cnc.submit.SubmitActivity;
import com.example.cnc.submit.SubmitActivity1;
import com.example.cnc.supporters.Timestamp;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class Exercise1Activity extends AppCompatActivity {
    Button submitBtn, noSubmitBtn, ex2Btn;
    String studentID;
    private TimestampDBHelper dbHelper;
    Timestamp ts;


      //images from /res/drawable/

    int[] flags = new int[]{
            R.drawable.ori_ex1, R.drawable.ori_p18, R.drawable.ori_p19, R.drawable.ori_p20,
            R.drawable.ori_p21, R.drawable.ori_p22, R.drawable.ori_p23, R.drawable.ori_p24,
            R.drawable.ori_p25, R.drawable.ori_p26a, R.drawable.ori_endex1,
    };



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ori_activity_exercise1);

//        Intent intentEmail = getIntent();
//        studentID = intentEmail.getStringExtra("ID");

        init();
    }

    private void init() {

        // Keys used in Hashmap
        String[] from = {"flag"};

        // Ids of views in listview_layout
        int[] to = {R.id.flag};

        // Each row in the list stores flag (image)
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 11; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("flag", Integer.toString(flags[i]));
            aList.add(hm);
        }

        // R.layout.ori_listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.ori_listview_layout, from, to);

        ListView listView = (ListView) findViewById(R.id.ex1_listview);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);


        View footer = getLayoutInflater().inflate(R.layout.ori_next_footerview, null);


        // listView.addFooterView(footer);
        listView.addFooterView(footer, null, false);


        //--- No submit ---
        noSubmitBtn = footer.findViewById(R.id.exitNotSubmit);
        noSubmitBtn.setOnClickListener(click -> {
            Intent intent = new Intent(this, AlertExerciseNoSubmitActivity.class);
 //           intent.putExtra("ID", studentID);
            startActivity(intent);
        });

        //--- Exercise 2 ---
        ex2Btn = footer.findViewById(R.id.goToNext);
        ex2Btn.setOnClickListener(click->{
            Intent intent=new Intent(this, Exercise2Activity.class);
  //          intent.putExtra("ID", studentID);
            startActivity(intent);
        });
/*
        //--- submit ---
        //---Passing title & student to submission page --
        submitBtn = footer.findViewById(R.id.goToSubmit);
        submitBtn.setOnClickListener(click -> {
            String timeStamp = getTimestamp();
            add_Ori_Timestamp(studentID, "00", timeStamp);
            Intent intent = new Intent(this, SubmitActivity1.class);
            intent.putExtra("TITLE", "Orientation");
            intent.putExtra("ID", studentID);
            intent.putExtra("STARTTIME", "N/A");
            intent.putExtra("ENDTIME", timeStamp);
            startActivity(intent);
        });
*/

    }
    private String getTimestamp(){

        Calendar calendar = Calendar.getInstance();
        //String timestamp = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = df.format(calendar.getTime());
        return timestamp;
    }

}