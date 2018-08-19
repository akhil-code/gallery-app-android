package com.techg.restaurant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;


public class Category {
    public long id;
    public String name;

    // constructors
    public Category(){}

    public Category(long id, String name){
        this(name);
        setId(id);
    }

    public Category(String name){
        this.name = name;
    }

    public Category(SQLiteDatabase db,String name){
        this(name);
        insertCategoryToDb(db);
    }

    public void setId(long id){
        this.id = id;
    }

    private void insertCategoryToDb(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(MenuContract.Category.COLUMN_NAME_NAME, this.name);
        setId(db.insert(MenuContract.Category.TABLE_NAME, null, values));
    }

    public static Category getCategory(SQLiteDatabase db, long id){
        String[] projection = {
                BaseColumns._ID,
                MenuContract.Category.COLUMN_NAME_NAME,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { Long.toString(id) };

        Cursor cursor = db.query(
                MenuContract.Category.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        String name="", price="";
        if(cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex(MenuContract.Category.COLUMN_NAME_NAME));
        }
        cursor.close();

        return new Category(name);
    }

    public static ArrayList<Category> getCategories(SQLiteDatabase db){
        String[] projection = {
                BaseColumns._ID,
                MenuContract.Category.COLUMN_NAME_NAME,
        };


        Cursor cursor = db.query(
                MenuContract.Category.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        ArrayList<Category> categories = new ArrayList<>();
        while(cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
            String name = cursor.getString(cursor.getColumnIndex(MenuContract.Category.COLUMN_NAME_NAME));
            categories.add(new Category(id, name));
        }
        cursor.close();

        return categories;
    }

}

