package com.example.team98;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class pagerview extends AppCompatActivity {

    ViewPager2 viewPager2;
    Button btnToggle;
    private CircleIndicator3 mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        FragmentStateAdapter pagerAdatper;

        viewPager2 = findViewById(R.id.viewPager2);
        btnToggle = findViewById(R.id.btnToggle);
        pagerAdatper = new VIewpageAdapter(this,3);

        viewPager2.setAdapter(pagerAdatper);
        //indicator
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager2);
        mIndicator.createIndicators(3,0);
        viewPager2.setOrientation(viewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setCurrentItem(1000);
        viewPager2.setOffscreenPageLimit(3);

        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
                    btnToggle.setText("가로로 슬라이드");
                    viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                }else {
                    btnToggle.setText("세로로 슬라이드");
                    viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                }
            }
        });
    }
}