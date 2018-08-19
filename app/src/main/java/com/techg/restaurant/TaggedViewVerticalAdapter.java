package com.techg.restaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TaggedViewVerticalAdapter extends RecyclerView.Adapter<TaggedViewVerticalAdapter.ViewHolder> {
    private SQLiteDatabase db;
    private ArrayList<Category> categories;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public TaggedViewVerticalAdapter(SQLiteDatabase db, Context context) {
        this.db = db;
        if(db != null)
            categories = Category.getAllCategories(db);
        this.context = context;
    }

    @Override
    public TaggedViewVerticalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.tagged_vertical_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(categories.get(position).name);

        holder.horizontalRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.horizontalRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter horizontalAdapter = new TaggedViewHorizontalAdapter(db, categories.get(position), context);
        holder.horizontalRecyclerView.setAdapter(horizontalAdapter);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public View view;
        public RecyclerView horizontalRecyclerView;

        public ViewHolder(View v) {
            super(v);
            this.view = v;
            title = (TextView) v.findViewById(R.id.vertical_view_title);
            horizontalRecyclerView = (RecyclerView) v.findViewById(R.id.horizontal_container);

        }
    }

}