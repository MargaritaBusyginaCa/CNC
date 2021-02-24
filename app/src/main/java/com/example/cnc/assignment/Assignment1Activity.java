package com.example.cnc.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.loginPage.AccountActivity;
import com.example.cnc.submit.SubmitActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Assignment1Activity extends AppCompatActivity {
    Button submitBtn, noSubmitBtn;


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
        noSubmitBtn.setOnClickListener(click->{
            Intent intent=new Intent(this, AlertAssignmentNoSubmitActivity.class);
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