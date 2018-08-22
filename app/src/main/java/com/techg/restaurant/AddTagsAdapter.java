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
    private ArrayList<Category> categories;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private long items_checked;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AddTagsAdapter(SQLiteDatabase db, Context context, long item_id) {
        // fetch categories checked and unchecked and merge them
        ArrayList<Category> categories_checked = Category.getCategoriesOfItem(db, item_id, true);
        ArrayList<Category> categories_unchecked = Category.getCategoriesOfItem(db, item_id, false);
        this.items_checked = categories_checked.size();
        categories_checked.addAll(categories_unchecked);
        categories = categories_checked;
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
        if(position < items_checked)
            holder.checkBox.setChecked(true);
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
            checkBoxes.add(this.checkBox);
        }
    }

    public ArrayList<CheckBox> getCheckboxes(){
        return checkBoxes;
    }
    public long getNumberOfCheckedItems(){
        return items_checked;
    }
    public ArrayList<Category> getCategories() {
        return categories;
    }
}
