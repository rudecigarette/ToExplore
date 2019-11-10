package com.example.materialtest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.materialtest.R;

public class WordActivity extends AppCompatActivity {
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        textView = findViewById(R.id.myTextView);
        Intent intent = getIntent();
        String wordName = intent.getStringExtra("WordName");
        textView.setText(wordName);
    }
}
