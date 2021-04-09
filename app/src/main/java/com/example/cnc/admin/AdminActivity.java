package com.example.cnc.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.loginPage.AccountActivity;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.submit.SubmitChoiceActivity;
import com.example.cnc.supporters.User;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;
import org.json.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AdminActivity extends AppCompatActivity{
    private final AppCompatActivity activity = AdminActivity.this;
    Button bt_changeEmail, bt_report, bt_exit;
    String rece_id = "000000";
    String prof_email;

    private DatabaseHelper databaseHelper;

    private TextView LoginLink;

    public List<User> users = new ArrayList<>();
    String email_def, db_email;
    String email;

    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        init();
    }

    private void init() {

        bt_changeEmail=findViewById(R.id.admin_getEmail_bt);
        bt_changeEmail.setOnClickListener(click->{
            getEmail();
        });

        bt_report=findViewById(R.id.admin_report_bt);
        bt_report.setOnClickListener(click->{
            countSubmission();

        });

        bt_exit=findViewById(R.id.exit);
        bt_exit.setOnClickListener(click->{
            Intent intent=new Intent(this, AccountActivity.class);
            startActivity(intent);
        });
    }



    //-------------Get email----
    private void getEmail() {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        String uri_params;
                        uri_params = "student_id=" + rece_id;
                        String rest_url = getString(R.string.rest_url) + "get_email/?";
                        HttpURLConnection connection = (HttpURLConnection) new URL(rest_url + uri_params).openConnection();
                        connection.setRequestMethod("GET");

                        int responseCode = connection.getResponseCode();

                        if (responseCode == 200) {

                            String response = "";
                            String token;
                            Scanner scanner = new Scanner(connection.getInputStream());
                            while (scanner.hasNextLine()) {
                                response += scanner.nextLine();
                                response += "\n";
                            }
                            scanner.close();
                            //--------
                            System.out.println("-->Admin page: response " + response);
                            //token = response;

                        String jsonString = response;
                        try {

                            JSONObject obj = new JSONObject(response);
                            token = obj.keys().next();
                            email = obj.getString(token);
                            System.out.println("Admin page: Prof email " + email);

                            Snackbar.make(bt_changeEmail, "OK REST", Snackbar.LENGTH_LONG).show();
                            Intent intentAccount = new Intent(getApplicationContext(), AdminEmailActivity.class);
                            intentAccount.putExtra("EMAIL", email);
                            startActivity(intentAccount);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Snack Bar to show success message that record is wrong
                        Snackbar.make(bt_changeEmail, getString(R.string.invalid_student_id), Snackbar.LENGTH_LONG).show();
                    }
                    //do exception handling here
                } catch (Exception ex) {

                    // Snack Bar to show unsuccessful message that record is wrong
                    Snackbar.make(bt_changeEmail, "REST API Failed" + ex, Snackbar.LENGTH_LONG).show();
                    ex.printStackTrace();
                }

            }
        });
        thread.start();

    }




    //--------------------------------------------------
    //-- Count the submission of Orientation, Assignment 1 and 2
    private void countSubmission() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                   // String uri_params;
                   // uri_params = "task_id=01";
                    String rest_url = getString(R.string.rest_url) + "count_submission/?";
                    HttpURLConnection connection = (HttpURLConnection) new URL(rest_url ).openConnection();
                    connection.setRequestMethod("GET");

                    int responseCode = connection.getResponseCode();

                    if (responseCode == 200) {

                        String response = "";

                        String ori, a1, a2;
                        Scanner scanner = new Scanner(connection.getInputStream());
                        while (scanner.hasNextLine()) {
                            response += scanner.nextLine();
                            response += "\n";
                        }
                        scanner.close();
                        //System.out.println("-->Account page: getTimestamp >> " + response);
                        try {
                            JSONObject object = new JSONObject(response);
                            String value1 = object.optString("res1");
                            String value2 = object.optString("res2");
                            String value3 = object.optString("res3");
                            System.out.println("--> countSubmission:>> " + value1 + " " + value2 + " " + value3 );


                        scanner.close();


                        ori = value1.substring( 2, value1.length() - 2 );
                        a1 = value2.substring( 2, value2.length() - 2 );
                        a2 = value3.substring( 2, value3.length() - 2 );
                            System.out.println("--> countSubmission: ori, a1, a2>> " + ori + " " + a1 + " " + a2 );
                        Intent intent=new Intent(getApplicationContext(), AdminReportActivity.class);
                        intent.putExtra("ORI", ori);
                        intent.putExtra("A1", a1);
                        intent.putExtra("A2", a2);
                        startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Snackbar.make(bt_report, "No any submission!", Snackbar.LENGTH_LONG).show();
                    }
                    //do exception handling here
                } catch (Exception ex) {

                    // Snack Bar to show unsuccessful message that record is wrong
                    Snackbar.make(bt_report, "REST API Failed - Admin " + ex, Snackbar.LENGTH_LONG).show();
                    ex.printStackTrace();
                }

            }
        });
        thread.start();
    }

}