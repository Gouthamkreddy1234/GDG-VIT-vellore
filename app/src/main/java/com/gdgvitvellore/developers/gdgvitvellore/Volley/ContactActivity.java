package com.gdgvitvellore.developers.gdgvitvellore.Volley;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.tabs.SlidingTabActivity;

/**
 * Created by shalini on 18-02-2015.
 */
public class ContactActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private final int NUM_PAGES=2;
    private SlidingTabActivity tabmain;
    private ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);
        init();
        pager.setAdapter(new SliderAdapter(getSupportFragmentManager()));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabmain.setViewPager(pager);
        tabmain.setDistributeEvenly(true);
        tabmain.setCustomTabView(R.layout.customtablayout,R.id.customtext);
    }

    private void init() {
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        tabmain=(SlidingTabActivity)findViewById(R.id.tablayout);
        pager= (ViewPager) findViewById(R.id.contactpager);


    }
    private class SliderAdapter extends FragmentStatePagerAdapter {
        String[] tabs;
        public SliderAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
            tabs=getResources().getStringArray(R.array.tab_names_contact);
        }
        @Override
        public Fragment getItem(int position) {

            if(position==0)
            {
                return (new SliderContactFragment());
            }
            else
            {
                return (new SliderMapFragment());
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tabs[position];
        }

        @Override

        public int getCount() {
            return NUM_PAGES;
        }
    }


}
