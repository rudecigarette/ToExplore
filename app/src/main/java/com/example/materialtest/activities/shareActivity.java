package com.example.materialtest.activities;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.materialtest.R;
import com.example.materialtest.helps.UserHelp;
import com.example.materialtest.utils.MysqlUtil;


public class shareActivity extends BaseActivity {

    String shopname = "";
    String title = "";
    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initNavbar(true, "分享发现");
        Button button28 = (Button) findViewById(R.id.buttonshare);
        final EditText edit1 = (EditText) findViewById(R.id.shop);
        final EditText edit2 = (EditText) findViewById(R.id.title);
        final EditText edit3 = (EditText) findViewById(R.id.text);
        button28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopname = edit1.getText().toString();
                title = edit2.getText().toString();
                text = edit3.getText().toString();
                String userid = UserHelp.getInstance().getPhone();
                MysqlUtil.publishSharing(userid, shopname, title, text);
                Toast.makeText(shareActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
}
