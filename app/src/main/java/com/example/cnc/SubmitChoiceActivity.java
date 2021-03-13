package com.example.cnc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.example.cnc.submit.SubmitActivity;

public class SubmitChoiceActivity extends AppCompatActivity {
private Button email;
private Button ms_form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_choice);

        email=findViewById(R.id.bt_submit_email);
        ms_form=findViewById(R.id.bt_submit_ms);

        email.setOnClickListener(click->{
            Intent intent=new Intent(this, SubmitActivity.class);
            startActivity(intent);
        });

        ms_form.setOnClickListener(click->{
            gotoUrl("https://forms.office.com/Pages/DesignPage.aspx?auth_pvr=OrgId&auth_upn=busy0001%40algonquinlive.com&origin=OfficeDotCom&lang=en-CA&route=LeftNav#FormId=JNkb7GoKqUqqicmAMWwESVtB4Pm0yv5Ojv2WVfBt2BJURVVGN0ZVUDIxWk1GTEZXR1IzVFo3QlZETi4u");
        });
    }

    private void gotoUrl(String urlPattern){
        Uri uri= Uri.parse(urlPattern);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}