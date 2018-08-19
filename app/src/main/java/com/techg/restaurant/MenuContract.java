package com.techg.restaurant;

import android.provider.BaseColumns;

public class MenuContract {
    private MenuContract(){}

    // Item table schema
    public static class Item implements BaseColumns{
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_FILENAME = "filename";
    }

    // Category table schema
    public static class Category implements BaseColumns{
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME_NAME = "name";
    }

    // tags table schema
    public static class Tag implements BaseColumns{
        public static final String TABLE_NAME = "tag";
        public static final String COLUMN_NAME_ITEM_ID = "item_id";
        public static final String COLUMN_NAME_CATEGORY_ID = "category_id";
    }

    // create table statements
    public static final String SQL_CREATE_TABLE_ITEM =
                    "CREATE TABLE IF NOT EXISTS " + Item.TABLE_NAME + " (" +
                    Item._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    Item.COLUMN_NAME_NAME + " TEXT UNIQUE," +
                    Item.COLUMN_NAME_PRICE + " TEXT ," +
                    Item.COLUMN_NAME_FILENAME + " TEXT)";

    public static final String SQL_CREATE_TABLE_CATEGORY =
            "CREATE TABLE IF NOT EXISTS " + Category.TABLE_NAME + " (" +
                    Category._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    Category.COLUMN_NAME_NAME + " TEXT UNIQUE)";

    public static final String SQL_CREATE_TABLE_TAG =
            "CREATE TABLE IF NOT EXISTS " + Tag.TABLE_NAME + " (" +
                    Tag._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    Tag.COLUMN_NAME_ITEM_ID + " INTEGER REFERENCES " + Item.TABLE_NAME + "(" + Item._ID + ")," +
                    Tag.COLUMN_NAME_CATEGORY_ID + " INTEGER REFERENCES " + Category.TABLE_NAME + "(" + Category._ID + "))";



    // drop table statements
    public static final String SQL_DELETE_TABLE_ITEM =
            "DROP TABLE IF EXISTS " + Item.TABLE_NAME;

    public static final String SQL_DELETE_TABLE_CATEGORY =
            "DROP TABLE IF EXISTS " + Category.TABLE_NAME;

    public static  final String SQL_DELETE_TABLE_TAG =
            "DROP TABLE IF EXISTS " + Tag.TABLE_NAME;

}
