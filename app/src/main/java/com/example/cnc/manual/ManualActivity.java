package com.example.cnc.manual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.loginPage.AccountActivity;
import com.example.cnc.orientation.Exercise1Activity;
import com.example.cnc.orientation.Exercise2Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManualActivity extends AppCompatActivity {
    Button noSubmitBtn;
    String studentID;



    //pasted it in /res/drawable/

    int[] flags = new int[]{
            R.drawable.man_p1, R.drawable.man_p2, R.drawable.man_p3, R.drawable.man_p4, R.drawable.man_p5,
            R.drawable.man_p6,
    };



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_activity_main);


        // Each row in the list stores flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<6;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
             hm.put("flag", Integer.toString(flags[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "flag"};

         int[] to = { R.id.flag};

        // R.layout.ori_listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.ori_listview_layout, from, to);

        ListView listView = (ListView) findViewById(R.id.listview);


        // Setting the adapter to the listView
        listView.setAdapter(adapter);

          //--- No submit ---
        noSubmitBtn = findViewById(R.id.exitBtn);
        noSubmitBtn.setOnClickListener(click->{
            Intent intent=new Intent(this, AccountActivity.class);
             startActivity(intent);
        });
    }
}
