package com.example.cnc.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.supporters.InputValidation;
import com.example.cnc.supporters.User;
import com.google.android.material.snackbar.Snackbar;


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
                postDataToSQLite();
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
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
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

        if (!databaseHelper.checkUser(textEmail.getText().toString().trim())) {

            user.setStudentID(textStudentID.getText().toString().trim());
            user.setEmail(textEmail.getText().toString().trim());
            user.setPassword(textPassword.getText().toString().trim());

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(ButtonRegister, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
           /* // Toast message
            Toast.makeText(getApplicationContext(),"Registration successful", Toast.LENGTH_SHORT).show();*/
            emptyInputEditText();


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(ButtonRegister, getString(R.string.unsuccess_message), Snackbar.LENGTH_LONG).show();
        }


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
