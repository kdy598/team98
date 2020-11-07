package com.example.team98;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class PopupActivity extends Activity {

    TextView txtText,txtText4,txtText3;
    ImageView image;
    CheckBox c1,c2,c3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup);

        //UI 객체생성
        image = (ImageView)findViewById(R.id.img);
        txtText = (TextView)findViewById(R.id.txt1);
        txtText4 = (TextView)findViewById(R.id.txt4);
        txtText3 = (TextView)findViewById(R.id.txt3);
        c1 = (CheckBox)findViewById(R.id.checkBox);
        c2 = (CheckBox)findViewById(R.id.checkBox2);
        c3 = (CheckBox)findViewById(R.id.checkBox3);

        //데이터 가져오기
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String sex = intent.getStringExtra("sex");
        String feature = intent.getStringExtra("feature");
        String live = intent.getStringExtra("live");
        int img = intent.getIntExtra("img",1);
        txtText.setText(name);
        if(sex.equals("male"))
        {
            c1.setChecked(true);
        }
        else if(sex.equals("female"))
        {
            c2.setChecked(true);
        }
        else
        {
            c3.setChecked(true);
        }
        txtText3.setText(feature);
        txtText4.setText(live);
        image.setImageResource(img);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}


