package com.example.team98;

import androidx.appcompat.app.AppCompatActivity;
import com.example.team98.LoginResult;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText TextInput_ID,TextInput_PW;// 아이디 비밀번호


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_log = (Button)findViewById(R.id.button);
        Button btn_log_kakao = (Button)findViewById(R.id.button3);
        Button btn_log_naver = (Button)findViewById(R.id.button4);

        TextInput_ID = findViewById(R.id.login_ID);
        TextInput_PW = findViewById(R.id.login_PW);
        btn_log.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = TextInput_ID.getText().toString();
                String pw = TextInput_PW.getText().toString();

                Intent intent = new Intent(MainActivity.this,LoginResult.class);
                intent.putExtra("ID",id);
                intent.putExtra("PW",pw);
                startActivity(intent);


            }
        });
    }
}
