package com.example.team98;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Catlistactivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String s1[] , s2[];
    String s3[];
    int image[] ={R.drawable.caramel,R.drawable.karae,R.drawable.ms,R.drawable.rl,R.drawable.bd,R.drawable.mn};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catlist);

        recyclerView = findViewById(R.id.recyclerview);
        s1 = getResources().getStringArray(R.array.cat_list_juk);
        s2 = getResources().getStringArray(R.array.cat_features_juk);
        s3 = getResources().getStringArray(R.array.cat_feature);

        Adapter myadapter = new Adapter(this,s1,s2,image);
        recyclerView.setAdapter(myadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myadapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // 클릭했을때 원하는데로 처리해주는 부분
                Intent intent = new Intent(Catlistactivity.this,PopupActivity.class);
                intent.putExtra("name",s1[position]);
                intent.putExtra("sex", "male");
                intent.putExtra("feature", s3[position]);
                intent.putExtra("img", image[position]);
                intent.putExtra("live", s2[position]);
                startActivity(intent);
            }
        });
    }

}
