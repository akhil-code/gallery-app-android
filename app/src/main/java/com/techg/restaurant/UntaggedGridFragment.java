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
    String type;

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
        type = getArguments().getString("type");
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
                if(type.equals("untagged")){

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    DialogFragment dialogFragment = new AddTagsFragment();
                    dialogFragment.show(ft, "dialog");

                }
                return true;
            }
        });

        return view;
    }

}
