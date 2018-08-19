package com.techg.restaurant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class Tag {
    long id, item_id, category_id;

    //constructors
    public Tag(){}
    public Tag(long item_id, long category_id){
        this.item_id = item_id;
        this.category_id = category_id;
    }

    public Tag(long id, long item_id, long category_id){
        this(item_id,category_id);
        this.id = id;
    }

    // Database functions
    public static Tag insertTagToDb(SQLiteDatabase db, Item item, Category category){
        ContentValues values = new ContentValues();
        values.put(MenuContract.Tag.COLUMN_NAME_ITEM_ID, item.id);
        values.put(MenuContract.Tag.COLUMN_NAME_CATEGORY_ID, category.id);
        long id = db.insert(MenuContract.Tag.TABLE_NAME, null, values);
        if(id > 0) return new Tag(id, item.id, category.id);
        else return null;
    }

    public static ArrayList<Tag> getTagsFromDb(SQLiteDatabase db, Item item){
        String[] projection = {
                BaseColumns._ID,
                MenuContract.Tag.COLUMN_NAME_ITEM_ID,
                MenuContract.Tag.COLUMN_NAME_CATEGORY_ID,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = MenuContract.Tag.COLUMN_NAME_ITEM_ID + " = ?";
        String[] selectionArgs = { Long.toString(item.id) };

        Cursor cursor = db.query(
                MenuContract.Tag.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        ArrayList<Tag> tags = new ArrayList<>();
        while(cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
            long item_id = cursor.getLong(cursor.getColumnIndex(MenuContract.Tag.COLUMN_NAME_ITEM_ID));
            long category_id  = cursor.getLong(cursor.getColumnIndex(MenuContract.Tag.COLUMN_NAME_CATEGORY_ID));
            tags.add(new Tag(item_id, category_id));
        }
        cursor.close();

        return tags;
    }

    public static ArrayList<Tag> getTagsFromDb(SQLiteDatabase db, Category category){
        String[] projection = {
                BaseColumns._ID,
                MenuContract.Tag.COLUMN_NAME_ITEM_ID,
                MenuContract.Tag.COLUMN_NAME_CATEGORY_ID,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = MenuContract.Tag.COLUMN_NAME_CATEGORY_ID + " = ?";
        String[] selectionArgs = { Long.toString(category.id) };

        Cursor cursor = db.query(
                MenuContract.Tag.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        ArrayList<Tag> tags = new ArrayList<>();
        while(cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
            long item_id = cursor.getLong(cursor.getColumnIndex(MenuContract.Tag.COLUMN_NAME_ITEM_ID));
            long category_id  = cursor.getLong(cursor.getColumnIndex(MenuContract.Tag.COLUMN_NAME_CATEGORY_ID));
            tags.add(new Tag(id, item_id, category_id));
        }
        cursor.close();

        return tags;
    }


    public static long deleteTag(SQLiteDatabase db, long id){
        // Define 'where' part of query.
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { Long.toString(id) };
        return db.delete(MenuContract.Tag.TABLE_NAME, selection, selectionArgs);
    }
}
