package com.example.cnc.checklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.cnc.R;
import com.example.cnc.loginPage.AccountActivity;

import java.util.ArrayList;

public class ChecklistActivity_2 extends AppCompatActivity {
    public static  boolean checklistcompleted;
    Button bt_submit;
    private CheckBox cb_dont_1, cb_dont_2, cb_dont_3, cb_dont_4;
    private ArrayList<CheckBox> checkBoxes2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_2);
        cb_dont_1 = findViewById(R.id.cb_dont_1);
        cb_dont_2 = findViewById(R.id.cb_dont_2);
        cb_dont_3 = findViewById(R.id.cb_dont_3);
        cb_dont_4 = findViewById(R.id.cb_dont_4);
        checkBoxes2 = new ArrayList<>();

        checkBoxes2.add(cb_dont_1);
        checkBoxes2.add(cb_dont_2);
        checkBoxes2.add(cb_dont_3);
        checkBoxes2.add(cb_dont_4);

        bt_submit = findViewById(R.id.bt_submit_checklist);
        bt_submit.setOnClickListener(click -> {
            if (allChecked()) {
                System.out.println("Checkboxes are checked");
                Toast.makeText(getApplicationContext(), "Checklist is submitted", Toast.LENGTH_LONG).show();
               checklistcompleted=true;
                Intent intent = new Intent(this, AccountActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), "Mark all the checkboxes", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean allChecked() {
        for (CheckBox cb : checkBoxes2) {
            if (cb.isChecked() == false) {
                return false;
            }
        }
        return true;
    }
}
