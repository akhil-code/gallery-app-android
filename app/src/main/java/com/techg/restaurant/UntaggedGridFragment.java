package com.techg.restaurant;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class UntaggedGridFragment extends Fragment {
    private SQLiteDatabase db;
    private MenuDbHelper menuDbHelper;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflating view
        View view = inflater.inflate(R.layout.fragment_untagged_grid, container, false);
        this.mContext = getActivity().getApplicationContext();
        // database classes
        this.menuDbHelper = new MenuDbHelper(getActivity());
        this.db = menuDbHelper.getWritableDatabase();
        // grid view initialisation
        final String type = getArguments().getString("type");
        GridView gridview = (GridView) view.findViewById(R.id.gridView);
        gridview.setAdapter(new GridViewAdapter(mContext, this.db, type));
        // on click listeners
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ContentView.class);
                intent.putExtra("position",position);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });

        // on long click listener
        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                Toast.makeText(mContext, ""+position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return view;
    }

}
