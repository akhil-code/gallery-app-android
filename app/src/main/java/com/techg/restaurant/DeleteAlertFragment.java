package com.techg.restaurant;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DeleteAlertFragment extends DialogFragment {

    private DeleteCategoryListener listener;

    public static DeleteAlertFragment newInstance(String title, long category_id) {
        DeleteAlertFragment frag = new DeleteAlertFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putLong("category_id", category_id);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        final long category_id = getArguments().getLong("category_id");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                doPositiveClick(category_id);
                            }
                        }
                )
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                doNegativeClick();
                            }
                        }
                )
                .create();
    }


    public void doPositiveClick(long category_id) {
        // database objects
        MenuDbHelper menuDbHelper = new MenuDbHelper(getActivity().getApplicationContext());
        SQLiteDatabase db = menuDbHelper.getWritableDatabase();
        Tag.deleteTagsOfCategory(db, category_id);
        Category.deleteCategory(db, category_id);
        listener.onDeleteCategory();
    }

    public void doNegativeClick() {
        this.dismiss();
    }

    public interface DeleteCategoryListener{
        public void onDeleteCategory();
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            listener = (DeleteCategoryListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


}
