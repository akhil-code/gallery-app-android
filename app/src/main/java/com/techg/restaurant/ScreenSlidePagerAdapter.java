package com.techg.restaurant;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private int pages;
    private boolean home;
    private MenuDbHelper menuDbHelper;
    private SQLiteDatabase db;
    private ArrayList<Item> items;

    public ScreenSlidePagerAdapter(FragmentManager fm, int pages, boolean home, ArrayList<Item> items) {
        super(fm);
        this.pages = pages;
        this.home = home;
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) {
        if(!home){
            String filename = items.get(position).filename;
            Bundle args = new Bundle();
            args.putString("filename",filename);
            ContentViewFragment fragment = new ContentViewFragment();
            fragment.setArguments(args);
            return fragment;
        }

        // all items and untagged grid view
        Bundle args = new Bundle();
        if(position == 0 || position == 2){
            String type = position == 0 ? "allitems" : "untagged";
            args.putString("type", type);

            UntaggedGridFragment fragment = new UntaggedGridFragment();
            fragment.setArguments(args);
            return fragment;
        }

        // category view (double recycler view)
        TaggedView fragment = new TaggedView();
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String[] titles = {"All items", "Tagged", "Untagged"};
        return titles[position];
    }

    @Override
    public int getCount() {
        return pages;
    }
}
