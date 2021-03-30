package com.example.cnc.submit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.example.cnc.R;
import com.example.cnc.submit.SubmitActivity;

public class SubmitChoiceActivity extends AppCompatActivity {
    private Button email;
    private Button ms_form;
    String title, studentID, s_timestamp, e_timestamp, desc, ck_timestamp;
    String code_ck = null;
    String code_s, code_e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_choice);

        //----- get the info from the previous activity
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

        email=findViewById(R.id.bt_submit_email);
        ms_form=findViewById(R.id.bt_submit_ms);

        email.setOnClickListener(click->{
            Intent intent = new Intent(this, SubmitActivity.class);
            intent.putExtra("TITLE", title);
            intent.putExtra("ID", studentID);
            intent.putExtra("DESC", desc);
            intent.putExtra("CK_TS", ck_timestamp);
            intent.putExtra("START_TS", s_timestamp);
            intent.putExtra("END_TS", e_timestamp);
            intent.putExtra("CK_CODE", code_ck);
            intent.putExtra("S_CODE", code_s);
            intent.putExtra("E_CODE", code_e);
            startActivity(intent);
            startActivity(intent);
        });

        ms_form.setOnClickListener(click->{
            gotoUrl("https://forms.office.com/Pages/DesignPage.aspx?auth_pvr=OrgId&auth_upn=busy0001%40algonquinlive.com&origin=OfficeDotCom&lang=en-CA&route=LeftNav#FormId=JNkb7GoKqUqqicmAMWwESVtB4Pm0yv5Ojv2WVfBt2BJURVVGN0ZVUDIxWk1GTEZXR1IzVFo3QlZETi4u");
        });
    }
//This method transfers to the MS forms web page
    private void gotoUrl(String urlPattern){
        Uri uri= Uri.parse(urlPattern);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}