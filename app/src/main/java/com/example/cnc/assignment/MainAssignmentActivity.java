package com.example.cnc.assignment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.checklist.CheckListActivity;
import com.example.cnc.orientation.OrientationActivity;
import com.example.cnc.submit.SubmitActivity;

import static com.example.cnc.checklist.ChecklistActivity_2.checklistcompleted;

public class MainAssignmentActivity extends AppCompatActivity {
    Button bt_c1, bt_c2, bt_c3, bt_a1, bt_a2, bt_a3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assi_activity_main);
        init();
    }

    private void init() {
      bt_c1=findViewById(R.id.check1Button);
        bt_c1.setOnClickListener(click->{
            Intent intent=new Intent(this, CheckListActivity.class);
            startActivity(intent);
        });

        bt_c2=findViewById(R.id.check2Button);
        bt_c2.setOnClickListener(click-> {
            Intent intent=new Intent(this, WarningNoAccessActivity.class);
            startActivity(intent);
            // Toast.makeText(this, "ACCESS UNAVAILABLE!", Toast.LENGTH_LONG).show();
        });


        bt_c3 = findViewById(R.id.check3Button);
        bt_c3.setOnClickListener(click -> {

        //    Intent intent = new Intent(this, CheckList3Activity.class);
        //    startActivity(intent);
            Intent intent=new Intent(this, WarningNoAccessActivity.class);
            startActivity(intent);
        });

        bt_a1 = findViewById(R.id.assig1Button);
        bt_a1.setOnClickListener(click -> {
            //if the cheklist was completed, it calls Intent object
            if(checklistcompleted==true){
                Intent intent = new Intent(this, Assignment1Activity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"You have to complete the Cheklist 1 first",Toast.LENGTH_LONG).show();
            }

        });

        bt_a2 = findViewById(R.id.assig2Button);
        bt_a2.setOnClickListener(click -> {
            //    Intent intent = new Intent(this, Assignment2Activity.class);
            //    startActivity(intent);
            Intent intent=new Intent(this, WarningNoAccessActivity.class);
            startActivity(intent);
        });

        bt_a3 = findViewById(R.id.assig3Button);
        bt_a3.setOnClickListener(click -> {
            //    Intent intent = new Intent(this, Assignment3Activity.class);
            //    startActivity(intent);
            Intent intent=new Intent(this, WarningNoAccessActivity.class);
            startActivity(intent);
        });

    }
}