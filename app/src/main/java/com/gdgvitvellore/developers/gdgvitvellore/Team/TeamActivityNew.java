package com.gdgvitvellore.developers.gdgvitvellore.Team;

/**
 * Created by shalini on 18-03-2015.
 */

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gdgvitvellore.developers.gdgvitvellore.Feeds.HomeActivity;
import com.gdgvitvellore.developers.gdgvitvellore.R;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


/**
 * Created by shalini on 18-02-2015.
 */
public class TeamActivityNew extends Fragment implements MaterialTabListener {
    private MaterialTabHost mtab;
    private final int NUM_PAGES=2;
    private ViewPager pager;
    private HomeActivity home;
    private TeamNavigationFragment teamNavigationFragment;
    private DrawerLayout drawerLayout;

    public TeamActivityNew(){

    }

    @SuppressLint("ValidFragment")
    public TeamActivityNew(HomeActivity hm){
         home=hm;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.team_layout, container, false);
        setup(rootView);
        return rootView;
    }

   public void setup(ViewGroup rv) {
        init(rv);
        teamNavigationFragment = (TeamNavigationFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.team_drawer_fragment);
       if(teamNavigationFragment==null)
       {
           Log.d("nav", "null");
       }
       teamNavigationFragment.setUp(drawerLayout,home.getToolbar());
        mtab.setAccentColor(getResources().getColor(android.R.color.white));
        SliderAdapter adapter=new SliderAdapter(getActivity().getSupportFragmentManager());
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
    private void init(ViewGroup v) {
        mtab=(MaterialTabHost)v.findViewById(R.id.materialTabHost);
        pager= (ViewPager) v.findViewById(R.id.contactpager1);

        drawerLayout = (DrawerLayout) v.findViewById(R.id.drawer_layout);


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
            tabs=getResources().getStringArray(R.array.tab_names_team);
        }
        @Override
        public Fragment getItem(int position) {

           return new TeamFragment(position,teamNavigationFragment,drawerLayout);

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
