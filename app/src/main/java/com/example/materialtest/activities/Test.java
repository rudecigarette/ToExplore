package com.example.materialtest.activities;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.materialtest.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Test extends AppCompatActivity {
    Button button;
    String s2 = "123";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        InputStream inputStream = getResources().openRawResource(R.raw.storeinfo);
        s2 = getString(inputStream).get(0);



        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Test.this, s2, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static List<String> getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        List<String> storeinfo= new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                storeinfo.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storeinfo;
    }

}
