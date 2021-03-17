package com.example.cnc.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.supporters.InputValidation;
import com.google.android.material.snackbar.Snackbar;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by NyNguyen on March 12, 2021
 */


public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = ResetPasswordActivity.this;

    private RelativeLayout ResetPasswordView;

    private TextView resetPassword;
    private TextView resetConfirmPassword;

    private EditText textResetPassword;
    private EditText textResetConfirmPassword;

    private Button ButtonResetPassword;

    private String User_Email;
    private String User_Verification_Code;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
      // getSupportActionBar().hide();
       // getActionBar().hide();

        initViews();
        initListeners();
        initObjects();

        // Check if this is for reset_password
        Bundle extras = getIntent().getExtras();

        //send confirmation code on form load
        User_Email = extras.getString("EXTRA_STUDENT_EMAIL");
        User_Verification_Code = extras.getString("EXTRA_STUDENT_VERIFICATION_CODE");

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        ResetPasswordView = (RelativeLayout) findViewById(R.id.activity_reset_password);

        resetPassword = (TextView) findViewById(R.id.resetPassword);
        resetConfirmPassword = (TextView) findViewById(R.id.resetConfirmPassword);

        textResetPassword = (EditText) findViewById(R.id.textResetPassword);
        textResetConfirmPassword = (EditText) findViewById(R.id.textResetConfirmPassword);


        ButtonResetPassword = (Button) findViewById(R.id.ButtonResetPassword);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        ButtonResetPassword.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ButtonResetPassword:
                doVerify();
                break;

        }
    }



    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void doVerify() {

        if (!inputValidation.isFilled(textResetPassword, resetPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputMatches(textResetPassword, textResetConfirmPassword,
                resetConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }


        if (true) {
            // Instead, create a HTTP post to http://192.168.200.2/register
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        String uri_params;
                        uri_params = "student_email=" + User_Email;
                        uri_params += "&student_verification_code=" + User_Verification_Code;
                        uri_params += "&student_new_password=" + textResetPassword.getText().toString().trim();
                        String rest_url = getString(R.string.rest_url) + "reset/?";
                        //HttpURLConnection connection = (HttpURLConnection) new URL("http://10.0.2.2:8181/api/reset/?" + uri_params).openConnection();
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
                            Snackbar.make(ButtonResetPassword, "OK REST", Snackbar.LENGTH_LONG).show();
                            //Intent intent=new Intent(this, SubmitActivity.class);
                            emptyInputEditText();

                            // Navigate to LoginActivity
                            Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intentLogin);
                            //return response;
                        } else {
                            // Snack Bar to show success message that record is wrong
                            Snackbar.make(ButtonResetPassword, "Incorrect Activation Code", Snackbar.LENGTH_LONG).show();
                        }
                     //do exception handling here
                    } catch (Exception ex) {
                        // Snack Bar to show message that record is wrong
                        Snackbar.make(ButtonResetPassword, "REST API Failed" + ex, Snackbar.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }

                }
            });
            thread.start();
        }else {
            // Snack Bar to show message that record is wrong
            Snackbar.make(ButtonResetPassword, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }


    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {

        textResetPassword.setText(null);
        textResetConfirmPassword.setText(null);
    }



}
