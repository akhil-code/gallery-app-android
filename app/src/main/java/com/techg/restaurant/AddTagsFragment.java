package com.techg.restaurant;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

public class AddTagsFragment extends DialogFragment {
    private Context context;
    SQLiteDatabase db;
    AddTagsAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_tags, container, false);
        context = getActivity().getApplicationContext();


        MenuDbHelper menuDbHelper = new MenuDbHelper(context);
        this.db = menuDbHelper.getWritableDatabase();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.tags_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AddTagsAdapter(db, context);
        recyclerView.setAdapter(mAdapter);

        Button button = view.findViewById(R.id.add_tags_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTags(view);
            }
        });

        return view;
    }

    public void addTags(View v){
        long item_id = getArguments().getLong("item_id");
        Log.d("mytag", "item_id: "+item_id);
        Item item = Item.getItemFromDb(db,item_id);

        ArrayList<CheckBox> checkBoxes = mAdapter.getCheckboxes();
        Log.d("mytag", "checkboxes: "+checkBoxes.size());

        for(int i=0; i<checkBoxes.size(); i++){
            Log.d("mytag", "box-> "+checkBoxes.get(i).getText().toString());
            if(checkBoxes.get(i).isChecked()){
                Log.d("mytag", "addTags: selected");
                Category category = Category.getAllCategories(db).get(i);
                Tag.insertTagToDb(db, item, category);
            }
        }

        checkBoxes.clear();
        this.dismiss();
    }

}
