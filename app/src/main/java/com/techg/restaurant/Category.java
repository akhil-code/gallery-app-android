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

    public static Category insertCategoryToDb(SQLiteDatabase db, String category_name){
        ContentValues values = new ContentValues();
        values.put(MenuContract.Category.COLUMN_NAME_NAME, category_name);
        long category_id = db.insert(MenuContract.Category.TABLE_NAME, null, values);
        if(category_id > 0){
            return new Category(category_id, category_name);
        }
        else return null;
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
        return new Category(id, name);
    }

    public static ArrayList<Category> getAllCategories(SQLiteDatabase db){
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

        return getCategoriesFromCursor(cursor);
    }

    public static long deleteCategory(SQLiteDatabase db, long id){
        // Define 'where' part of query.
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { Long.toString(id) };
        return db.delete(MenuContract.Category.TABLE_NAME, selection, selectionArgs);
    }

    public static ArrayList<Category> getCategoriesOfItem(SQLiteDatabase db, long item_id, boolean included){
        String SQL_QUERY = "SELECT * FROM " + MenuContract.Category.TABLE_NAME
                                + " WHERE " + MenuContract.Category._ID;
        if(!included) SQL_QUERY += " NOT";
        SQL_QUERY += " IN ( SELECT DISTINCT(" + MenuContract.Tag.COLUMN_NAME_CATEGORY_ID +
                                ") FROM " + MenuContract.Tag.TABLE_NAME + " WHERE " +
                                   MenuContract.Tag.COLUMN_NAME_ITEM_ID + " = " + item_id + ")";
        Cursor cursor = db.rawQuery(SQL_QUERY,null);
        return getCategoriesFromCursor(cursor);
    }


    private static ArrayList<Category> getCategoriesFromCursor(Cursor cursor){
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

