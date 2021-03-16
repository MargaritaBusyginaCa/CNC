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
import com.example.cnc.loginPage.AccountActivity;
import com.example.cnc.main.MainActivity;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.supporters.InputValidation;
import com.example.cnc.supporters.User;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;

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

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    public List<User> users = new ArrayList<>();
    String email, studentID;
    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // getSupportActionBar().hide();
        // getActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

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

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        ButtonLogin.setOnClickListener(this);
        RegisterLink.setOnClickListener(this);
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
            case R.id.ButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.RegisterLink:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }



    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {

        if (!inputValidation.isEmail(textEmail, Email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isFilled(textPassword, Password, getString(R.string.error_message_password))) {
            return;
        }
        //-------------------------------------OLD version
        if (databaseHelper.checkUser(textEmail.getText().toString().trim()
                , textPassword.getText().toString().trim())){
            Intent intentAccount = new Intent(getApplicationContext(), AccountActivity.class);
            //intentAccount.putExtra("EMAIL",textEmail.getText().toString().trim());
            // retrieve student ID from database
            email = textEmail.getText().toString().trim();
            studentID = getStudentID(email);
            intentAccount.putExtra("ID", studentID);
            emptyInputEditText();
            startActivity(intentAccount);
    //------------------------------------------ NEW version
        /*if (false && databaseHelper.checkUser(textEmail.getText().toString().trim()
                , textPassword.getText().toString().trim())) {
            Intent intentAccount = new Intent(getApplicationContext(), AccountActivity.class);
            intentAccount.putExtra("EMAIL", textEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(intentAccount);
        } */
/*
        if (true) {
            // Instead, create a HTTP post to http://192.168.200.2/register
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        String uri_params;
                        uri_params = "student_email=" + textEmail.getText().toString().trim();
                        uri_params += "&student_password=" + textPassword.getText().toString().trim();
                        HttpURLConnection connection = (HttpURLConnection) new URL("http://10.0.2.2:8181/api/login/?" + uri_params).openConnection();
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
                            Intent intentAccount = new Intent(getApplicationContext(), AccountActivity.class);
                            //intentAccount.putExtra("EMAIL", textEmail.getText().toString().trim());
                            // retrieve student ID from database
                            email = textEmail.getText().toString().trim();
                            studentID = getStudentID(email);
                            intentAccount.putExtra("ID", studentID);

                            emptyInputEditText();
                            startActivity(intentAccount);
                            //return response;
                        } else {
                            // Snack Bar to show success message that record is wrong
                            Snackbar.make(ButtonLogin, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        //do exception handling here
                        // Snack Bar to show success message that record is wrong
                        Snackbar.make(ButtonLogin, "REST API Failed" + ex, Snackbar.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }

                }
            });
            thread.start();

 */

        }else {
            // Snack Bar to show success message that record is wrong
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

    //--- retrieve student ID from database ---  by Lai Shan

    private String getStudentID(String stuEmail) {
        databaseHelper = new DatabaseHelper(this);
        users = databaseHelper.getAllUser();
        String stuID;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equalsIgnoreCase(email)) {
                index = i;
                break;
            }
        }
        stuID = users.get(index).getStudentID();
        return stuID;
    }


}
