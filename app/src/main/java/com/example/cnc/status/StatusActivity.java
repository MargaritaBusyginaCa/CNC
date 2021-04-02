package com.example.cnc.status;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.loginPage.AccountActivity;
import com.example.cnc.orientation.AlertExerciseNoSubmitActivity;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.sql.TimestampDBHelper;
import com.example.cnc.supporters.User;

import java.util.ArrayList;
import java.util.List;

import static com.example.cnc.loginPage.LoginActivity.studentID_def;
import com.example.cnc.supporters.Timestamp;


public class StatusActivity extends AppCompatActivity{
    private final AppCompatActivity activity = StatusActivity.this;
    Button exitBtn;
    String email;
    private TimestampDBHelper tsDBHelper;
    String ori_ts = null;
    String ck1_ts = null;
    String a1s_ts = null;
    String a1e_ts = null;
    String ck2_ts = null;
    String a2s_ts = null;
    String a2e_ts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        init();
    }

    private void init() {


        //--- retrieve email from database
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<User> users = databaseHelper.getAllUser();
        Integer index=0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getStudentID().equalsIgnoreCase(studentID_def)) {
                index = i;
                break;
            }
        }
        String email = users.get(index).getEmail();
        //--- retrieve timestamp from database
        ori_ts = getTsFromDB(studentID_def, "01");
        ck1_ts = getTsFromDB(studentID_def, "11");
        a1s_ts = getTsFromDB(studentID_def, "12");
        a1e_ts = getTsFromDB(studentID_def, "13");
        ck2_ts = getTsFromDB(studentID_def, "21");
        a2s_ts = getTsFromDB(studentID_def, "22");
        a2e_ts = getTsFromDB(studentID_def, "23");

        //----- display ----
        TextView typedStudent = findViewById(R.id.submitStudentID);
        typedStudent.setText(studentID_def);
        TextView typedEmail = findViewById(R.id.submitEmail);
        typedEmail.setText(email);
        TextView typedOri = findViewById(R.id.ori_timestamp);
        typedOri.setText(ori_ts);
        TextView typedCK1 = findViewById(R.id.ck1_timestamp);
        typedCK1.setText(ck1_ts);
        TextView typedA1S = findViewById(R.id.a1s_timestamp);
        typedA1S.setText(a1s_ts);
        TextView typedA1E = findViewById(R.id.a1e_timestamp);
        typedA1E.setText(a1e_ts);
        TextView typedCK2 = findViewById(R.id.ck2_timestamp);
        typedCK2.setText(ck2_ts);
        TextView typedA2S = findViewById(R.id.a2s_timestamp);
        typedA2S.setText(a2s_ts);
        TextView typedA2E = findViewById(R.id.a2e_timestamp);
        typedA2E.setText(a2e_ts);

        //--- Exit ---
        exitBtn = findViewById(R.id.exitNotSubmit);
        exitBtn.setOnClickListener(click->{
            Intent intent=new Intent(this, AccountActivity.class);
            startActivity(intent);
        });


    }

    // -- check status from the database
    private boolean isCompleted(String id, String code) {
        tsDBHelper = new TimestampDBHelper(this);
        String priKey = id + code;
        return (tsDBHelper.isExist(priKey, code));
    }
    // -- get timestamp from the database
    private String getTsFromDB(String id, String code) {
        tsDBHelper = new TimestampDBHelper(this);
        String priKey = id + code;
      //  return (tsDBHelper.getTimestamp(priKey, code));
        if(tsDBHelper.isExist(priKey, code)) {
            List<Timestamp> ts = tsDBHelper.getAllRecords();
            Integer index = 0;
            for (int i = 0; i < ts.size(); i++) {
                if (ts.get(i).getStudentID().equalsIgnoreCase(priKey)) {
                    index = i;
                    break;
                }
            }
            String final_ts = ts.get(index).getTimestamp();
            return final_ts;
        }else {
            return null;
        }
    }



}
