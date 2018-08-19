package com.techg.restaurant;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        ArrayList<Item> items = Item.getAllItemsFromDb(db);
        ArrayList<Category> categories = Category.getAllCategories(db);
        ArrayList<Item> unTaggedItems = Item.getAllUntaggedItemsFromDb(db);
        ArrayList<Item> selectedItems = Item.getItemsOfCategoryFromDb(db, categories.get(0).id);

        String mytext1 = "";
        for(Category category1 : categories){
            mytext1 += category1.name+" "+ category1.id +" " + "\n";
        }

        String mytext2 = "";
        for(Item item1 : selectedItems){
            mytext2 += item1.name+" "+ item1.id +" " + "\n";
        }

        String mytext3 = "";
        for(Item item1 : unTaggedItems){
            mytext3 += item1.name+" "+ item1.id +" " + "\n";
        }

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);

        textView1.setText(mytext1);
        textView2.setText(mytext2);
        textView3.setText(mytext3);
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }





}
