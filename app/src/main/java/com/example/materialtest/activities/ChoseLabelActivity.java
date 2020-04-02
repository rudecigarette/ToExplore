package com.example.materialtest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.materialtest.R;
import com.example.materialtest.helps.UserHelp;
import com.example.materialtest.utils.MysqlUtil;
import com.example.materialtest.utils.StatusBarUtils;
import com.example.materialtest.utils.UserUtils;

public class ChoseLabelActivity extends BaseActivity {
    public static String p = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_label);
        initNavbar(false,"选择您喜欢的标签");
        StatusBarUtils.setColor(this, getResources().getColor(R.color.colorPrimary));
        Button button2 =(Button) findViewById(R.id.button2);
        final String userPhone = UserHelp.getInstance().getPhone();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MysqlUtil.choseLabel(p,userPhone);
                Toast.makeText(ChoseLabelActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChoseLabelActivity.this,MainActivity.class));
                finish();
            }
        });

        Button button =(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button =(Button) findViewById(R.id.button);
                i= (String) button.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button3 =(Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button3 =(Button) findViewById(R.id.button3);
                i= (String) button3.getText();
                p=p+i+"|";
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button3.setEnabled(false);
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button4 =(Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button4 =(Button) findViewById(R.id.button4);
                i= (String) button4.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button4.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button5 =(Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button5 =(Button) findViewById(R.id.button5);
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button5.setEnabled(false);
                i= (String) button5.getText();
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button6 =(Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button6 =(Button) findViewById(R.id.button6);
                i= (String) button6.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button6.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button7 =(Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button7 =(Button) findViewById(R.id.button7);
                i= (String) button7.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button7.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button8 =(Button) findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button8 =(Button) findViewById(R.id.button8);
                i= (String) button8.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button8.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button9 =(Button) findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button9 =(Button) findViewById(R.id.button9);
                i= (String) button9.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button9.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button10 =(Button) findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button10 =(Button) findViewById(R.id.button10);
                i= (String) button10.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button10.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button11 =(Button) findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button11 =(Button) findViewById(R.id.button11);
                i= (String) button11.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button11.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button12 =(Button) findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button12 =(Button) findViewById(R.id.button12);
                i= (String) button12.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button12.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button13 =(Button) findViewById(R.id.button13);
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button13 =(Button) findViewById(R.id.button13);
                i= (String) button13.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button13.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button14 =(Button) findViewById(R.id.button14);
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button14 =(Button) findViewById(R.id.button14);
                i= (String) button14.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button14.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button15 =(Button) findViewById(R.id.button15);
        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button15 =(Button) findViewById(R.id.button15);
                i= (String) button15.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button15.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button16 =(Button) findViewById(R.id.button16);
        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button16 =(Button) findViewById(R.id.button16);
                i= (String) button16.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button16.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button17 =(Button) findViewById(R.id.button17);
        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button17 =(Button) findViewById(R.id.button17);
                i= (String) button17.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button17.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button18 =(Button) findViewById(R.id.button18);
        button18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button18 =(Button) findViewById(R.id.button18);
                i= (String) button18.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button18.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button19 =(Button) findViewById(R.id.button19);
        button19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button19 =(Button) findViewById(R.id.button19);
                i= (String) button19.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button19.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button20 =(Button) findViewById(R.id.button20);
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button20 =(Button) findViewById(R.id.button20);
                i= (String) button20.getText();
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button21 =(Button) findViewById(R.id.button21);
        button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button21 =(Button) findViewById(R.id.button21);
                i= (String) button21.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button21.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button22 =(Button) findViewById(R.id.button22);
        button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button22 =(Button) findViewById(R.id.button22);
                i= (String) button22.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button22.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button23 =(Button) findViewById(R.id.button23);
        button23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button23 =(Button) findViewById(R.id.button23);
                i= (String) button23.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button23.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button24 =(Button) findViewById(R.id.button24);
        button24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button24 =(Button) findViewById(R.id.button24);
                i= (String) button24.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button24.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button25 =(Button) findViewById(R.id.button25);
        button25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i="";
                Button button25 =(Button) findViewById(R.id.button25);
                i= (String) button25.getText();
                v.setBackgroundColor(getResources().getColor(R.color.colorHighlight));
                button25.setEnabled(false);
                p=p+i+"|";
                Toast.makeText(ChoseLabelActivity.this, "缓存成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
