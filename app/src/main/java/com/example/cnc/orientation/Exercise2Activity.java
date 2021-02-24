package com.example.cnc.orientation;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.submit.SubmitActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Exercise2Activity extends AppCompatActivity {
    Button submitBtn, noSubmitBtn;

    // Array of strings storing title names
    String[] title = new String[] {
            "Configuration",
            "Operation mode",
            "CNC screen 1",
            "CNC screen 2",
            "Operation 1",
            "Operation 2",
            "Operation 3",
            "Editing 1",
            "Editing 2",
            "In/Output"
    };

    // Array of integers points to images stored in /res/drawable/

    //here you have to give image name which you already pasted it in /res/drawable/

    int[] flags = new int[]{

                 R.drawable.ori_p27, R.drawable.ori_p28, R.drawable.ori_p29, R.drawable.ori_p30,
                 R.drawable.ori_p31, R.drawable.ori_p32, R.drawable.ori_p33, R.drawable.ori_p34,
                 R.drawable.ori_p35, R.drawable.ori_p36a, R.drawable.ori_endex2,


    };

    // Array of strings to store page number
    String[] count = new String[]{
            "p1",
            "p2",
            "p3",
            "p4",
            "p5",
            "p6",
            "p7",
            "p8",
            "p9",
            "p10"
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ori_activity_exercise2);

        // Each row in the list stores title, page num and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<11;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            //   hm.put("txt", "Title : " + title[i]);
            //    hm.put("cur","Count : " + count[i]);
            hm.put("flag", Integer.toString(flags[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        //String[] from = { "flag","txt","cur" };
        String[] from = { "flag"};

        // Ids of views in listview_layout
        // int[] to = { R.id.flag,R.id.txt,R.id.cur};
        int[] to = { R.id.flag};

        // Instantiating an adapter to store each items
        // R.layout.ori_listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.ori_listview_layout, from, to);

        ListView listView = (ListView) findViewById(R.id.ex2_listview);


        //--- Exercise 1 ---
        //  ex1Btn = findViewById(R.id.ex1Button);
        // ex1Btn.setOnClickListener(new View.OnClickListener() {
        //      public void onClick(View view) {
        //          startActivity(new Intent(OrientationActivity.this, exercise1Activity.class));
        //       }
        //   });

        // Setting the adapter to the listView
        listView.setAdapter(adapter);

        View footer = getLayoutInflater().inflate(R.layout.ori_submit_footerview, null);


        // listView.addFooterView(footer);
        listView.addFooterView(footer, null, false);



        //--- No submit ---
        noSubmitBtn = footer.findViewById(R.id.exitNotSubmit);
        noSubmitBtn.setOnClickListener(click->{
            Intent intent=new Intent(this, AlertExerciseNoSubmitActivity.class);
            startActivity(intent);
        });

        //--- submit ---
        submitBtn = footer.findViewById(R.id.goToSubmit);
        submitBtn.setOnClickListener(click->{
            Intent intent=new Intent(this, SubmitActivity.class);
            startActivity(intent);
        });
    }
}
