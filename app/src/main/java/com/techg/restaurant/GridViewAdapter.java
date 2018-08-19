package com.techg.restaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private SQLiteDatabase db;
    private ArrayList<Item> items;
    public GridViewAdapter(Context context, SQLiteDatabase db, String type) {
        mContext = context;
        this.db = db;

        if(type.equals("allitems"))
            this.items = Item.getAllItemsFromDb(db);
        else if(type.equals("untagged"))
            this.items = Item.getAllUntaggedItemsFromDb(db);
        else
            this.items = null;
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(320, 320));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(4, 4, 4, 4);
        }
        else {
            imageView = (ImageView) convertView;
        }

        int resid = mContext.getResources().getIdentifier(items.get(position).filename,"drawable",mContext.getPackageName());
        imageView.setImageResource(resid);
        return imageView;
    }

}
