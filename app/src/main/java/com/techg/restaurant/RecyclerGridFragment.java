package com.techg.restaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class RecyclerGridFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerGridAdapter mAdapter;
    private Context context;
    private SQLiteDatabase db;
    private MenuDbHelper menuDbHelper;
    private String type;
    private ArrayList<Item> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_grid, container, false);
        context = getActivity().getApplicationContext();
        // database classes
        this.menuDbHelper = new MenuDbHelper(getActivity());
        this.db = menuDbHelper.getWritableDatabase();
        //getting arguments
        type = getArguments().getString("type");
        items = type.equals("untagged") ?
                Item.getAllUntaggedItemsFromDb(db) : Item.getAllItemsFromDb(db) ;

        // instantiating recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_grid);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerGridAdapter(context, db, type, getFragmentManager());
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
