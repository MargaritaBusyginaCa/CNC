package com.example.cnc.checklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.cnc.R;
import com.example.cnc.assignment.MainAssignmentActivity;
import com.example.cnc.loginPage.AccountActivity;
import com.example.cnc.sql.TimestampDBHelper;
import com.example.cnc.supporters.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChecklistActivity_2 extends AppCompatActivity {
    public static boolean checklistcompleted;
    Button bt_submit;
    private CheckBox cb_dont_1, cb_dont_2, cb_dont_3, cb_dont_4;
    private ArrayList<CheckBox> checkBoxes2;
    String studentID;
    private TimestampDBHelper dbHelper;
    Timestamp ts;

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
        // get student ID from previous activity
        Intent intentOri = getIntent();
        studentID = intentOri.getStringExtra("ID");

        bt_submit = findViewById(R.id.bt_submit_checklist);
        bt_submit.setOnClickListener(click -> {
            if (allChecked()) {

                // --- get new timestamp ---
                String timeStamp = getNewTimestamp();

                System.out.println("Checkboxes are checked");
                Toast.makeText(getApplicationContext(), "Checklist is submitted", Toast.LENGTH_LONG).show();
                checklistcompleted = true;
                isCompleted(1);
                Intent intent = new Intent(this, MainAssignmentActivity.class);
                intent.putExtra("ID", studentID);
                intent.putExtra("CK_TS", timeStamp);
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

    // -- create Timestamp
    private String getNewTimestamp() {

        Calendar calendar = Calendar.getInstance();
        //String timestamp = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = df.format(calendar.getTime());
        return timestamp;
    }

    private boolean isCompleted(int num) {

        if (num == 0) {
            return false;
        }else {
            return true;
        }
    }




}
