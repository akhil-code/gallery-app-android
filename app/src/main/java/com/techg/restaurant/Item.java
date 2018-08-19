package com.techg.restaurant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class Item {
    public long id;
    public String name, price, filename;

    // constructors
    public Item(){}

    public Item(String name, String price, String filename){
        this.name = name;
        this.price = price;
        this.filename = filename;
    }

    public Item(long id, String name, String price, String filename){
        this(name, price, filename);
        this.id = id;
    }

    // Database functions
    public static Item insertItemToDb(SQLiteDatabase db, String name, String price, String filename){
        ContentValues values = new ContentValues();
        values.put(MenuContract.Item.COLUMN_NAME_NAME, name);
        values.put(MenuContract.Item.COLUMN_NAME_PRICE, price);
        values.put(MenuContract.Item.COLUMN_NAME_FILENAME, filename);
        long id = db.insert(MenuContract.Item.TABLE_NAME, null, values);
        if(id > 0) return new Item(id, name, price, filename);
        else return null;
    }

    private static String[] getProjection(){
        String[] projection = {
                BaseColumns._ID,
                MenuContract.Item.COLUMN_NAME_NAME,
                MenuContract.Item.COLUMN_NAME_PRICE,
                MenuContract.Item.COLUMN_NAME_FILENAME,
        };
        return projection;
    }

    public static Item getItemFromDb(SQLiteDatabase db, long id){
        String projection[] = getProjection();
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

        String name="", price="", filename="";
        if(cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex(MenuContract.Item.COLUMN_NAME_NAME));
            price = cursor.getString(cursor.getColumnIndex(MenuContract.Item.COLUMN_NAME_PRICE));
            filename = cursor.getString(cursor.getColumnIndex(MenuContract.Item.COLUMN_NAME_FILENAME));
        }
        cursor.close();

        if(name.equals(""))
            return null;
        return new Item(id, name, price, filename);
    }

    public static ArrayList<Item> getAllUntaggedItemsFromDb(SQLiteDatabase db){
        Cursor cursor = db.rawQuery(
                "SELECT * FROM "+MenuContract.Item.TABLE_NAME+
                    " WHERE "+MenuContract.Item._ID+
                    " NOT IN (SELECT DISTINCT("+ MenuContract.Tag.COLUMN_NAME_ITEM_ID+") FROM "+MenuContract.Tag.TABLE_NAME+")",
                null
        );

        ArrayList<Item> items = new ArrayList<>();
        while(cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
            String name = cursor.getString(cursor.getColumnIndex(MenuContract.Item.COLUMN_NAME_NAME));
            String price = cursor.getString(cursor.getColumnIndex(MenuContract.Item.COLUMN_NAME_PRICE));
            String filename = cursor.getString(cursor.getColumnIndex(MenuContract.Item.COLUMN_NAME_FILENAME));
            items.add(new Item(id, name, price, filename));
        }
        cursor.close();

        return items;
    }

    public static ArrayList<Item> getItemsOfCategoryFromDb(SQLiteDatabase db, long category_id){
        Cursor cursor = db.rawQuery(
                "SELECT * FROM "+MenuContract.Item.TABLE_NAME+" WHERE "+MenuContract.Item._ID+
                    " IN (SELECT DISTINCT("+MenuContract.Item._ID+") FROM "+MenuContract.Tag.TABLE_NAME+
                    " WHERE "+MenuContract.Tag.COLUMN_NAME_CATEGORY_ID+" = "+category_id+")",
                null
        );

        ArrayList<Item> items = new ArrayList<>();
        while(cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
            String name = cursor.getString(cursor.getColumnIndex(MenuContract.Item.COLUMN_NAME_NAME));
            String price = cursor.getString(cursor.getColumnIndex(MenuContract.Item.COLUMN_NAME_PRICE));
            String filename = cursor.getString(cursor.getColumnIndex(MenuContract.Item.COLUMN_NAME_FILENAME));
            items.add(new Item(id, name, price, filename));
        }
        cursor.close();

        return items;
    }


    public static ArrayList<Item> getAllItemsFromDb(SQLiteDatabase db){
        String[] projection = getProjection();

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
            String filename = cursor.getString(cursor.getColumnIndex(MenuContract.Item.COLUMN_NAME_FILENAME));
            items.add(new Item(id, name, price, filename));
        }
        cursor.close();

        return items;
    }


    public static long deleteItem(SQLiteDatabase db, long id){
        // Define 'where' part of query.
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { Long.toString(id) };
        return db.delete(MenuContract.Item.TABLE_NAME, selection, selectionArgs);
    }
}
