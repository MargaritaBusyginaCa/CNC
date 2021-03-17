package com.example.cnc.submit;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.loginPage.AccountActivity;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.sql.TimestampDBHelper;
import com.example.cnc.supporters.Timestamp;
import com.example.cnc.supporters.User;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class SubmitActivity1 extends AppCompatActivity {
    Button noSubmitBtn, submitBtn;
    Button SelectPhoto;
    private static final int PICK_IMAGE = 100;
    //-- added by Lai Shan --
    private DatabaseHelper dbHelper;
    private TimestampDBHelper tsDBHelper;
    Timestamp ts_new;

    public List<User> users = new ArrayList<>();

    int index = -1;
    String title, email, studentID, s_timestamp, e_timestamp, desc, ck_timestamp;
    String code_ck = null;
    String code_s, code_e;

    // -----------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit1);
        ListView myList = findViewById(R.id.theListView);


        //-----TIMESTAMP -------- by Lai Shan Law -------------------
        // String ori_TS, checklist_TS, assmnt_start_TS, assmnt_end_TS;

        //----- get the info from the previous activity
        Intent intentFrPreActivity = getIntent();

        title = intentFrPreActivity.getStringExtra("TITLE");
        studentID = intentFrPreActivity.getStringExtra("ID");
        ck_timestamp = intentFrPreActivity.getStringExtra("CK_TS");
        s_timestamp = intentFrPreActivity.getStringExtra("START_TS");
        e_timestamp = intentFrPreActivity.getStringExtra("END_TS");
        desc = intentFrPreActivity.getStringExtra("DESC");
        code_ck = intentFrPreActivity.getStringExtra("CK_CODE");
        code_s = intentFrPreActivity.getStringExtra("S_CODE");
        code_e = intentFrPreActivity.getStringExtra("E_CODE");


        //--- retrieve email from database
        dbHelper = new DatabaseHelper(this);
        users = dbHelper.getAllUser();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getStudentID().equalsIgnoreCase(studentID)) {
                index = i;
                break;
            }
        }
        email = users.get(index).getEmail();
        //--- retrieve timestamps from database
        /*
        tsDBHelper = new TimestampDBHelper(this);
        ori_TS = tsDBHelper.getTimestamp(studentID, "00");
        checklist1_TS = tsDBHelper.getTimestamp(studentID, "11");
        assmnt1_start_TS = tsDBHelper.getTimestamp(studentID, "12");
        assmnt1_end_TS = tsDBHelper.getTimestamp(studentID, "13");
*/
        //----- display ----
        TextView typedTitle = findViewById(R.id.submitTitle1);
        typedTitle.setText(title);
        TextView typedStudent = findViewById(R.id.submitStudentID);
        typedStudent.setText(studentID);
        TextView typedEmail = findViewById(R.id.submitEmail);
        typedEmail.setText(email);
        TextView typedchecklist = findViewById(R.id.submitChecklistTime);
        typedchecklist.setText(ck_timestamp);
        TextView typed_S_Timestamp = findViewById(R.id.submitStartTime);
        typed_S_Timestamp.setText(s_timestamp);
        TextView typedTimestamp = findViewById(R.id.submitEndTime);
        typedTimestamp.setText(e_timestamp);
        TextView typeDesc = findViewById(R.id.submitDesc);
        typeDesc.setText(desc);

        //------END TIMESTAMP ------------------------------------------


        //--- No submit ---
        noSubmitBtn = findViewById(R.id.cancelBtn);
        noSubmitBtn.setOnClickListener(click->{
            Intent intent=new Intent(this, AccountActivity.class);
            intent.putExtra("ID", studentID);
            startActivity(intent);
        });

        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(click->{

            if (code_ck == null){
                add_Timestamp(studentID, code_e , e_timestamp);
            }else {
                add_Timestamp(studentID, code_ck, ck_timestamp);
                add_Timestamp(studentID, code_s, s_timestamp);
                add_Timestamp(studentID, code_e, e_timestamp);
            }
            Toast.makeText(getApplicationContext(),"The timestamps have been saved",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this, AccountActivity.class);
            intent.putExtra("ID", studentID);
            startActivity(intent);
        });


    }

    // -- save Timestamp into the database
    private void add_Timestamp(String sID, String code, String timestamp) {
        tsDBHelper = new TimestampDBHelper(this);

        ts_new = new Timestamp();
        String priKey = sID + code;
        ts_new.setStudentID(priKey);
        ts_new.setAssmntCode(code);
        ts_new.setTimestamp(timestamp);

        if (tsDBHelper.isExist(priKey, code)){
            tsDBHelper.updateTimestamp(ts_new);
        }else {
            tsDBHelper.addTimestamp(ts_new);
        }

    }




}