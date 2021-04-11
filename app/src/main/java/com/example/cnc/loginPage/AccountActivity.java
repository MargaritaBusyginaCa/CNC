package com.example.cnc.loginPage;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cnc.R;
import com.example.cnc.admin.AdminActivity;
import com.example.cnc.assignment.MainAssignmentActivity;
import com.example.cnc.main.MainActivity;
import com.example.cnc.manual.ManualActivity;
import com.example.cnc.orientation.OrientationActivity;
//import com.example.cnc.sql.TimestampDBHelper;
import com.example.cnc.sql.TimestampDBHelper;
import com.example.cnc.status.StatusActivity;
import com.example.cnc.submit.SubmitActivity;
import com.example.cnc.supporters.Timestamp;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static com.example.cnc.loginPage.LoginActivity.studentID_def;
import static com.example.cnc.loginPage.LoginActivity.email_def;

public class AccountActivity extends AppCompatActivity {
    Button bt_orientation, bt_assignment, bt_submit, bt_manual, bt_status, bt_logout, bt_admin;
    //String adminID = "999999";
    String adminID = "admin@algonquinlive.com";
    String studID, result, resultTS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        bt_orientation=findViewById(R.id.orientation_bt);
        bt_assignment=findViewById(R.id.assignments_bt);
        bt_manual=findViewById(R.id.manual_bt);
        bt_status=findViewById(R.id.status_bt);
        bt_logout=findViewById(R.id.logout_bt);
        bt_admin=findViewById(R.id.admin_bt);

        //if(studentID_def.equals(adminID)){
        if(email_def.equals(adminID)){
             bt_admin.setVisibility(View.VISIBLE);
        }else{
            bt_admin.setVisibility(View.GONE);
        }

        init();
    }

    private void init() {
         bt_orientation.setOnClickListener(click->{

            Intent intent=new Intent(this, OrientationActivity.class);
           startActivity(intent);
        });

        bt_assignment.setOnClickListener(click->{
            Intent intent=new Intent(this, MainAssignmentActivity.class);
            startActivity(intent);
        });

        bt_manual.setOnClickListener(click->{
            Intent intent=new Intent(this, ManualActivity.class);
            startActivity(intent);
        });

        bt_status.setOnClickListener(click->{
            Intent intent=new Intent(this, StatusActivity.class);
            startActivity(intent);
        });

         bt_logout.setOnClickListener(click->{
            clearTimestamp();
            Toast.makeText(this, "Bye!", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        bt_admin.setOnClickListener(click->{
            Intent intent=new Intent(this, AdminActivity.class);
            startActivity(intent);
        });

    }

    //-- load timestamps from Server
    private void getTimestamp(String student_id, String task_id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    String uri_params;
                    uri_params = "student_id=" + student_id;
                    uri_params += "&task_id=" + task_id;
                    String rest_url = getString(R.string.rest_url) + "get_timestamp/?";

                    HttpURLConnection connection = (HttpURLConnection) new URL(rest_url + uri_params).openConnection();
                    connection.setRequestMethod("GET");

                    int responseCode = connection.getResponseCode();

                    if (responseCode == 200) {

                        String response = "";
                        result = null;
                        String token, task;
                        Scanner scanner = new Scanner(connection.getInputStream());
                        while (scanner.hasNextLine()) {
                            response += scanner.nextLine();
                            response += "\n";
                        }
                        scanner.close();
                        //--------
                        //System.out.println("-->Account page: getTimestamp >> " + response);
                        try {

                            JSONObject obj = new JSONObject(response);
                            token = obj.keys().next();
                            result = obj.getString(token);
                            System.out.println("--> Account page:" + task_id + "<< getTimestamp result>> " + result );

                            add_Timestamp(student_id, task_id, result);
                         } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Snackbar.make(bt_status, "Retrieving Timestamp!", Snackbar.LENGTH_LONG).show();
                        System.out.println("-->Login page: No Timestamp in the DB" + task_id);
                    }
                    //do exception handling here
                } catch (Exception ex) {

                    // Snack Bar to show unsuccessful message that record is wrong
                    Snackbar.make(bt_status, "REST API Failed" + ex, Snackbar.LENGTH_LONG).show();
                    ex.printStackTrace();
                }

            }
        });
        thread.start();

    }
    // -- save Timestamp into the SQLite database
    private void add_Timestamp(String sID, String tastID, String timestamp) {
        TimestampDBHelper tsDBHelper;
        Timestamp ts_new;

        tsDBHelper = new TimestampDBHelper(this);

        ts_new = new Timestamp();
        String priKey = sID + tastID;
        ts_new.setStudentID(priKey);
        ts_new.setAssmntCode(tastID);
        ts_new.setTimestamp(timestamp);

        if (tsDBHelper.isExist(priKey, tastID)){
            tsDBHelper.updateTimestamp(ts_new);
            System.out.println("--> Account page: isExist " + priKey);
        }else {
            tsDBHelper.addTimestamp(ts_new);
            System.out.println("--> Account page: add_Timestamp " + priKey);
        }
    }
    private void clearTimestamp() {
        TimestampDBHelper tsDBHelper;
        tsDBHelper = new TimestampDBHelper(this);

        tsDBHelper.deleteAll();

            System.out.println("--> Account page: delete all timestamp");

        }
}