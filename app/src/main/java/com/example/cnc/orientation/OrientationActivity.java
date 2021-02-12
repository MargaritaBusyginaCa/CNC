package com.example.cnc.orientation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cnc.R;
import com.example.cnc.main.MainActivity;
import com.example.cnc.submit.SubmitActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrientationActivity extends Activity {

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
            R.drawable.ori_p1,
            R.drawable.ori_p2,
            R.drawable.ori_p3,
            R.drawable.ori_p4,
            R.drawable.ori_p5,
            R.drawable.ori_p6,
            R.drawable.ori_p6,
            R.drawable.ori_p6,
            R.drawable.ori_p6,
            R.drawable.ori_p6,
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

        for(int i=0;i<10;i++){
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
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.ori_listview_layout, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = ( ListView ) findViewById(R.id.listview);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);
    }
}