package com.techg.restaurant;

import android.database.sqlite.SQLiteDatabase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_view);

        // database classes
        MenuDbHelper menuDbHelper = new MenuDbHelper(getApplicationContext());
        SQLiteDatabase db = menuDbHelper.getWritableDatabase();

        // getting view pager position
        int position = getIntent().getIntExtra("position", 0);

        // allitems, category, untagged
        ArrayList<Item> items = null;
        String type = getIntent().getStringExtra("type");

        if(type.equals("allitems")){
            items = Item.getAllItemsFromDb(db);
        }
        else if(type.equals("untagged")){
            items = Item.getAllUntaggedItemsFromDb(db);
        }
        else if(type.equals("category")){
            long category_id = getIntent().getLongExtra("category_id",0);
            Category category = Category.getCategory(db, category_id);
            items = Item.getItemsOfCategoryFromDb(db, category);
        }



        // hiding action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Instantiate a ViewPager and a PagerAdapter.
        ViewPager mPager = (ViewPager) findViewById(R.id.content_view_pager);
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), items.size(), false, items);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(position);
    }
    
    


}
