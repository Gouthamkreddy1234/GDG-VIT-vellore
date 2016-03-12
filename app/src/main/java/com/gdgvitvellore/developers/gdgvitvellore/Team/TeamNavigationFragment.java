package com.gdgvitvellore.developers.gdgvitvellore.Team;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;

/**
 * Created by shalini on 19-03-2015.
 */
public class TeamNavigationFragment  extends Fragment {

        private DrawerLayout drawerLayout;
        private Toolbar toolbar;
        private TextView name,des,phone,mail;
        private ImageView pic;
        private ActionBarDrawerToggle drawerToggle;
        public TeamNavigationFragment(){

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.team_drawer_fragment, container, false);
            init(rootView);
            return rootView;
        }

    private void init(ViewGroup rv) {
        name=(TextView)rv.findViewById(R.id.team_drawer_name);
        des=(TextView)rv.findViewById(R.id.team_drawer_desg);
        phone=(TextView)rv.findViewById(R.id.team_drawer_phone);
        mail=(TextView)rv.findViewById(R.id.team_drawer_mail);
        pic=(ImageView)rv.findViewById(R.id.dispic);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TeamInfo info=new TeamInfo();
        info.name="Name";
        info.desg="Designation";
        info.phone="Phone";
        info.mail="Email";
        info.url="http://i.stack.imgur.com/Sv4BC.png";
        setContent(info);

    }

    public void setContent(TeamInfo info) {
        name.setText(info.name);
        des.setText(info.desg);
        phone.setText(info.phone);
        mail.setText(info.mail);
        AppController.getInstance().getImageLoader().get(info.url,ImageLoader.getImageListener(pic, R.drawable.loader, R.drawable.hippo));
    }

    public void setUp(DrawerLayout dl,Toolbar t) {
            toolbar=t;
            drawerLayout=dl;
            /*drawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,t,R.string.draweropen,R.string.drawerclose) {


                @Override
                public void onDrawerOpened(View drawerView) {

                    super.onDrawerOpened(drawerView);
                    getActivity().invalidateOptionsMenu();
                }

                @Override
                public void onDrawerClosed(View drawerView) {

                    super.onDrawerClosed(drawerView);
                    getActivity().invalidateOptionsMenu();
                }

            /*@Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if(slideOffset<0.6)
                toolbar.setAlpha(1-slideOffset);
            }*/
            //};
            //drawerLayout.setDrawerListener(drawerToggle);
        }



    }


