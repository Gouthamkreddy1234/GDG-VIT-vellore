package com.gdgvitvellore.developers.gdgvitvellore.Volley;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;
import com.gdgvitvellore.developers.gdgvitvellore.R;

/**
 * Created by shalini on 24-02-2015.
 */
public class GalleryActivity extends FragmentActivity {
    Toolbar toolbar;
    LinearLayout closeButton;
    ViewPager pager;
    private int NUM_PAGES=0,current=0;
    private String[] images_urls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_layout);
        images_urls=getIntent().getBundleExtra("imagesbundle").getStringArray("imagesarray");
        if(getIntent().getBundleExtra("imagesbundle").containsKey("currentimage"))
        {
            current=getIntent().getBundleExtra("imagesbundle").getInt("currentimage");
        }
        NUM_PAGES=images_urls.length;
        pager= (ViewPager) findViewById(R.id.gallerypager);
        GalleryAdapter adapter=new GalleryAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(current);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        closeButton=(LinearLayout)findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.getInstance().cancelPendingRequests(AppController.TAG);
                for(int i=0;i<images_urls.length;i++)
                AppController.getInstance().getRequestQueue().getCache().remove(images_urls[i]);
                finish();

            }
        });

    }
    private class GalleryAdapter extends FragmentStatePagerAdapter {
        String[] tabs;
        public GalleryAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }
        @Override
        public Fragment getItem(int position) {

            GalleryFragment galleryFragment= new GalleryFragment();
            Bundle i=new Bundle();
            i.putString("imageuri",images_urls[position]);
            galleryFragment.setArguments(i);
            return galleryFragment;
        }


        @Override

        public int getCount()
        {
            return NUM_PAGES;
        }
    }
}
