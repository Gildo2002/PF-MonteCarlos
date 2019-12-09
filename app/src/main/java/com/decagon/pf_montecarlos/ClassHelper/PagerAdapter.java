package com.decagon.pf_montecarlos.ClassHelper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.decagon.pf_montecarlos.ClassFragment.tabs1;
import com.decagon.pf_montecarlos.ClassFragment.tabs2;
import com.decagon.pf_montecarlos.ClassFragment.tabs3;
import com.decagon.pf_montecarlos.ClassFragment.tabs4;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numberTab;

    public PagerAdapter(FragmentManager fm, int numberTab) {
        super(fm);
        this.numberTab = numberTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new tabs1();
            case 1:
                return new tabs2();
            case 2:
                return new tabs3();
            case 3:
                return new tabs4();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
