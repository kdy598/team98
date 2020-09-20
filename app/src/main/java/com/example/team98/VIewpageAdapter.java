package com.example.team98;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.team98.FragFirst;
import com.example.team98.FragSecond;
import com.example.team98.FragThird;

public class VIewpageAdapter extends FragmentStateAdapter {

    public int mCount;

    public VIewpageAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0) return new FragFirst();
        else if(index==1) return new FragSecond();
        else return new FragThird();
         }

    @Override
    public int getItemCount() {
        return 2000;
    }

    public int getRealPosition(int position) { return position % mCount; }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return createFragment(position);
    }
}