package com.techg.restaurant;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class TaggedView extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_tagged_view, container, false);
        context = getActivity().getApplicationContext();
        MenuDbHelper menuDbHelper = new MenuDbHelper(context);
        SQLiteDatabase db = menuDbHelper.getWritableDatabase();

        recyclerView = (RecyclerView) view.findViewById(R.id.vertical_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new TaggedViewVerticalAdapter(db, context, getFragmentManager());
        recyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = view.findViewById(R.id.fab);
        ViewCompat.setElevation(fab, 30);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCategoryDialog(view);
            }
        });

        return view;
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


}
