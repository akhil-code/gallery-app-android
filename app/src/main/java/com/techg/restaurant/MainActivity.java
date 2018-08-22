package com.techg.restaurant;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  AddTagsFragment.addTagListener{
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // adding data to Database
        addContent();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), 3, true, null);
        mPager.setAdapter(mPagerAdapter);
        // shows titles of pages in view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mPager);

    }

    // refreshes pages when new tags are added
    @Override
    public void tagsAdded() {
        mPager.getAdapter().notifyDataSetChanged();
    }

    // adds data to database
    void addContent(){
        // database objects
        MenuDbHelper menuDbHelper = new MenuDbHelper(getApplicationContext());
        SQLiteDatabase db = menuDbHelper.getWritableDatabase();

        // add content data
        Item.insertItemToDb(db, "Cat","150","img1");
        Item.insertItemToDb(db, "Dog","450","img2");
        Item item = Item.insertItemToDb(db, "Deer","250","img3");
        Item.insertItemToDb(db, "Elephant","100","img4");
        Item.insertItemToDb(db, "Chimpanzee","10","img5");
        Item.insertItemToDb(db, "Bear","180","img6");
        Item.insertItemToDb(db, "Parrot","120","img7");

        Category.insertCategoryToDb(db, "Mammals");
        Category.insertCategoryToDb(db, "Birds");
    }
}
