package com.techg.restaurant;

import android.content.Context;
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
        this.menuDbHelper = new MenuDbHelper(getActivity());
        this.db = menuDbHelper.getWritableDatabase();

        GridView gridview = (GridView) view.findViewById(R.id.gridView);
        gridview.setAdapter(new GridViewAdapter(mContext, this.db));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "" + position,Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
