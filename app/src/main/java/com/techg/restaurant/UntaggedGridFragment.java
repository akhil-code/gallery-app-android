package com.techg.restaurant;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class UntaggedGridFragment extends Fragment{
    private SQLiteDatabase db;
    private MenuDbHelper menuDbHelper;
    private Context mContext;
    String type;

    private GridView gridView;
    private GridViewAdapter mAdapter;
    private ArrayList<Item> items;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // inflating view
        View view = inflater.inflate(R.layout.fragment_untagged_grid, container, false);
        this.mContext = getActivity().getApplicationContext();
        // database classes
        this.menuDbHelper = new MenuDbHelper(getActivity());
        this.db = menuDbHelper.getWritableDatabase();
        // grid view initialisation
        type = getArguments().getString("type");
        gridView = (GridView) view.findViewById(R.id.gridView);
        mAdapter = new GridViewAdapter(mContext, this.db, type);
        gridView.setAdapter(mAdapter);

        if(type.equals("untagged")){
            items = Item.getAllUntaggedItemsFromDb(db);
        }
        else{
            items = Item.getAllItemsFromDb(db);
        }


        // on click listeners
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ContentView.class);
                intent.putExtra("position",position);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });

        // on long click listener
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                if(type.equals("untagged")){

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    Bundle args = new Bundle();
                    args.putLong("item_id", items.get(position).id);
                    DialogFragment dialogFragment = new AddTagsFragment();
                    dialogFragment.setArguments(args);
                    dialogFragment.show(ft, "dialog");

                }
                return true;
            }
        });

        return view;
    }

}
