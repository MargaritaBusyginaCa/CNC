package com.example.cnc.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
 * Created by NyNguyen on Match 12, 2021
 */


public class VerificationActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = VerificationActivity.this;

    private RelativeLayout VerificationView;

    private TextView textVerificationCode;
    //private TextView Password;

    private Button ButtonActivate;
    TextView ResendVerificationCode;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    private Boolean Is_Reset_Password = false;  //True if parent page is calling this page for resetting password

    private String User_Email;
    //private String User_Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        initViews();
        initListeners();
        initObjects();

        // Check if this is for reset_password
        Bundle extras = getIntent().getExtras();
        Is_Reset_Password = extras.getBoolean("EXTRA_RESET_PASSWORD");

        //Display pop up message
        Snackbar.make(ButtonActivate, "A verification code has been sent to your email. Please check your mailbox and enter it here", Snackbar.LENGTH_LONG).show();

        //send confirmation code on form load
        User_Email = extras.getString("EXTRA_STUDENT_EMAIL");
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        VerificationView = (RelativeLayout) findViewById(R.id.activity_verification);
        textVerificationCode = (TextView) findViewById(R.id.textVerificationCode);
        ButtonActivate = (Button) findViewById(R.id.ButtonResetPassword);
        ResendVerificationCode = (TextView) findViewById(R.id.ResendVerificationCode);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        ButtonActivate.setOnClickListener(this);
        ResendVerificationCode.setOnClickListener(this);
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
            case R.id.ResendVerificationCode:
                sendVerificationCode();
                break;
        }
    }



    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void doVerify() {

        if (true) {
            // Instead, create a HTTP post to http://192.168.200.2/register
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        String uri_params;
                        String Verification_Code;
                        Verification_Code = textVerificationCode.getText().toString().trim();
                        uri_params = "student_email=" + User_Email;
                        uri_params += "&student_verification_code=" + Verification_Code;
                        String rest_url = getString(R.string.rest_url) + "verify/?";
                        //HttpURLConnection connection = (HttpURLConnection) new URL("http://10.0.2.2:8181/api/verify/?" + uri_params).openConnection();
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
                            Snackbar.make(ButtonActivate, "OK REST", Snackbar.LENGTH_LONG).show();
                            emptyInputEditText();
                            // if reset_password, then open a reset password page
                            if (Is_Reset_Password) {
                                Intent intentResetPassword = new Intent(getApplicationContext(), ResetPasswordActivity.class);

                                //pass email to Reset Password Page
                                intentResetPassword.putExtra("EXTRA_STUDENT_EMAIL", User_Email);
                                intentResetPassword.putExtra("EXTRA_STUDENT_VERIFICATION_CODE", Verification_Code);
                                startActivity(intentResetPassword);

                            } else {
                                // else, log user in and display Account Activity page
                                Intent intentAccount = new Intent(getApplicationContext(), AccountActivity.class);
                                startActivity(intentAccount);

                                //return response;
                            }
                        } else {
                            // Snack Bar to show message that record is wrong
                            Snackbar.make(ButtonActivate, "Incorrect Verification Code", Snackbar.LENGTH_LONG).show();
                        }
                     //do exception handling here
                    } catch (Exception ex) {
                        // Snack Bar to show success message that record is wrong
                        Snackbar.make(ButtonActivate, "REST API Failed" + ex, Snackbar.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }

                }
            });
            thread.start();
        }else {
            // Snack Bar to show message that record is wrong
            Snackbar.make(ButtonActivate, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    private void sendVerificationCode() {

        if (true) {
            // Instead, create a HTTP post to http://192.168.200.2/register
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        String uri_params;
                        uri_params = "student_email=" + User_Email;
                        uri_params += "&resend_verification=true";
                        String rest_url = getString(R.string.rest_url) + "verify/?";
                        //HttpURLConnection connection = (HttpURLConnection) new URL("http://10.0.2.2:8181/api/verify/?" + uri_params).openConnection();
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
                            Snackbar.make(ButtonActivate, "Verification Code sent to " + LoginActivity.User_Email, Snackbar.LENGTH_LONG).show();

                        } else {
                            // Snack Bar to show success message that record is wrong
                            Snackbar.make(ButtonActivate, "Error occurred.", Snackbar.LENGTH_LONG).show();
                        }
                     //do exception handling here
                    } catch (Exception ex) {
                        // Snack Bar to show message that record is wrong
                        Snackbar.make(ButtonActivate, "REST API Failed" + ex, Snackbar.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }

                }
            });
            thread.start();
        }else {
            // Snack Bar to show message that record is wrong
            Snackbar.make(ButtonActivate, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }


    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {

        textVerificationCode.setText(null);
    }
}
