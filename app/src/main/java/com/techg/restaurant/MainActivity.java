package com.techg.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  AddTagsFragment.addTagListener,
        AddCategoryFragment.AddNewTagListener, DeleteAlertFragment.DeleteCategoryListener{
    private ViewPager mPager;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        // hiding action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // adding data to Database
        addContent();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), 3, true, null);
        mPager.setAdapter(mPagerAdapter);

        // shows titles of pages in view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mPager);

        // shared Pref.
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // shared pref. common to entire application
        mPager.setCurrentItem(sharedPreferences.getInt("position_home",0));

        String redirect = sharedPreferences.getString("activity",null);

        if(redirect != null && redirect.equals("content")){
            Intent intent = new Intent(this, ContentView.class);
            // type
            String type = sharedPreferences.getString("type", "allitems");
            intent.putExtra("type", type);
            // position
            int position = sharedPreferences.getInt("position_content", 0);
            intent.putExtra("position", position);
            // category
            if(type.equals("category")){
                long category_id = sharedPreferences.getLong("category_id", 0);
                intent.putExtra("category_id", category_id);
            }
            startActivity(intent);
        }

    }

    public void reloadAdapter(){
        mPager.getAdapter().notifyDataSetChanged();
    }

    // refreshes pages when new tags are added
    @Override
    public void tagsAdded() {
        reloadAdapter();
    }

    @Override
    public void newTagAdded(){ reloadAdapter(); }

    @Override
    public void onDeleteCategory() {
        reloadAdapter();
    }

    // adds data to database
    void addContent(){
        // database objects
        MenuDbHelper menuDbHelper = new MenuDbHelper(getApplicationContext());
        SQLiteDatabase db = menuDbHelper.getWritableDatabase();

        // add content data
        for(int i=1;i<=100;i++){
            String filename = "img"+i;
            Item.insertItemToDb(db, filename,filename,filename);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("activity", "home");
        editor.commit();

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("position_home", mPager.getCurrentItem());
        editor.commit();
    }
}
