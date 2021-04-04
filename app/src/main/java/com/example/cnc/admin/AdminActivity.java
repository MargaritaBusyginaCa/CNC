package com.example.cnc.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.loginPage.AccountActivity;
import com.example.cnc.loginPage.RegisterActivity;
import com.example.cnc.loginPage.VerificationActivity;
import com.example.cnc.orientation.AlertExerciseNoSubmitActivity;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.submit.SubmitActivity;
import com.example.cnc.supporters.InputValidation;
import com.example.cnc.supporters.User;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static com.example.cnc.loginPage.LoginActivity.studentID_def;



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

        });

        bt_exit=findViewById(R.id.exit);
        bt_exit.setOnClickListener(click->{
            Intent intent=new Intent(this, AccountActivity.class);
            startActivity(intent);
        });
    }



    //-------------Get email----
    private void getEmail() {
        // Instead, create a HTTP post to http://192.168.200.2/register
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    String uri_params;
                    uri_params = "student_id=" + rece_id;
                    String rest_url = getString(R.string.rest_url) + "get_email/?";
                    //HttpURLConnection connection = (HttpURLConnection) new URL("http://10.0.2.2:8181/api/admin_get_email/?" + uri_params).openConnection();
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

   /*
    //-----------------------
    private class ServerGet extends AsyncTask< String, Integer, String> {

        public String doInBackground(String... args) {

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(args[0]).openConnection();
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

                    try {

                        JSONObject obj = new JSONObject(response);
                        token = obj.keys().next();
                        prof_email = obj.getString(token);
                        System.out.println("-->Admin page: Prof email " + prof_email);

                        Snackbar.make(bt_changeEmail, "OK REST", Snackbar.LENGTH_LONG).show();

                        Intent intentAccount = new Intent(getApplicationContext(), AdminEmailActivity.class);
                        intentAccount.putExtra("EMAIL", prof_email);
                        startActivity(intentAccount);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return "done";
                } else {
                    Snackbar.make(bt_changeEmail, getString(R.string.invalid_student_id), Snackbar.LENGTH_LONG).show();
                    return getString(R.string.invalid_student_id);
                }

            } catch (Exception ex) {
                Snackbar.make(bt_changeEmail, "Error!!! Unable to get the email from server.", Snackbar.LENGTH_LONG).show();
                return "Error!";
            }
        }
    }

    private void getEmail(String id){
        String uri_params;
        uri_params = "student_id=" + id;

        ServerGet req = new ServerGet(); //creates a background thread
        req.execute(getString(R.string.rest_url) + "get_email/?"+uri_params);


    }

    */
}