package com.example.cnc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class CheckList_pdf_reader extends AppCompatActivity {
PDFView ch_list_pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_pdf_reader);
        ch_list_pdf=findViewById(R.id.pdfView);
        ch_list_pdf.fromAsset("ChecklistFile.pdf").load();

    }
}