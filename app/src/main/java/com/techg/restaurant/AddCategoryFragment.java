package com.techg.restaurant;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class AddCategoryFragment extends DialogFragment {
    private EditText editText;
    private SQLiteDatabase db;
    private AddNewTagListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_category, container, false);

        // database objects
        MenuDbHelper menuDbHelper = new MenuDbHelper(getActivity().getApplicationContext());
        db = menuDbHelper.getWritableDatabase();

        // setting on click listener
        editText = (EditText) view.findViewById(R.id.new_category_field);
        Button button = (Button) view.findViewById(R.id.button_add_category);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTagToDatabase();
            }
        });



        return view;
    }

    public void addTagToDatabase(){
        String category = editText.getText().toString().trim();
        if(!category.equals("")){
            Category.insertCategoryToDb(db, category);
            listener.newTagAdded();
            dismiss();
        }
    }

    public interface AddNewTagListener{
        public void newTagAdded();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            listener = (AddNewTagListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

}
