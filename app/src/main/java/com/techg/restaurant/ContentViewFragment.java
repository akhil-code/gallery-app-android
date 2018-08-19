package com.techg.restaurant;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ContentViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content_view, container, false);
        Context context = getActivity().getApplicationContext();

        String filename = getArguments().getString("filename","img1");
        ImageView imageView = (ImageView) view.findViewById(R.id.content_image_view);
        int resid = context.getResources().getIdentifier(filename,"drawable",
                                                            context.getPackageName());

        getActivity().getApplicationContext();
        imageView.setImageResource(resid);
        return view;
    }


}
