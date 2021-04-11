package com.example.cnc.loginPage;
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
import com.example.cnc.admin.AdminEmailActivity;
import com.example.cnc.admin.AdminReportActivity;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.sql.TimestampDBHelper;
import com.example.cnc.submit.SubmitActivity;
import com.example.cnc.supporters.InputValidation;
import com.example.cnc.supporters.Timestamp;
import com.example.cnc.supporters.User;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by NyNguyen on Feb 6, 2021
 */


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private RelativeLayout loginView;

    private TextView Email;
    private TextView Password;

    private EditText textEmail;
    private EditText textPassword;

    private Button ButtonLogin;
    private TextView RegisterLink;

    private TextView ResetPasswordLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private TimestampDBHelper tsDBHelper;


    public static String studentID_def;
    public static String email_def;
    public List<User> users = new ArrayList<>();
    //String email_def;
    int index = 0;
    int size;
    String studID, result;
    String[] task= {"01","11","12","13","21","22","23"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListeners();
        initObjects();
   }


    public static String User_Email = "";
    public static String User_Password = "";

    /**
     * This method is to initialize views
     */
    private void initViews() {

        loginView = (RelativeLayout) findViewById(R.id.activity_login);

        Email = (TextView) findViewById(R.id.Email);
        Password = (TextView) findViewById(R.id.Password);

        textEmail = (EditText) findViewById(R.id.textEmail);
        textPassword = (EditText) findViewById(R.id.textPassword);

        ButtonLogin = (Button) findViewById(R.id.ButtonLogin);

        RegisterLink = (TextView) findViewById(R.id.RegisterLink);

        ResetPasswordLink = (TextView) findViewById(R.id.ResetPasswordLink);


    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        ButtonLogin.setOnClickListener(this);
        RegisterLink.setOnClickListener(this);
        ResetPasswordLink.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        tsDBHelper = new TimestampDBHelper(this);
        inputValidation = new InputValidation(activity);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        size = 0;

        switch (v.getId()) {
            case R.id.ButtonLogin:

                email_def = textEmail.getText().toString().trim();
                getStudentID(email_def);

                doVerify();

                break;
            case R.id.RegisterLink:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
            // Navigate to ResetPasswordActivity
            case R.id.ResetPasswordLink:
                resetPassword();
                break;
        }
    }



    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void doVerify() {

        if (!inputValidation.isEmail(textEmail, Email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isFilled(textPassword, Password, getString(R.string.error_message_password))) {
            return;
        }


        if (true) {
            // Instead, create a HTTP post to http://192.168.200.2/register
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        String uri_params;

                        uri_params = "student_email=" + textEmail.getText().toString().trim();
                        uri_params += "&student_password=" + textPassword.getText().toString().trim();
                        String rest_url = getString(R.string.rest_url) + "login/?";
                        //HttpURLConnection connection = (HttpURLConnection) new URL("http://10.0.2.2:8181/api/login/?" + uri_params).openConnection();
                        HttpURLConnection connection = (HttpURLConnection) new URL(rest_url + uri_params).openConnection();
                        connection.setRequestMethod("GET");

                        int responseCode = connection.getResponseCode();

                        if (responseCode == 200) {
                            String response = "";
                            Scanner scanner = new Scanner(connection.getInputStream());
                            while (scanner.hasNextLine()) {
                                response += scanner.nextLine();
                                response += "\n";
                            }
                            scanner.close();
                            Snackbar.make(ButtonLogin, "OK REST", Snackbar.LENGTH_LONG).show();

                            clearTimestamp();

                            studID = studentID_def;
                            loadTimestamp(studID);

                            Intent intentAccount = new Intent(getApplicationContext(), AccountActivity.class);
                            startActivity(intentAccount);

                        //    email_def = textEmail.getText().toString().trim();
                        //    studentID_def = getStudentID(email_def);
                        //    intentAccount.putExtra("ID", studentID_def);
                            //--------------
                            emptyInputEditText();
                        //    startActivity(intentAccount);
                            //return response;
                        } else if(responseCode == 302) {
                            /* Activation is required.
                             Snack Bar to show success message that record is wrong

                             Set public variable for Activation Activity to access*/
                            User_Email = textEmail.getText().toString().trim();
                            User_Password = textPassword.getText().toString().trim();

                            Snackbar.make(ButtonLogin, "OK GOTO ACTIVATION", Snackbar.LENGTH_LONG).show();

                            Intent intentActivation = new Intent(getApplicationContext(), VerificationActivity.class);

                            //pass student_email and student_password
                            intentActivation.putExtra("EXTRA_STUDENT_EMAIL", textEmail.getText().toString().trim());

                            startActivity(intentActivation);
                        } else {
                            // Snack Bar to show success message that record is wrong
                            Snackbar.make(ButtonLogin, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
                        }
                        //do exception handling here
                    } catch (Exception ex) {

                        // Snack Bar to show unsuccessful message that record is wrong
                        Snackbar.make(ButtonLogin, "REST API Failed" + ex, Snackbar.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }

                }
            });
            thread.start();
        }else {
            // Snack Bar to show unsuccessful message that record is wrong
            Snackbar.make(ButtonLogin, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    private void resetPassword() {
        //Make sure email address is entered
        if (!inputValidation.isEmail(textEmail, Email, getString(R.string.error_message_email))) {
            Snackbar.make(ButtonLogin, "Missing/invalid email address. Please enter your email address", Snackbar.LENGTH_LONG).show();
            return;
        }

        //Empty password field as we don't need this for password reset
        textPassword.setText(null);
        if (true) {
            /*call API to check email address. If found, then 302 is returned. Else, 404 is returned. No 2XX will be ever returned
             Instead, create a HTTP post to http://192.168.200.2/register*/
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        String uri_params;

                        uri_params = "student_email=" + textEmail.getText().toString().trim();
                        uri_params += "&student_password=";
                        uri_params += "&reset_password=true";
                        String rest_url = getString(R.string.rest_url) + "login/?";
                        //HttpURLConnection connection = (HttpURLConnection) new URL("http://10.0.2.2:8181/api/login/?" + uri_params).openConnection();
                        HttpURLConnection connection = (HttpURLConnection) new URL(rest_url + uri_params).openConnection();
                        connection.setRequestMethod("GET");

                        int responseCode = connection.getResponseCode();

                        if(responseCode == 302) {
                           /*  Activation is required.
                             Snack Bar to show message that record is wrong

                             Set public variable for Activation Activity to access*/
                            User_Email = textEmail.getText().toString().trim();
                            User_Password = textPassword.getText().toString().trim();

                            Snackbar.make(ButtonLogin, "OK GOTO ACTIVATION", Snackbar.LENGTH_LONG).show();
                            // Navigate to RegisterActivity
                            Intent intentVerification = new Intent(getApplicationContext(), VerificationActivity.class);
                            intentVerification.putExtra("EXTRA_RESET_PASSWORD", true);
                            //pass student_email. Student password is blank
                            intentVerification.putExtra("EXTRA_STUDENT_EMAIL", textEmail.getText().toString().trim());

                            emptyInputEditText();
                            startActivity(intentVerification);

                        } else {
                            // Snack Bar to show message that record is wrong
                            Snackbar.make(ButtonLogin, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
                        }
                        //do exception handling here
                    } catch (Exception ex) {
                        // Snack Bar to show message that record is wrong
                        Snackbar.make(ButtonLogin, "REST API Failed" + ex, Snackbar.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }

                }
            });
            thread.start();
        }else {
            // Snack Bar to show message that record is wrong
            Snackbar.make(ButtonLogin, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }

    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textEmail.setText(null);
        textPassword.setText(null);
    }

    //--------------------------------------------------
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
                        //System.out.println("-->Account page: getTimestamp >> " + response);
                        try {

                            JSONObject obj = new JSONObject(response);
                            token = obj.keys().next();
                            result = obj.getString(token);
                            System.out.println("--> Login page:" + task_id + "<< getTimestamp result>> " + result );

                          add_Timestamp(student_id, task_id, result);

                         } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                       //Snackbar.make(ButtonLogin, "", Snackbar.LENGTH_LONG).show();
                    }
                    //do exception handling here
                } catch (Exception ex) {

                    // Snack Bar to show unsuccessful message that record is wrong
                    Snackbar.make(ButtonLogin, "REST API Failed - getTimestamp" + ex, Snackbar.LENGTH_LONG).show();
                    ex.printStackTrace();
                }

            }
        });
        thread.start();

    }

    // -- clear Timestamp into the SQLite database
    private void clearTimestamp() {
         tsDBHelper.deleteAll();

        System.out.println("--> Login page: delete all timestamp");

    }
    //------------------------

    // -- save Timestamp into the SQLite database
    private void add_Timestamp(String sID, String tastID, String timestamp) {
        Timestamp ts_new;

        ts_new = new Timestamp();
        String priKey = sID + tastID;
        ts_new.setStudentID(priKey);
        ts_new.setAssmntCode(tastID);
        ts_new.setTimestamp(timestamp);
            tsDBHelper.addTimestamp(ts_new);
            System.out.println("--> Login page: add_Timestamp " + priKey);
        }

    //--- retrieve student ID from database
        private class ServerGet extends AsyncTask< String, Integer, String> {

            public String doInBackground(String... args) {

                try{
                    HttpURLConnection connection = (HttpURLConnection) new URL(args[0] ).openConnection();
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
                            studentID_def = obj.optString("student_id");
                            System.out.println("-->Login page: getStudentID " + studentID_def);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return "done";
                    }
                    else
                    {
                        return (getString(R.string.error_email));
                    }

                }catch(Exception ex){
                    return "Error!!! Unable to get the student ID in server.";
                }
            }
        }

    private void getStudentID(String stuEmail) {

        String uri_params;
        uri_params = "student_email=" + stuEmail;
        ServerGet req = new ServerGet();
        req.execute(getString(R.string.rest_url) + "get_student_id/?" + uri_params);
    }

    private void loadTimestamp(String id){

        //--Load timestamps from server--
        for (String i : task) {
            getTimestamp(id, i);
          //
            waitforServerRepsonese();
            //
        }
    }
    private void waitforServerRepsonese() {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
