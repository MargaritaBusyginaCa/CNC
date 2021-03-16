package com.example.cnc.orientation;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.sql.TimestampDBHelper;
import com.example.cnc.submit.SubmitActivity;
import com.example.cnc.submit.SubmitActivity1;
import com.example.cnc.supporters.Timestamp;
import com.example.cnc.supporters.User;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.example.cnc.checklist.ChecklistActivity_2.checklistcompleted;

public class Exercise2Activity extends AppCompatActivity {
    Button submitBtn, noSubmitBtn;
    String studentID, title, desc;
    private TimestampDBHelper dbHelper;
    Timestamp ts_new;


    //images from /res/drawable/

    int[] flags = new int[]{

                 R.drawable.ori_p27, R.drawable.ori_p28, R.drawable.ori_p29, R.drawable.ori_p30,
                 R.drawable.ori_p31, R.drawable.ori_p32, R.drawable.ori_p33, R.drawable.ori_p34,
                 R.drawable.ori_p35, R.drawable.ori_p36a, R.drawable.ori_endex2,
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ori_activity_exercise2);

        Intent intentOri = getIntent();
        studentID = intentOri.getStringExtra("ID");
        desc = "Orientation Completed";


        init();
    }

    private void init() {

        // Each row in the list stores flag (image)
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

        // R.layout.ori_listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.ori_listview_layout, from, to);

        ListView listView = (ListView) findViewById(R.id.ex2_listview);


        // Setting the adapter to the listView
        listView.setAdapter(adapter);

        View footer = getLayoutInflater().inflate(R.layout.ori_submit_footerview, null);

        // listView.addFooterView(footer);
        listView.addFooterView(footer, null, false);

        //--- No submit ---
        noSubmitBtn = footer.findViewById(R.id.exitNotSubmit);
        noSubmitBtn.setOnClickListener(click->{
            Intent intent=new Intent(this, AlertExerciseNoSubmitActivity.class);
            intent.putExtra("ID", studentID);
            startActivity(intent);
        });

        //--- submit ---
        //---Passing title & student to submission page --
        submitBtn = footer.findViewById(R.id.goToSubmit);
        submitBtn.setOnClickListener(click -> {
            String timeStamp = getNewTimestamp();
            //add_Timestamp(studentID, "00", timeStamp);
            Intent intent = new Intent(this, SubmitActivity1.class);
            intent.putExtra("TITLE", "Orientation");
            intent.putExtra("ID", studentID);
            intent.putExtra("DESC", desc);
            intent.putExtra("END_TS", timeStamp);
            intent.putExtra("E_CODE", "01");
            checklistcompleted=true;
            startActivity(intent);
        });


    }
    // -- create Timestamp
    private String getNewTimestamp(){

        Calendar calendar = Calendar.getInstance();
        //String timestamp = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = df.format(calendar.getTime());
        return timestamp;
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
