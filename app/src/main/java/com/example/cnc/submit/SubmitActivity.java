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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cnc.R;
import com.example.cnc.loginPage.AccountActivity;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipOutputStream;

public class SubmitActivity extends AppCompatActivity {
    Button noSubmitBtn;
    Button SelectPhoto;
    private static final int PICK_IMAGE = 100;

    MyListAdapter myAdapter;
    private ArrayList<Uri> elements = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        loadData();
        ListView myList = findViewById(R.id.theListView);
        myAdapter = new MyListAdapter();
        myList.setAdapter(myAdapter);

        //--- Select photos ---
        SelectPhoto = findViewById(R.id.photos);
        SelectPhoto.setOnClickListener(click->{
            Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
            gallery.setType("image/*");
            gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
        });

        //--- No submit ---
        noSubmitBtn = findViewById(R.id.cancelBtn);
        noSubmitBtn.setOnClickListener(click->{
            Intent intent=new Intent(this, AccountActivity.class);
            startActivity(intent);
        });

        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(click->{
            final Intent emailIntent = new Intent (Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "email"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "assignment subject");
            if (elements!=null) {
                emailIntent.putExtra(Intent.EXTRA_STREAM, elements.get(0));
            }
            emailIntent.putExtra(Intent.EXTRA_TEXT, " extra");
            this.startActivity(Intent.createChooser(emailIntent, "Sending email..."));
            /*Intent intent=new Intent(this, AccountActivity.class);
            startActivity(intent);*/
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
            }
            myAdapter.notifyDataSetChanged();
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

            /*loadDataFromDatabase();
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            myAdapter.notifyDataSetChanged(); */

    }

    private class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return elements.size() ; //list will have 10 items
        }

        @Override //shows what string is at row i (0-9)
        public Uri getItem(int i) {
            return elements.get(i);
        }

        @Override //returns the database id of row i
        public long getItemId(int i) { return i;} //worry about this next week

        @Override //how to show row i
        public View getView(int i, View view, ViewGroup viewGroup) {
            Uri msg=getItem(i);
            LayoutInflater inflater = getLayoutInflater(); //this loads xml layouts
            View thisRow;
            thisRow= inflater.inflate(R.layout.row_submitpicture, viewGroup, false);
           // TextView tv = thisRow.findViewById(R.id.textGoesHere);
            //tv.setText( getItem(i)); //what goes in row i
            ImageView imgView= thisRow.findViewById(R.id.selectedImg);
            imgView.setImageURI(msg);
            return thisRow;
        }
    }

    private ZipOutputStream makeZip(String name){
        FileOutputStream dest=null;
        try{
            dest = new FileOutputStream(name);
        }catch(FileNotFoundException ex){
            Toast.makeText(this, "Something went wrong when creating zip", Toast.LENGTH_LONG).show();
        }
        return new ZipOutputStream(new BufferedOutputStream(dest));
    }

    private void loadData() {
            elements.clear();
   /*         elements.add("picture 1");
            elements.add("picture 2");
            elements.add("picture 3");
            elements.add("picture 4");
            elements.add("picture 5");
            elements.add("picture 6");
     */   }
}