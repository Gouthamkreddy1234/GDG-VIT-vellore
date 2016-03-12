package com.gdgvitvellore.developers.gdgvitvellore.Navigation;

import android.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.gdgvitvellore.developers.gdgvitvellore.AboutUs.AboutUs;
import com.gdgvitvellore.developers.gdgvitvellore.AboutUs.AboutUsActivity;
import com.gdgvitvellore.developers.gdgvitvellore.Events.SponsorMain;
import com.gdgvitvellore.developers.gdgvitvellore.Feeds.FeedsActivity;
import com.gdgvitvellore.developers.gdgvitvellore.Feeds.HomeActivity;
import com.gdgvitvellore.developers.gdgvitvellore.Login.LoginActivity;
import com.gdgvitvellore.developers.gdgvitvellore.Login.MainFragment;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Sponser.Sponser;
import com.gdgvitvellore.developers.gdgvitvellore.Team.TeamActivity;
import com.gdgvitvellore.developers.gdgvitvellore.Team.TeamActivityNew;
import com.gdgvitvellore.developers.gdgvitvellore.Volley.ContactActivityNew;
import com.gdgvitvellore.developers.gdgvitvellore.Volley.FeedbackActivity;
import com.gdgvitvellore.developers.gdgvitvellore.Project.ProjectActivity;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shalini on 23-02-2015.
 */
public class NavigationDrawerFragment extends Fragment implements NavigationDrawerAdapter.ClickListener {
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private NavigationDrawerAdapter adapter;
    private Toolbar toolbar;
    private TextView actorName;
    private HomeActivity home;
    private ActionBarDrawerToggle drawerToggle;
    private FrameLayout fl;
    private ArrayList<String> m;
    CircularImageView dp;
    private FrameLayout popup;
    PopupMenu popup1;
    private ImageLoader imageLoader;
    private String menuTag="";
    private String social="GUEST";

