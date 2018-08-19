package com.techg.restaurant;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTagsAdapter extends RecyclerView.Adapter<AddTagsAdapter.ViewHolder> {
    private SQLiteDatabase db;
    private Context context;
    private ArrayList<Category> categories;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AddTagsAdapter(SQLiteDatabase db, Context context) {
        this.db = db;
        this.context = context;
        this.categories = Category.getAllCategories(db);
    }

    @Override
    public AddTagsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.tag_checkbox, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.checkBox.setText(categories.get(position).name);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public CheckBox checkBox;

        public ViewHolder(View v) {
            super(v);
            this.view = v;
            this.checkBox = (CheckBox) v.findViewById(R.id.checkBox_item);
            AddTagsFragment.checkBoxes.add(this.checkBox);
        }
    }

}
