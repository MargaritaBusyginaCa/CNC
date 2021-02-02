package com.example.cnc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SubmitActivity extends AppCompatActivity {

    MyListAdapter myAdapter;
    private ArrayList<String> elements = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        loadData();
        ListView myList = findViewById(R.id.theListView);
        myAdapter = new MyListAdapter();
        myList.setAdapter(myAdapter);


    }

    private class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return elements.size() ; //list will have 10 items
        }

        @Override //shows what string is at row i (0-9)
        public String getItem(int i) {
            return elements.get(i);
        }

        @Override //returns the database id of row i
        public long getItemId(int i) { return i;} //worry about this next week

        @Override //how to show row i
        public View getView(int i, View view, ViewGroup viewGroup) {
            String msg=getItem(i);
            LayoutInflater inflater = getLayoutInflater(); //this loads xml layouts
            View thisRow;
            thisRow= inflater.inflate(R.layout.row_submitpicture, viewGroup, false);
            TextView tv = thisRow.findViewById(R.id.textGoesHere);
            tv.setText( getItem(i)); //what goes in row i
            return thisRow;
        }
    }

    private void loadData() {
            elements.clear();
            elements.add("picture 1");
            elements.add("picture 2");
            elements.add("picture 3");
            elements.add("picture 4");
            elements.add("picture 5");
            elements.add("picture 6");
        }
}