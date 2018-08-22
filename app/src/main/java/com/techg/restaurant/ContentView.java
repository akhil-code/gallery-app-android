package com.techg.restaurant;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class ContentView extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private String type;
    private ViewPager mPager;
    private long category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_view);

        // database classes
        MenuDbHelper menuDbHelper = new MenuDbHelper(getApplicationContext());
        SQLiteDatabase db = menuDbHelper.getWritableDatabase();

        // Shared Pref.
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // getting view pager position
        int position = getIntent().getIntExtra("position", 0);

        // allitems, category, untagged
        ArrayList<Item> items = null;
        type = getIntent().getStringExtra("type");

        if(type.equals("allitems")){
            items = Item.getAllItemsFromDb(db);
        }
        else if(type.equals("untagged")){
            items = Item.getAllUntaggedItemsFromDb(db);
        }
        else if(type.equals("category")){
            category_id = getIntent().getLongExtra("category_id",0);
            Category category = Category.getCategory(db, category_id);
            items = Item.getItemsOfCategoryFromDb(db, category);
        }

        // hiding action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.content_view_pager);
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), items.size(), false, items);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(position);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("activity", "content");
        editor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("type", type);
        editor.putInt("position_content", mPager.getCurrentItem());
        if(type.equals("category")){
            editor.putLong("category_id", category_id);
        }
        editor.commit();
    }


}
