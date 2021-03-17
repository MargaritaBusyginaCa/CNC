package com.example.cnc.submit;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cnc.R;
import com.example.cnc.loginPage.AccountActivity;
import com.example.cnc.sql.DatabaseHelper;
import com.example.cnc.sql.TimestampDBHelper;
import com.example.cnc.supporters.Timestamp;
import com.example.cnc.supporters.User;

import java.util.ArrayList;
import java.util.List;


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
    String title, studentID,ck_timestamp,s_timestamp,e_timestamp,desc,code_ck,code_s,code_e;
    private DatabaseHelper dbHelper;
    private TimestampDBHelper tsDBHelper;
    Timestamp ts_new;

    @Override
    //display submit page
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);  //from layout to find listView
        loadData();  //find the data
        ListView myList = findViewById(R.id.theListView);
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
            if (code_ck == null){
                add_Timestamp(studentID, code_e , e_timestamp);
            }else {
                add_Timestamp(studentID, code_ck, ck_timestamp);
                add_Timestamp(studentID, code_s, s_timestamp);
                add_Timestamp(studentID, code_e, e_timestamp);
            }
            Toast.makeText(getApplicationContext(),"The timestamps have been saved",Toast.LENGTH_LONG).show();

            final Intent emailIntent = new Intent (Intent.ACTION_SEND_MULTIPLE);//intent send email
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "linlinfhl@gmail.com"});//default to email address
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, title); //email subject
            emailIntent.putExtra(Intent.EXTRA_STREAM,elements); //email attachment

            String emailBody="Student ID: " + studentID+"\n";
            emailBody+="Check list time: " +    ck_timestamp +"\n";
            emailBody+="Start time: " +    s_timestamp +"\n";
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
        Intent intentFrPreActivity = getIntent();

        title = intentFrPreActivity.getStringExtra("TITLE");
        studentID = intentFrPreActivity.getStringExtra("ID");
        ck_timestamp = intentFrPreActivity.getStringExtra("CK_TS");
        s_timestamp = intentFrPreActivity.getStringExtra("START_TS");
        e_timestamp = intentFrPreActivity.getStringExtra("END_TS");
        desc = intentFrPreActivity.getStringExtra("DESC");
        code_ck = intentFrPreActivity.getStringExtra("CK_CODE");
        code_s = intentFrPreActivity.getStringExtra("S_CODE");
        code_e = intentFrPreActivity.getStringExtra("E_CODE");


        //--- retrieve email from database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<User> users = dbHelper.getAllUser();
        Integer index=0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getStudentID().equalsIgnoreCase(studentID)) {
                index = i;
                break;
            }
        }
        String email = users.get(index).getEmail();

        //----- display ----
        TextView typedTitle = findViewById(R.id.submittitle);
        typedTitle.setText(title);
        TextView typedStudent = findViewById(R.id.submitStudentID);
        typedStudent.setText(studentID);
        TextView typedEmail = findViewById(R.id.submitEmail);
        typedEmail.setText(email);
        TextView typedchecklist = findViewById(R.id.submitChecklistTime);
        typedchecklist.setText(ck_timestamp);
        TextView typed_S_Timestamp = findViewById(R.id.submitStartTime);
        typed_S_Timestamp.setText(s_timestamp);
        TextView typedTimestamp = findViewById(R.id.submitEndTime);
        typedTimestamp.setText(e_timestamp);
        TextView typeDesc = findViewById(R.id.submitDesc);
        typeDesc.setText(desc);

    }

    // -- save Timestamp into the database
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
}