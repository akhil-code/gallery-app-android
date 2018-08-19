package com.techg.restaurant;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 3;

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new UntaggedGridFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String[] titles = {"All items", "Tagged", "Untagged"};
        return titles[position];
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
