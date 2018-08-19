package com.techg.restaurant;

import android.provider.BaseColumns;

public class MenuContract {
    private MenuContract(){}

    public static class Item implements BaseColumns{
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PRICE = "price";
    }

    public static class Category implements BaseColumns{
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME_NAME = "name";
    }

    public static final String SQL_CREATE_TABLE_ITEM =
                    "CREATE TABLE IF NOT EXISTS " + Item.TABLE_NAME + " (" +
                    Item._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    Item.COLUMN_NAME_NAME + " TEXT," +
                    Item.COLUMN_NAME_PRICE + " TEXT)";

    public static final String SQL_CREATE_TABLE_CATEGORY =
            "CREATE TABLE IF NOT EXISTS " + Category.TABLE_NAME + " (" +
                    Category._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    Category.COLUMN_NAME_NAME + " TEXT)";

    public static final String SQL_DELETE_TABLE_ITEM =
            "DROP TABLE IF EXISTS " + Item.TABLE_NAME;

    public static final String SQL_DELETE_TABLE_CATEGORY =
            "DROP TABLE IF EXISTS " + Item.TABLE_NAME;

}
