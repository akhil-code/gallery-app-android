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

    public Category(String name){
        this.name = name;
    }

    public Category(long id, String name){
        this.name = name;
        this.id = id;
    }

    public static long insertCategoryToDb(SQLiteDatabase db, Category category){
        ContentValues values = new ContentValues();
        values.put(MenuContract.Category.COLUMN_NAME_NAME, category.name);
        return db.insert(MenuContract.Category.TABLE_NAME, null, values);
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

        String name="";
        if(cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex(MenuContract.Category.COLUMN_NAME_NAME));
        }
        cursor.close();

        if(name.equals(""))
            return null;
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

    public static long deleteCategory(SQLiteDatabase db, long id){
        // Define 'where' part of query.
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { Long.toString(id) };
        return db.delete(MenuContract.Category.TABLE_NAME, selection, selectionArgs);
    }

}

