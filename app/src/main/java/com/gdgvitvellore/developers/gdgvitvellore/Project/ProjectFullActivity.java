package com.gdgvitvellore.developers.gdgvitvellore.Project;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ProjectsParser;
import com.gdgvitvellore.developers.gdgvitvellore.R;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by shalini on 18-02-2015.
 */
public class ProjectFullActivity extends ActionBarActivity implements MaterialTabListener {
    private MaterialTabHost mtab;
    private Toolbar toolbar;
    private final int NUM_PAGES=3;
    private ViewPager pager;
    private ProjectsParser projectsParser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout1);
        init();
        toolbar=(Toolbar)findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Projects");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.procolor)));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        projectsParser= AppController.getInstance().getProjectParser();
        mtab.setAccentColor(getResources().getColor(android.R.color.white));
        mtab.setPrimaryColor(getResources().getColor(R.color.procolor));
        SliderAdapter adapter=new SliderAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
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



    private class SliderAdapter extends FragmentStatePagerAdapter {
        String[] tabs;
        public SliderAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
            tabs=getResources().getStringArray(R.array.tab_names_project);
        }
        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new ProjectDescriptionFragment(projectsParser);
                case 1:
                    return new ProjectDetailsFragment(projectsParser);
                case 2:
                    return new ProjectGalleryFragment(projectsParser);
            }
            return null;

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
