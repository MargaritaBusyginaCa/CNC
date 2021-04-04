package com.example.cnc.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.orientation.AlertExerciseNoSubmitActivity;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.sql.TimestampDBHelper;
import com.example.cnc.submit.SubmitChoiceActivity;
import com.example.cnc.supporters.Timestamp;
import com.example.cnc.supporters.User;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AdminEmailActivity extends AppCompatActivity{
    private final AppCompatActivity activity = AdminEmailActivity.this;
    Button bt_changeEmail, bt_update, bt_cancel;
    String rece_id = "000000";
    String prof_pwd = "prof";
    String email_def, update_email;
    String prof_email;
    private DatabaseHelper databaseHelper;
    private User user;
    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_email);

        Intent intent = getIntent();
        prof_email = intent.getStringExtra("EMAIL");

        init();
    }

    private void init() {


        TextView typedemail = findViewById(R.id.admin_editEmail);
        typedemail.setText(prof_email);

        //--- Update ---
        bt_update = findViewById(R.id.updateButton);
        bt_update.setOnClickListener(click -> {
            EditText edit_email = (EditText) findViewById(R.id.admin_editEmail);
            update_email = edit_email.getText().toString().trim();
            updateEmail();
            System.out.println("-->AdminEmail page: line60 " + update_email);
            updateEmailSQLite(rece_id, update_email, prof_pwd);

        });

        //--- No submit ---
        bt_cancel = findViewById(R.id.cancel);
        bt_cancel.setOnClickListener(click->{
            Intent intent=new Intent(this, AdminActivity.class);
            startActivity(intent);
        });



    }

    private void updateEmail() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    String uri_params;
                    uri_params = "student_id=" + rece_id;
                    uri_params += "&student_email=" + update_email;

                    String rest_url = getString(R.string.rest_url) + "update_email/?";
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
                        Snackbar.make(bt_update, "The Email has been updated.", Snackbar.LENGTH_LONG).show();
                        System.out.println("-->AdminEmail page - updated Email : response " + response);

                        String jsonString = response;
                        try {

                            JSONObject obj = new JSONObject(response);
                            token = obj.keys().next();
                            String result = obj.getString(token);
                            System.out.println("--> AdminEmail page - updated Email : result " + result);

                            Intent intentAccount = new Intent(getApplicationContext(), AdminActivity.class);
                            startActivity(intentAccount);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        // Snack Bar to show success message that record is wrong
                        Snackbar.make(bt_update, "Error!! unable to updated", Snackbar.LENGTH_LONG).show();
                    }
                    //do exception handling here
                } catch (Exception ex) {

                    // Snack Bar to show unsuccessful message that record is wrong
                    Snackbar.make(bt_update, "REST API Failed" + ex, Snackbar.LENGTH_LONG).show();
                    ex.printStackTrace();
                }

            }
        });
        thread.start();

    }
    // -- save Timestamp into the SQLite database
    private void updateEmailSQLite(String id, String email, String pwd) {
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
        databaseHelper.getAllUser();

        // passing user info into sqlite:
        user.setStudentID(id);
        user.setEmail(email);
        user.setPassword(pwd);
        databaseHelper.updateUser(user);
    }

}
