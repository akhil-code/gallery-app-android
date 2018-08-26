package com.techg.restaurant;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.ViewHolder>{

    private Context context;
    private String type;
    private ArrayList<Item> items;
    private FragmentManager fm;

    public RecyclerGridAdapter(Context context, SQLiteDatabase db, String type, FragmentManager fm) {
        this.context = context;
        this.type = type;
        this.fm = fm;
        items = type.equals("untagged") ?
                Item.getAllUntaggedItemsFromDb(db) : Item.getAllItemsFromDb(db) ;
    }

    @Override
    public RecyclerGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.fragment_recycler_grid_row, parent, false);
        RecyclerGridAdapter.ViewHolder vh = new RecyclerGridAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerGridAdapter.ViewHolder holder, final int position) {
        final int img_pos_start = 3*position;
        Log.d("mytag", "items size: "+items.size());

        for(int i=0;i<3;i++){
            final int curr_position = img_pos_start + i;
            if(curr_position < items.size()){


                holder.imageView[i].setVisibility(View.VISIBLE);
                int resid = context.getResources().getIdentifier(items.get(curr_position).filename,"drawable",context.getPackageName());
//                holder.imageView[i].setImageResource(resid);

                Glide.with(context).load(resid).placeholder(R.drawable.ic_launcher_background).into(holder.imageView[i]);

                // on click listener
                holder.imageView[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    Intent intent = new Intent(context, ContentView.class);
                    intent.putExtra("position",curr_position);
                    intent.putExtra("type", type);
                    context.startActivity(intent);
                    }
                });
                // on long click listener
                holder.imageView[i].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment prev = fm.findFragmentByTag("dialog");
                        if (prev != null) {
                            ft.remove(prev);
                        }
                        ft.addToBackStack(null);

                        Bundle args = new Bundle();
                        args.putLong("item_id", items.get(curr_position).id);
                        args.putBoolean("editMode", false);
                        DialogFragment dialogFragment = new AddTagsFragment();
                        dialogFragment.setArguments(args);
                        dialogFragment.show(ft, "dialog_edit_tags");

                        return true;
                    }
                });
            }
            else{
                holder.imageView[i].setVisibility(View.INVISIBLE);
            }

        }
    }

    @Override
    public int getItemCount() {
        if(items.size()%3 == 0)
            return items.size()/3;
        return items.size()/3 + 1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView[] imageView = new ImageView[3];
        public View view;

        public ViewHolder(View v) {
            super(v);
            this.view = v;
            imageView[0] = (ImageView) v.findViewById(R.id.imageView1);
            imageView[1] = (ImageView) v.findViewById(R.id.imageView2);
            imageView[2] = (ImageView) v.findViewById(R.id.imageView3);
        }
    }

}



