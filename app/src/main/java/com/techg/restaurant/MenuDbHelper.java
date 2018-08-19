package com.techg.restaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MenuDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Menu.db";

    public MenuDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MenuContract.SQL_CREATE_TABLE_ITEM);
        db.execSQL(MenuContract.SQL_CREATE_TABLE_CATEGORY);
        db.execSQL(MenuContract.SQL_CREATE_TABLE_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(MenuContract.SQL_DELETE_TABLE_ITEM);
        db.execSQL(MenuContract.SQL_DELETE_TABLE_CATEGORY);
        db.execSQL(MenuContract.SQL_DELETE_TABLE_TAG);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
