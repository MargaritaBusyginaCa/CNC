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
import com.example.cnc.submit.SubmitActivity1;
import com.example.cnc.supporters.InputValidation;
import com.example.cnc.supporters.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

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
