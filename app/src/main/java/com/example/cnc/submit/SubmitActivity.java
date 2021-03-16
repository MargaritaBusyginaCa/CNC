package com.example.cnc.submit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.cnc.supporters.User;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class SubmitActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private static final int SEND_EMAIL = 200;
    private Button submitBtn;
    MyListAdapter myAdapter;
    private ArrayList<Uri> elements = new ArrayList<>();
    String studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        loadData();
        ListView myList = findViewById(R.id.theListView);
        myAdapter = new MyListAdapter();
        myList.setAdapter(myAdapter);


        //--- Select photos ---
        Button SelectPhoto = findViewById(R.id.photos);
        SelectPhoto.setOnClickListener(click->{
            Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
            gallery.setType("image/*");
            gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
        });

        //--- No submit ---
        Button noSubmitBtn = findViewById(R.id.cancelBtn);
        noSubmitBtn.setOnClickListener(click->{
            Intent intent=new Intent(this, AccountActivity.class);
            intent.putExtra("ID", studentID); // -- added by Lai
            startActivity(intent);
        });

        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setEnabled(false);
        submitBtn.setOnClickListener(click->{

         final Intent emailIntent = new Intent (Intent.ACTION_SEND_MULTIPLE);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "email"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "assignment subject");
            emailIntent.putExtra(Intent.EXTRA_STREAM,elements);
          /*  if (elements!=null && elements.size()>=1) {
                for(Uri u: elements){
                    emailIntent.putExtra(Intent.EXTRA_STREAM, u);
                }

            }*/
            EditText commentText = findViewById(R.id.submitComments);
            String commentStr=commentText.getText().toString();
            if(commentStr !=null && ! commentStr.trim().isEmpty()){
                emailIntent.putExtra(Intent.EXTRA_TEXT, commentStr);
            }

            this.startActivityForResult(Intent.createChooser(emailIntent, "Sending email..."), SEND_EMAIL);



        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        try {
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null !=data) {
                if(data.getClipData() !=null) {
                    int count = data.getClipData().getItemCount();
                    for(int i = 0; i < count; i++)
                        elements.add(data.getClipData().getItemAt(i).getUri());
                }
                else if(data.getData() != null) {

                    elements.add(data.getData());
                }
                myAdapter.notifyDataSetChanged();
                toggleSubmitStatus();
            }
            if (requestCode == SEND_EMAIL) {
                Intent intent=new Intent(this, AccountActivity.class);
                startActivity(intent);
            }

        }catch (Exception e){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    private class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return elements.size() ;
        }

        @Override //shows what string is at row i (0-9)
        public Uri getItem(int i) {
            return elements.get(i);
        }

        @Override //returns the database id of row i
        public long getItemId(int i) { return i;}

        @Override //how to show row i
        public View getView(int i, View view, ViewGroup viewGroup) {
            Uri img=getItem(i);
            LayoutInflater inflater = getLayoutInflater(); //this loads xml layouts
            View thisRow;
            thisRow= inflater.inflate(R.layout.row_submitpicture, viewGroup, false);
           // TextView tv = thisRow.findViewById(R.id.textGoesHere);
            //tv.setText( getItem(i)); //what goes in row i
            ImageView imgView= thisRow.findViewById(R.id.selectedImg);
            imgView.setImageURI(img);
            ImageButton delImgBtn =thisRow.findViewById(R.id.delImgButton);
            delImgBtn.setOnClickListener(click->{
                elements.remove(getItem(i));
                notifyDataSetChanged();
                toggleSubmitStatus();
            });
            return thisRow;
        }
    }

    private void toggleSubmitStatus() {
        if(elements!=null && elements.size()>=1)
        {
            submitBtn.setEnabled(true);
        }
        else
        {
            submitBtn.setEnabled(false);
        }
    }

    private void loadData() {
            elements.clear();
    }
}