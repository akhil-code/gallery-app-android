package com.techg.restaurant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class Item {
    public long id;
    public String name;
    public String price;

    // constructors
    public Item(){}

    public Item(long id, String name, String price){
        this(name, price);
        setId(id);
    }

    public Item(String name, String price){
        this.name = name;
        this.price = price;
    }

    public Item(SQLiteDatabase db,String name, String price){
        this(name, price);
        insertItemToDb(db);
    }

    public void setId(long id){
        this.id = id;
    }

    private void insertItemToDb(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(MenuContract.Item.COLUMN_NAME_NAME, this.name);
        values.put(MenuContract.Item.COLUMN_NAME_PRICE, this.price);
        setId(db.insert(MenuContract.Item.TABLE_NAME, null, values));
    }

    public static Item getItem(SQLiteDatabase db, long id){
        String[] projection = {
            BaseColumns._ID,
            MenuContract.Item.COLUMN_NAME_NAME,
            MenuContract.Item.COLUMN_NAME_PRICE,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { Long.toString(id) };

        Cursor cursor = db.query(
                MenuContract.Item.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        String name="", price="";
        if(cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex(MenuContract.Item.COLUMN_NAME_NAME));
            price = cursor.getString(cursor.getColumnIndex(MenuContract.Item.COLUMN_NAME_PRICE));
        }
        cursor.close();

        return new Item(name, price);
    }

    public static ArrayList<Item> getItems(SQLiteDatabase db){
        String[] projection = {
                BaseColumns._ID,
                MenuContract.Item.COLUMN_NAME_NAME,
                MenuContract.Item.COLUMN_NAME_PRICE,
        };


        Cursor cursor = db.query(
                MenuContract.Item.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        ArrayList<Item> items = new ArrayList<>();
        while(cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
            String name = cursor.getString(cursor.getColumnIndex(MenuContract.Item.COLUMN_NAME_NAME));
            String price = cursor.getString(cursor.getColumnIndex(MenuContract.Item.COLUMN_NAME_PRICE));
            items.add(new Item(id, name, price));
        }
        cursor.close();

        return items;
    }

}
