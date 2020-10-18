package com.example.team98;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginResult extends AppCompatActivity
{
    EditText login_id,login_pw,check_pw,name,pnum;
    Button btn_regi,btn_cancel;
    Spinner year,birth;
    String ps = "^[a-zA-Z0-9]*$.{5,10}"; // 한글 숫자 조합 5~10글자

    private static final String TAG = "LoginResult";
    private FirebaseAuth mAuth;

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        login_id = (EditText) findViewById(R.id.idText);
        login_pw = (EditText) findViewById(R.id.pwText);
        check_pw = (EditText) findViewById(R.id.pw_checkText);
        name = (EditText)findViewById(R.id.name);
        pnum = (EditText)findViewById(R.id.pnum);

        btn_regi = (Button) findViewById(R.id.btn_regi);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        year =(Spinner)findViewById(R.id.spinner);
        birth = (Spinner)findViewById(R.id.spinner2);

        /* 영어 또는 숫자만 받아오는필터 */



        pnum.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if(keyCode == event.KEYCODE_ENTER)
                {
                    return true;
                }
                return false;
            }
        }); // 글자 입력시 엔터키로 개행되는 것을 막는다.
        name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if(keyCode == event.KEYCODE_ENTER)
                {
                    return true;
                }
                return false;
            }
        });


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
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginResult.this,HomeActivity.class);
                startActivity(intent);
            }
        });


        btn_regi.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (login_id.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "id를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if(!login_id.getText().toString().equals("")){
                    if (ps.matches(login_id.getText().toString())|(login_id.getText().toString().length()<5|login_id.getText().toString().length()>10)) {
                        Toast.makeText(getApplicationContext(), "5~10 글자의 영,숫자를 입력하시오", Toast.LENGTH_SHORT).show();
                    } else if (login_pw.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "pw를 입력하세요", Toast.LENGTH_SHORT).show();
                        return;

                    }/*else if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", login_pw.getText().toString()))
                    {

                        Toast.makeText(getApplicationContext(), "정규식x", Toast.LENGTH_SHORT).show();

                    }*/

                }
                else {
                    signUp();
                }

            }
        });


    }
    // 여기서부터 로그인 인증
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    private void signUp() {
        String mCustomToken = ((btn_regi)).getText().toString();

        mAuth.signInWithCustomToken(mCustomToken)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            Toast.makeText(LoginResult.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
