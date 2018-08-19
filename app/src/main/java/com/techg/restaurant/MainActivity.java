package com.techg.restaurant;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MenuDbHelper mDbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // starting db
        mDbHelper = new MenuDbHelper(getApplicationContext());
        db = mDbHelper.getWritableDatabase();



        // fetching single row
         Item item = Item.getItemFromDb(db, 2);
         TextView textView = (TextView) findViewById(R.id.textView);
         if(item != null)
             textView.setText(item.name);

    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }





}
