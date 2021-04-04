package com.example.cnc.submit;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cnc.R;
import com.example.cnc.admin.AdminEmailActivity;
import com.example.cnc.loginPage.AccountActivity;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.sql.TimestampDBHelper;
import com.example.cnc.supporters.Timestamp;
import com.example.cnc.supporters.User;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.cnc.loginPage.LoginActivity.studentID_def;


public class SubmitActivity extends AppCompatActivity {

    //Identified intent request code for select pictures
    private static final int PICK_IMAGE = 100;
    //Identified intent request code for send email
    private static final int SEND_EMAIL = 200;
    //stores submit button
    private Button submitBtn;
    //use to manage listView and data source
    MyListAdapter myAdapter;
    //save the selected pictures
    private ArrayList<Uri> elements = new ArrayList<>();
    //variable to store related information
    String title, studentID, ck_timestamp,s_timestamp,e_timestamp,desc,code_ck,code_s,code_e;
    String assignmentId;
    String prof_email;
    //Database helper to query students database
    private DatabaseHelper dbHelper;
    //Database helper to query timestamp database
    private TimestampDBHelper tsDBHelper;
    //current timestamp
    Timestamp ts_new;

    @Override
    //display submit page
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);  //from layout to find listView
        loadData();  //find the data

        //---- Get Prof email
        String prof_id = "0";
        getEmail(prof_id);

        GridView myList = findViewById(R.id.theListView);
        myAdapter = new MyListAdapter();  //listView connect with adapter (adapter manage listView)
        myList.setAdapter(myAdapter);

        //--- Select photos ---
        Button SelectPhoto = findViewById(R.id.photos);  //find select photo button
        SelectPhoto.setOnClickListener(click->{  //click on select photo button
            Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);  //go to gallery to get photos
            gallery.setType("image/*");
            gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);  //can choose multiple photos
            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
        });

        //--- No submit ---
        Button noSubmitBtn = findViewById(R.id.cancelBtn);  //find cancel button
        noSubmitBtn.setOnClickListener(click->{  //click cancel button
            Intent intent=new Intent(this, AccountActivity.class);  //back to accountActivity(main page)
            startActivity(intent);
        });

        submitBtn = findViewById(R.id.submitBtn);  //find submit button
        submitBtn.setEnabled(false);  //if there has not pictures in the submit page, can not click submit button
        submitBtn.setOnClickListener(click->{  //click submit button


            final Intent emailIntent = new Intent (Intent.ACTION_SEND_MULTIPLE);//intent send email
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { prof_email});//default to email address
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, title); //email subject
            emailIntent.putExtra(Intent.EXTRA_STREAM,elements); //email attachment
            // Use task and timestamp information to create email body
            String emailBody="Student ID: " + studentID+"\n";

            // Copied from SubmitActivity1 to update timestamp database
            if (code_ck == null){
                add_Timestamp(studentID, code_e , e_timestamp);  //
                UpdateServer(studentID,assignmentId,code_e,e_timestamp);
            }else {
                add_Timestamp(studentID, code_ck, ck_timestamp);
                UpdateServer(studentID,assignmentId,code_ck,ck_timestamp);
                add_Timestamp(studentID, code_s, s_timestamp);
                UpdateServer(studentID,assignmentId,code_s,s_timestamp);
                add_Timestamp(studentID, code_e, e_timestamp);
                UpdateServer(studentID,assignmentId,code_e,e_timestamp);
                emailBody+="Check list time: " +    ck_timestamp +"\n";
                emailBody+="Start time: " +    s_timestamp +"\n";
            }
            Toast.makeText(getApplicationContext(),"The timestamps have been saved",Toast.LENGTH_LONG).show();


            // End of timestamp database
            emailBody+="End time: " +    e_timestamp +"\n";
            emailBody+="Status: " +    desc+ "\n";
            EditText commentText = findViewById(R.id.submitComments);
            String commentStr=commentText.getText().toString();
            if(commentStr !=null && ! commentStr.trim().isEmpty()){
                emailIntent.putExtra(Intent.EXTRA_TEXT,emailBody +commentStr);
            }else
            {
                emailIntent.putExtra(Intent.EXTRA_TEXT,emailBody);
            }
            this.startActivityForResult(Intent.createChooser(emailIntent, "Sending email..."), SEND_EMAIL);

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  //onActivityResult get results from startActivityResult
        super.onActivityResult(requestCode,resultCode,data);
        try {
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null !=data) {  //requestCode 100 is pick_image, resultCode: success or not
                if(data.getClipData() !=null) {  //if get multiple pictures, then it send back to getClipData, and put pictures into elements
                    int count = data.getClipData().getItemCount();
                    for(int i = 0; i < count; i++)
                        elements.add(data.getClipData().getItemAt(i).getUri());
                }
                else if(data.getData() != null) {  //if get only 1 picture, then it send back to getData

                    elements.add(data.getData());
                }
                myAdapter.notifyDataSetChanged();  //elements(pictures) change, then have to update contents in the listView
                toggleSubmitStatus();  //change submit status, if listView have pictures then can submit, if not, can not submit
            }
            //if request code is send_email, then will back to AccountActivity(main page)
            if (requestCode == SEND_EMAIL) {
                Intent intent=new Intent(this, AccountActivity.class);
                intent.putExtra("ID", studentID);
                startActivity(intent);
            }

        }catch (Exception e){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    private class MyListAdapter extends BaseAdapter {  //do listView, have to implement BaseAdapter
        @Override
        public int getCount() {
            return elements.size() ;
        }  //how many contents in the list

        @Override //shows what string is at row i (0-9)
        public Uri getItem(int i) { return elements.get(i); }  //Which item to fetch from the list, i start from 0

        @Override //returns the database id of row i
        public long getItemId(int i) { return i;}  //from database to get item ID

        @Override //how to show row i
        public View getView(int i, View view, ViewGroup viewGroup) {  //from getView to display listView's content
            Uri img=getItem(i);  // get Item
            LayoutInflater inflater = getLayoutInflater();
            View thisRow;
            thisRow= inflater.inflate(R.layout.row_submitpicture, viewGroup, false);  //Take the contents of the layout to display

            ImageView imgView= thisRow.findViewById(R.id.selectedImg1);  // find the box that display pictures
            imgView.setImageURI(img);  //display pictures
            ImageButton delImgBtn =thisRow.findViewById(R.id.delImgButton1);  //find the delete button
            delImgBtn.setOnClickListener(click->{  //if click delete button, then will delete
                elements.remove(getItem(i));
                notifyDataSetChanged();  //if delete data, need to update
                toggleSubmitStatus();  //check submit status
            });
            return thisRow;
        }
    }

    private void toggleSubmitStatus() {  //submit status
        if(elements!=null && elements.size()>=1)  //if pictures are not null and pictures >= 1
        {
            submitBtn.setEnabled(true);  //submit button can click
        }
        else
        {
            submitBtn.setEnabled(false);  //otherwise can not click submit button
        }
    }

    //Each time will clear data
    private void loadData() {
        elements.clear();

        studentID = studentID_def;
        //determine the calling activity.
        Intent intentFrPreActivity = getIntent();
        title=intentFrPreActivity.getStringExtra("TITLE");
        code_e = intentFrPreActivity.getStringExtra("E_CODE");
        desc = intentFrPreActivity.getStringExtra("DESC");

        LinearLayout checklistField = findViewById(R.id.ChecklistTimeField);
        checklistField.setVisibility(View.GONE);

        LinearLayout stimestampField = findViewById(R.id.startTimeField);
        stimestampField.setVisibility(View.GONE);
        e_timestamp= intentFrPreActivity.getStringExtra("END_TS");
        code_ck = intentFrPreActivity.getStringExtra("CK_CODE");
        assignmentId="0";
        if(code_ck != null){
            ck_timestamp = intentFrPreActivity.getStringExtra("CK_TS");
            s_timestamp = intentFrPreActivity.getStringExtra("START_TS");
            code_s = intentFrPreActivity.getStringExtra("S_CODE");
            assignmentId=code_e.substring(0,1);
            checklistField.setVisibility(View.VISIBLE);
            TextView typedchecklist=findViewById(R.id.submitChecklistTime);
            typedchecklist.setText(ck_timestamp);
            stimestampField.setVisibility(View.VISIBLE);
            TextView typed_S_Timestamp=findViewById(R.id.submitStartTime);
            typed_S_Timestamp.setText(s_timestamp);
        }

        //----- display ----
        TextView typedTitle = findViewById(R.id.submittitle);
        typedTitle.setText(title);
        TextView typedStudent = findViewById(R.id.submitStudentID);
        typedStudent.setText(studentID);
        TextView typedTimestamp = findViewById(R.id.submitEndTime);
        typedTimestamp.setText(e_timestamp);
        TextView typeDesc = findViewById(R.id.submitDesc);
        typeDesc.setText(desc);

    }

    // -- save Timestamp into the SQLite database
    private void add_Timestamp(String sID, String code, String timestamp) {
        tsDBHelper = new TimestampDBHelper(this);

        ts_new = new Timestamp();
        String priKey = sID + code;
        ts_new.setStudentID(priKey);
        ts_new.setAssmntCode(code);
        ts_new.setTimestamp(timestamp);

        if (tsDBHelper.isExist(priKey, code)){
            tsDBHelper.updateTimestamp(ts_new);
        }else {
            tsDBHelper.addTimestamp(ts_new);
        }

    }

    private class ServerQuery extends AsyncTask< String, Integer, String> {

        public String doInBackground(String... args) {

            try{
                HttpURLConnection connection = (HttpURLConnection) new URL(args[0] ).openConnection();
                connection.setRequestMethod("POST");
                int responseCode = connection.getResponseCode();

                if (responseCode == 200) {
                    return "done";
                }
                else
                {
                    return (getString(R.string.error_valid_email_password));
                }

            }catch(Exception ex){
                return "REST API Failed";
            }

        }

    }

    private void UpdateServer(String student_id, String assignment_id, String task_id, String time_stamp){
        String uri_params;
        uri_params = "student_id=" + student_id;
        uri_params += "&assignment_id=" + assignment_id;
        uri_params += "&task_id=" + task_id;
        uri_params += "&time_stamp=" + time_stamp;

        ServerQuery req = new ServerQuery(); //creates a background thread
        req.execute(getString(R.string.rest_url) + "task/?"+uri_params);

    }
//-------------Get email----
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
                        token = obj.keys().next();
                        prof_email = obj.getString(token);
                        System.out.println("-->Submission page: Prof email " + prof_email);

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

    private void getEmail(String id){
        String uri_params;
        uri_params = "student_id=" + id;

        ServerGet req = new ServerGet(); //creates a background thread
        req.execute(getString(R.string.rest_url) + "get_email/?"+uri_params);
    }
}

