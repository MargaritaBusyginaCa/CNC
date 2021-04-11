package com.example.cnc.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnc.R;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.submit.SubmitActivity;
import com.example.cnc.supporters.User;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.cnc.loginPage.LoginActivity.studentID_def;


public class AdminReportActivity extends AppCompatActivity{
    private final AppCompatActivity activity = AdminReportActivity.this;
    Button bt_exit;
    String ori, a1, a2, totalStud;
    DatabaseHelper databaseHelper;
    List<User> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_report);

        Intent intent = getIntent();
        ori = intent.getStringExtra("ORI");
        a1 = intent.getStringExtra("A1");
        a2 = intent.getStringExtra("A2");

        init();
    }

    private void init() {

        //int count = getCount();
        getCount();
        //TextView typedCount = findViewById(R.id.totalNumber);
        //typedCount.setText(String.valueOf(count));
        TextView typedOri = findViewById(R.id.ori_timestamp);
        typedOri.setText(ori);
        TextView typeda1 = findViewById(R.id.a1_timestamp);
        typeda1.setText(a1);
        TextView typeda2 = findViewById(R.id.a2_timestamp);
        typeda2.setText(a2);

        //--- Exit ---
        bt_exit = findViewById(R.id.exit);
        bt_exit.setOnClickListener(click->{
            Intent intent=new Intent(this, AdminActivity.class);
            startActivity(intent);
        });
    }

    //total students
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
                        String numofStud = obj.optString("res");
                        System.out.println("-->Report page: numofStud " + numofStud);
                        totalStud = numofStud.substring( 2, numofStud.length() - 2 );
                        int actualNum = (Integer.parseInt(totalStud))-2;
                        TextView typedCount = findViewById(R.id.totalNumber);
                        typedCount.setText(String.valueOf(actualNum));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return "done";
                }
                else
                {
                    return (getString(R.string.invalid_student_id));
                }

            }catch(Exception ex){
                return "Error!!! Unable to get the email from server.";
            }
        }
    }

    private void getCount() {{

        ServerGet req = new ServerGet(); //creates a background thread
        req.execute(getString(R.string.rest_url) + "total_student/?");
    }

    }


}
