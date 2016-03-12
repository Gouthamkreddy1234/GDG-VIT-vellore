package com.gdgvitvellore.developers.gdgvitvellore.Volley;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.gdgvitvellore.developers.gdgvitvellore.R;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by shalini on 18-02-2015.
 */
public class ContactActivityNew extends ActionBarActivity implements MaterialTabListener ,View.OnClickListener{
    private MaterialTabHost mtab;
    private Toolbar toolbar;
    private final int NUM_PAGES=2;
    private ViewPager pager;
    private LinearLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout1);
        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.rest)));
        mtab.setAccentColor(getResources().getColor(android.R.color.white));
        SliderAdapter adapter=new SliderAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        mtab.setAccentColor(getResources().getColor(R.color.colorPrimary));
        mtab.setTextColor(getResources().getColor(android.R.color.white));
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mtab.setSelectedNavigationItem(position);
            }
        });
        for(int i=0;i<adapter.getCount();i++)
        {
            mtab.addTab(
                    mtab.newTab()
                    .setText(adapter.getPageTitle(i))
                    .setTabListener(this)
            );
        }

    }
    private void init() {
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        mtab=(MaterialTabHost)findViewById(R.id.materialTabHost);
        pager= (ViewPager) findViewById(R.id.contactpager1);


    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        pager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                finish();
            default:
                break;

        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.home:
                finish();
                break;
            default:
                break;

        }
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
