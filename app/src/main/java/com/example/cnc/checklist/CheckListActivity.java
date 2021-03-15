package com.example.cnc.checklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

//import com.github.barteksc.pdfviewer.PDFView;

import com.example.cnc.R;

import java.util.ArrayList;

public class CheckListActivity extends AppCompatActivity {

    //private PDFView ch_list_pdf;
    private TextView youtube_video_one, youtube_video_two, youtube_video_three, youtube_video_four;
    private Button arrow_next;
    private CheckBox cb_1, cb_2, cb_3, cb_4, cb_5, cb_6, cb_7;
    private ArrayList<CheckBox> checkBoxes;
    String studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
//        ch_list_pdf=findViewById(R.id.pdfView);
//        ch_list_pdf.fromAsset("ChecklistFile.pdf").load();
        arrow_next=findViewById(R.id.bt_next);
        checkBoxes=new ArrayList<>();

        Intent intentOri = getIntent();
        studentID = intentOri.getStringExtra("ID");

        cb_1=findViewById(R.id.cb_1);
        cb_2=findViewById(R.id.cb_2);
        cb_3=findViewById(R.id.cb_3);
        cb_4=findViewById(R.id.cb_4);
        cb_5=findViewById(R.id.cb_5);
        cb_6=findViewById(R.id.cb_6);
        cb_7=findViewById(R.id.cb_7);

        checkBoxes.add(cb_1);
        checkBoxes.add(cb_2);
        checkBoxes.add(cb_3);
        checkBoxes.add(cb_4);
        checkBoxes.add(cb_5);
        checkBoxes.add(cb_6);
        checkBoxes.add(cb_7);

        youtube_video_one=findViewById(R.id.tv_video_one);
        youtube_video_two=findViewById(R.id.tv_video_two);
        youtube_video_three=findViewById(R.id.tv_video_three);
        youtube_video_four=findViewById(R.id.tv_video_four);
        youtube_video_one.setOnClickListener(click->{
            gotoUrl("https://www.youtube.com/watch?v=OzdWfa1lJzo");
        });
        youtube_video_two.setOnClickListener(click->{
            gotoUrl("https://www.youtube.com/watch?v=Sv7qB53ro6k");
        });
        youtube_video_three.setOnClickListener(click->{
            gotoUrl("https://www.youtube.com/watch?v=1CUosQgAZdg");
        });
        youtube_video_four.setOnClickListener(click->{
            gotoUrl("https://www.youtube.com/watch?v=M6wtVSIcZUI");
        });

        arrow_next.setOnClickListener(click->{
            if(allChecked()){
                Intent intent=new Intent(this, ChecklistActivity_2.class);
                intent.putExtra("ID", studentID);
                startActivity(intent);

            }else{
                Toast.makeText(getApplicationContext(),"Mark all the checkboxes",Toast.LENGTH_LONG).show();
            }
        });

    }
    public boolean allChecked() {

        for (CheckBox cb : checkBoxes) {
            if (cb.isChecked() == false) {
                return false;
            }
        }return true;
    }

    private void gotoUrl(String urlPattern){
        Uri uri= Uri.parse(urlPattern);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }



}