    public NavigationDrawerFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.drawer_fragment, container, false);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.drawer_recycler_view);
        adapter = new NavigationDrawerAdapter(getActivity(),getData());
        actorName=(TextView)rootView.findViewById(R.id.actor_name);
        imageLoader = AppController.getInstance().getImageLoader();
        fl=(FrameLayout)rootView.findViewById(R.id.drawer_frame);
        popup=(FrameLayout)rootView.findViewById(R.id.popup);
        popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Creating the instance of PopupMenu
                   popup1 = new PopupMenu(getActivity(),popup);
                    //Inflating the Popup using xml file
                    popup1.getMenuInflater()
                            .inflate(R.menu.popup, popup1.getMenu());
                    popup1.getMenu().getItem(0).setTitle(menuTag);

                    //registering popup with OnMenuItemClickListener
                    popup1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(
                                    getActivity(),
                                    "You Clicked : " + item.getTitle(),
                                    Toast.LENGTH_SHORT
                            ).show();
                            if(item.getTitle().equals("Login")){
                                Intent intent=new Intent(getActivity(), LoginActivity.class);
                                intent.putExtra("action","Login");
                                intent.putExtra("social",social);
                                startActivity(intent);
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(
                                        getActivity(),"Logout",
                                        Toast.LENGTH_SHORT
                                ).show();
                                MainFragment.callFacebookLogout(getActivity());
                                MainFragment.signOutFromGplus();
                                Intent intent=new Intent(getActivity(), LoginActivity.class);
                                intent.putExtra("action","Logout");
                                intent.putExtra("social",social);
                                startActivity(intent);
                                getActivity().finish();
                            }
                            return true;
                        }
                    });

                    popup1.show(); //showing popup menu
                }
            }); //closing the setOnClickListener method


        dp=(CircularImageView)rootView.findViewById(R.id.dp);
        //spinner=(Spinner)rootView.findViewById(R.id.actionlist);
        /*ArrayAdapter<String> dataAdapter;
        m=new ArrayList<String>();
        m.add("Guest");
        m.add("Logout");
        dataAdapter=new ArrayAdapter<String>(getActivity(),R.layout.simple_spinner_item, m);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dopdown_item);
        spinner.setAdapter(dataAdapter);*/
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto_Medium.ttf");
        actorName.setTypeface(tf);
        actorName.setText("GUEST");
        dp.setImageResource(R.drawable.ic_perm_contact);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }
    public static List<NavigationDrawerInfo> getData(){
        List<NavigationDrawerInfo> list=new ArrayList<>();
        String[] text={"Feeds","Events & Registrations","Projects","Sponsors","About GDG","Inner Circle","Contact Us","Feedback"};
        int[] icon={R.drawable.ic_navbar_feeds, R.drawable.ic_navbar_events, R.drawable.ic_navbar_projects, R.drawable.ic_navbar_sponsors, R.drawable.ic_info, R.drawable.ic_group, R.drawable.ic_perm_contact, R.drawable.ic_feedback};
        for(int i=0;i<text.length;i++){
            NavigationDrawerInfo info=new NavigationDrawerInfo();
            info.name=text[i];
            info.iconId=icon[i];
            list.add(info);
        }
        return list;

    }

    public void setUp(DrawerLayout dl,Toolbar t,HomeActivity hm) {
        toolbar=t;
        home=hm;
        drawerLayout=dl;
        drawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,t, R.string.draweropen, R.string.drawerclose) {

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
        };
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
        drawerLayout.setDrawerListener(drawerToggle);
        actorName.setText("GUEST");
        menuTag="Login";
        dp.setImageResource(R.drawable.contacts);
    }

    @Override
    public void onRecyclerItemClick(View v, int position) {

        if(position==6){drawerLayout.closeDrawers();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getActivity().startActivity(new Intent(getActivity(),ContactActivityNew.class));
                }
            }).start();
        }
        if(position==2) {
            drawerLayout.closeDrawers();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment projects = new ProjectActivity();
            ft.replace(R.id.fragment_holder, projects);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
            home.setToolbarFormat(2);
        }
        if(position==1) {
            drawerLayout.closeDrawers();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment sponsors = new SponsorMain(getActivity());
            ft.replace(R.id.fragment_holder, sponsors);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
            home.setToolbarFormat(1);
        }
        if(position==0) {
            drawerLayout.closeDrawers();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment feeds = new FeedsActivity(home);
            ft.replace(R.id.fragment_holder, feeds);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
            home.setToolbarFormat(0);

        }

        if(position==3) {

            drawerLayout.closeDrawers();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    Fragment sponsor = new Sponser();
                    ft.replace(R.id.fragment_holder, sponsor);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();

                }
            }).start();
            home.setToolbarFormat(3);
        }
        if(position==5) {
            drawerLayout.closeDrawers();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getActivity().startActivity(new Intent(getActivity(), TeamActivity.class));
                }
            }).start();
            /*drawerLayout.closeDrawers();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment team = new TeamActivityNew(home);
            ft.replace(R.id.fragment_holder,team);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
            home.setToolbarFormat(3);*/

        }
        if(position==7) {
            drawerLayout.closeDrawers();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getActivity().startActivity(new Intent(getActivity(), FeedbackActivity.class));
                }
            }).start();
        }
        if(position==4) {
            drawerLayout.closeDrawers();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getActivity().startActivity(new Intent(getActivity(), AboutUs.class));
                }
            }).start();
        }
        }

    public void setUp(DrawerLayout dl, Toolbar t, HomeActivity hm, Bundle bundle) {
        toolbar=t;
        home=hm;
        drawerLayout=dl;
        drawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,t, R.string.draweropen, R.string.drawerclose) {

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
        };
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
        drawerLayout.setDrawerListener(drawerToggle);
        actorName.setText(bundle.get("name").toString());
        imageLoader.get(bundle.getString("image"), ImageLoader.getImageListener(dp,R.drawable.contacts,R.drawable.round_3));
        menuTag="Logout";
        social=bundle.get("social").toString();
    }
}
