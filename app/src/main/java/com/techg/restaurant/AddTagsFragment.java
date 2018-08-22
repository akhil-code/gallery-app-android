package com.techg.restaurant;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTagsFragment extends DialogFragment {
    private Context context;
    SQLiteDatabase db;
    AddTagsAdapter mAdapter;
    private addTagListener listener;
    private long item_id;
    private boolean editMode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflating fragment view and getting context
        View view = inflater.inflate(R.layout.fragment_add_tags, container, false);
        context = getActivity().getApplicationContext();
        // database helper objects
        MenuDbHelper menuDbHelper = new MenuDbHelper(context);
        this.db = menuDbHelper.getWritableDatabase();
        // fetching item id sent via fragment bundle
        item_id = getArguments().getLong("item_id");
        editMode = getArguments().getBoolean("editMode");

        // inflating recycler view to display checkbox items
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.tags_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AddTagsAdapter(db, context, item_id);
        recyclerView.setAdapter(mAdapter);

        // setting onclick listener for save button
        Button button = view.findViewById(R.id.add_tags_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyTags(view);
            }
        });
        // setting onclick listener for new category
        ImageView imageView = (ImageView) view.findViewById(R.id.new_category_imageview);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCategoryDialog(view);
            }
        });

        return view;
    }

    public void modifyTags(View v){
        Item item = Item.getItemFromDb(db,item_id);
        // get checkboxes and category list from adapter class
        ArrayList<CheckBox> checkBoxes = mAdapter.getCheckboxes();
        ArrayList<Category> categories = mAdapter.getCategories();

        // if executed from untagged page
        if(!editMode){
            for(int i=0; i<checkBoxes.size(); i++){
                if(checkBoxes.get(i).isChecked()){
                    Tag.insertTagToDb(db, item, categories.get(i));
                }
            }
        }
        // if executed from tagged page
        else{
            long checked_items = mAdapter.getNumberOfCheckedItems();
            for(int i=0;i<checkBoxes.size();i++){
                // if new categories are checked
                if(i >= checked_items && checkBoxes.get(i).isChecked())
                    Tag.insertTagToDb(db, item, categories.get(i));
                // if already tagged categories are unchecked i.e removed
                else if(i < checked_items && !checkBoxes.get(i).isChecked())
                    Tag.deleteTag(db, item.id, categories.get(i).id);
            }
        }

        // clean up
        checkBoxes.clear();     // clean up
        listener.tagsAdded();   // refresh view pager via main activity
        this.dismiss();         // dismiss the fragment
    }

    public void newCategoryDialog(View view){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog_edit_tags");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment dialogFragment = new AddCategoryFragment();
        dialogFragment.show(ft, "dialog");

    }


    // Container Activity must implement this interface
    public interface addTagListener {
        public void tagsAdded();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (addTagListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }



}
