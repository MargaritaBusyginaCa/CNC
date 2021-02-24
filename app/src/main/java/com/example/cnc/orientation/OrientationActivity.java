package com.example.cnc.orientation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.assignment.MainAssignmentActivity;
import com.example.cnc.loginPage.AccountActivity;
import com.example.cnc.main.MainActivity;
import com.example.cnc.manual.ManualActivity;

import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrientationActivity extends AppCompatActivity {
    Button ex1Btn, ex2Btn;

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
            R.drawable.ori_p1, R.drawable.ori_p2, R.drawable.ori_p3, R.drawable.ori_p4, R.drawable.ori_p5,
            R.drawable.ori_p6, R.drawable.ori_p7, R.drawable.ori_p8, R.drawable.ori_p9, R.drawable.ori_p10,
            R.drawable.ori_p11, R.drawable.ori_p12, R.drawable.ori_p13, R.drawable.ori_p14, R.drawable.ori_p15,
            R.drawable.ori_p16, R.drawable.ori_p17,
       /*
            R.drawable.ori_ex1, R.drawable.ori_p18, R.drawable.ori_p19,
            R.drawable.ori_p20, R.drawable.ori_p21, R.drawable.ori_p22, R.drawable.ori_p23, R.drawable.ori_p24,
            R.drawable.ori_p25, R.drawable.ori_p26, R.drawable.ori_p27, R.drawable.ori_p28, R.drawable.ori_p29,
            R.drawable.ori_p30, R.drawable.ori_p31, R.drawable.ori_p32, R.drawable.ori_p33, R.drawable.ori_p34,
            R.drawable.ori_p35, R.drawable.ori_p36, R.drawable.ori_end,

        */
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
        setContentView(R.layout.activity_orientation);

        // Each row in the list stores title, page num and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<17;i++){
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

        ListView listView = (ListView) findViewById(R.id.listview);


        // Setting the adapter to the listView
        listView.setAdapter(adapter);

        View footer = getLayoutInflater().inflate(R.layout.ori_exercise_footerview, null);


        // listView.addFooterView(footer);
        listView.addFooterView(footer, null, false);


        //--- Exercise 1 ---
        ex1Btn = footer.findViewById(R.id.ex1Button);
        ex1Btn.setOnClickListener(click->{
            Intent intent=new Intent(OrientationActivity.this, Exercise1Activity.class);
            startActivity(intent);
        });

        //--- Exercise 2 ---
        ex2Btn = footer.findViewById(R.id.ex2Button);
        ex2Btn.setOnClickListener(click->{
            Intent intent=new Intent(OrientationActivity.this, Exercise2Activity.class);
            startActivity(intent);
         });


/*
        //   listView.addFooterView(ex1Btn);
        TextView footer = new TextView(this);
        footer.setGravity(Gravity.CENTER);
        footer.setTextSize(30);
        footer.setText("Footer");
        if ( footer != null ) {
            listView.addFooterView(footer);
        } else {
            throw new NullPointerException("footer is null");
        }

*/
    }
/*
    //TOOLBAR, inflate the Menu resource
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ori_menu, menu);
        return true;
    }
    //MENU item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.icon_home:
                startActivity(new Intent(OrientationActivity.this, AccountActivity.class));
                return true;
            case R.id.icon_ori:
                startActivity(new Intent(OrientationActivity.this, OrientationActivity.class));
                return true;
            case R.id.icon_assi:
                startActivity(new Intent(OrientationActivity.this, MainAssignmentActivity.class));
            case R.id.icon_man:
                startActivity(new Intent(OrientationActivity.this, ManualActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

*/



}
