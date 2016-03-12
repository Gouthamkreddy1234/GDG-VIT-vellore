package com.gdgvitvellore.developers.gdgvitvellore.Volley;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;

/**
 * Created by shalini on 24-02-2015.
 */
public class GalleryFragment extends Fragment {
    private String image_url;
    ImageView im;
    public GalleryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.gallery_fragment, container, false);
                image_url=getArguments().getString("imageuri","");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        im=(ImageView)view.findViewById(R.id.galleryimage);
        AppController.getInstance().getImageLoader().get(image_url, ImageLoader.getImageListener(im, R.drawable.cat, R.drawable.hippo));
    }
}
