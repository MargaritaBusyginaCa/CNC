package com.example.cnc.loginPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cnc.R;
import com.example.cnc.assignment.MainAssignmentActivity;
import com.example.cnc.assignment.WarningNoAccessActivity;
import com.example.cnc.main.MainActivity;
import com.example.cnc.manual.ManualActivity;
import com.example.cnc.orientation.OrientationActivity;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.submit.SubmitActivity;
import com.example.cnc.supporters.User;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    public List<User> users = new ArrayList<>();
    Button bt_orientation, bt_assignment, bt_submit, bt_manual, bt_logout;
    String email, studentID;
    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Intent intentEmail = getIntent();
        studentID = intentEmail.getStringExtra("ID");

        //--- retrieve student ID from database ---  by Lai Shan
/*
        dbHelper = new DatabaseHelper(this);
        users = dbHelper.getAllUser();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equalsIgnoreCase(email)) {
                index = i;
                break;
            }
        }
        studentID = users.get(index).getStudentID();

 */
        //-------------

        TextView typedEmail = findViewById(R.id.editTextEmail);
        typedEmail.setText(studentID);

        // Manual
        bt_manual = findViewById(R.id.manual_bt);
        bt_manual.setOnClickListener(click -> {
            Intent intent = new Intent(this, ManualActivity.class);
            intent.putExtra("ID", studentID);
            startActivity(intent);
        });

        // Orientation
        bt_orientation = findViewById(R.id.orientation_bt);
        bt_orientation.setOnClickListener(click -> {
            Intent intent = new Intent(this, OrientationActivity.class);
            intent.putExtra("ID", studentID);
            startActivity(intent);
        });

        // Checklist and Assignment
        bt_assignment = findViewById(R.id.assignments_bt);
        bt_assignment.setOnClickListener(click -> {
            Intent intent = new Intent(this, MainAssignmentActivity.class);
            intent.putExtra("ID", studentID);
            startActivity(intent);
        });

        //The submission section will be removed from the main menu!
/*
        bt_submit = findViewById(R.id.submission_bt);
        bt_submit.setOnClickListener(click -> {
            Intent intent = new Intent(this, SubmitActivity.class);
            intent.putExtra("ID", studentID);
            startActivity(intent);
        });
*/
        // logout from the App
        bt_logout = findViewById(R.id.logout_bt);
        bt_logout.setOnClickListener(click -> {
            Toast.makeText(this, "Bye!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });




    }



}