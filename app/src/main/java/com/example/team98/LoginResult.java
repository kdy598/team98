package com.example.team98;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class LoginResult extends AppCompatActivity
{
    EditText login_id,login_pw,check_pw;
    Button btn_regi,btn_cancel;
    Spinner year,birth;


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        login_id = (EditText) findViewById(R.id.idText);
        login_pw = (EditText) findViewById(R.id.pwText);
        check_pw = (EditText) findViewById(R.id.pw_checkText);

        btn_regi = (Button) findViewById(R.id.btn_regi);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        year =(Spinner)findViewById(R.id.spinner);
        birth = (Spinner)findViewById(R.id.spinner2);

        check_pw.addTextChangedListener(new TextWatcher() {
            TextView txt = (TextView)findViewById(R.id.error_Text);
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(login_pw.getText().toString().equals(check_pw.getText().toString()))
                {
                    txt.setText("");
                }
                else{
                    txt.setText("   비밀번호가 일치하지 않습니다.   ");
                    txt.setTextColor(Color.parseColor("#FF0000")); // 표시되는 색 변경
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        btn_regi.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login_pw == null) {
                    Toast.makeText(getApplicationContext(), "pw를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;

                }
                else {
                    TextView txt = (TextView)findViewById(R.id.error_Text);
                    txt.setText("비밀번호가 일치하지 않습니다.");
                    return;
                }
            }
        });


    }
}
