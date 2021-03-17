package com.example.cnc.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.supporters.InputValidation;
import com.example.cnc.supporters.User;
import com.google.android.material.snackbar.Snackbar;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by NyNguyen on Feb 6, 2021
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    private final AppCompatActivity activity = RegisterActivity.this;

    private RelativeLayout registerView;

    private TextView StudentID;
    private TextView Email;
    private TextView Password;
    private TextView ConfirmPassword;

    private EditText textStudentID;
    private EditText textEmail;
    private EditText textPassword;
    private EditText textConfirmPassword;

    private Button ButtonRegister;
    private TextView LoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getSupportActionBar().hide();
        // getActionBar().hide();

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

        registerView = (RelativeLayout) findViewById(R.id.activity_login);

        StudentID = (TextView) findViewById(R.id.StudentID);
        Email = (TextView) findViewById(R.id.Email);
        Password = (TextView) findViewById(R.id.Password);
        ConfirmPassword = (TextView) findViewById(R.id.ConfirmPassword);

        textStudentID = (EditText) findViewById(R.id.textStudentID);
        textEmail = (EditText) findViewById(R.id.textEmail);
        textPassword = (EditText) findViewById(R.id.textPassword);
        textConfirmPassword = (EditText) findViewById(R.id.textConfirmPassword);

        ButtonRegister = (Button) findViewById(R.id.ButtonRegister);

        LoginLink = (TextView) findViewById(R.id.LoginLink);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        ButtonRegister.setOnClickListener(this);
        LoginLink.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
        databaseHelper.getAllUser();
    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ButtonRegister:
                doRegister();
                // Navigate to MainActivity
               /* Intent intentGoTo = new Intent(getApplicationContext(), GoToActivity.class);
                startActivity(intentGoTo);*/
                break;

            case R.id.LoginLink:
                // Navigate to LoginActivity
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);
                //  finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to database
     */
    private void doRegister() {
        if (!inputValidation.isFilled(textStudentID, StudentID, getString(R.string.error_message_studentID))) {
            return;
        }
        if (!inputValidation.isEmail(textEmail, Email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isFilled(textPassword, Password, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputMatches(textPassword, textConfirmPassword,
                ConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }


        // Create a HTTP post to http://192.168.200.2/register
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    String uri_params;
                    uri_params = "student_id=" + textStudentID.getText().toString().trim();
                    uri_params += "&student_email=" + textEmail.getText().toString().trim();
                    uri_params += "&student_password=" + textPassword.getText().toString().trim();
                    String rest_url = getString(R.string.rest_url) + "register/?";
                    //HttpURLConnection connection = (HttpURLConnection) new URL("http://10.0.2.2:8181/api/register/?" + uri_params).openConnection();
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

                        // Snack Bar to show success message that record saved successfully
                        Snackbar.make(ButtonRegister, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
                        // passing user info into sqlite:
                        user.setStudentID(textStudentID.getText().toString().trim());
                        user.setEmail(textEmail.getText().toString().trim());
                        user.setPassword(textPassword.getText().toString().trim());// we don't need store password in sqlite
                        databaseHelper.addUser(user);

                        Intent intentVerification = new Intent(getApplicationContext(), VerificationActivity.class);
                        intentVerification.putExtra("EXTRA_RESET_PASSWORD", false);
                        //pass student_email and student_password
                        intentVerification.putExtra("EXTRA_STUDENT_EMAIL", textEmail.getText().toString().trim());
                        //intentVerification.putExtra("EXTRA_STUDENT_PASSWORD", textPassword.getText().toString().trim());

                        emptyInputEditText();
                        startActivity(intentVerification);

                        //return response;
                    } else if (responseCode == 409) {
                        // Snack Bar to show error message that record already exists (either email exists, or studentID exists)
                        Snackbar.make(ButtonRegister, getString(R.string.unsuccess_message), Snackbar.LENGTH_LONG).show();

                    } else {
                        // Snack Bar to show message that record is wrong
                        Snackbar.make(ButtonRegister, "Error" + connection.getResponseMessage() , Snackbar.LENGTH_LONG).show();
                    }
                    //do exception handling here
                } catch (Exception ex) {

                    // Snack Bar to show message that record is wrong
                    Snackbar.make(ButtonRegister, "REST API Failed" + ex, Snackbar.LENGTH_LONG).show();
                    ex.printStackTrace();
                }

            }
        });

        thread.start();

    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textStudentID.setText(null);
        textEmail.setText(null);
        textPassword.setText(null);
        textConfirmPassword.setText(null);
    }


}
