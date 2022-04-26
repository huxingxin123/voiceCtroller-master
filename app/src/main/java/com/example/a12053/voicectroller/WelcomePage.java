package com.example.a12053.voicectroller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WelcomePage extends AppCompatActivity {

    public final static String USER_ID = "user_id";
    public final static String USER_PSD = "user_psd";
    private EditText signId;
    private EditText signPsd;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        signId = findViewById(R.id.signup_id);
        signPsd = findViewById(R.id.signup_psd);
        btn = findViewById(R.id.signup_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = signId.getText().toString();
                String psd = signPsd.getText().toString();

                if (TextUtils.isEmpty(id)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(WelcomePage.this);
                    builder.setTitle("提醒")
                            .setMessage("注册id不能为空")
                            .setNegativeButton("了解", null).create().show();
                }else if (TextUtils.isEmpty(psd)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(WelcomePage.this);
                    builder.setTitle("提醒")
                            .setMessage("注册密码不能为空")
                            .setNegativeButton("了解", null).create().show();
                }else {
                    Intent intent = new Intent(WelcomePage.this,MainActivity.class);
                    intent.putExtra(USER_ID,id);
                    intent.putExtra(USER_PSD,psd);

                    startActivity(intent);
                }


            }
        });
    }
}