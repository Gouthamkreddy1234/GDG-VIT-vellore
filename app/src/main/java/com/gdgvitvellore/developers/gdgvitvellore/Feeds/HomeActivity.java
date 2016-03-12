package com.gdgvitvellore.developers.gdgvitvellore.Feeds;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gdgvitvellore.developers.gdgvitvellore.Navigation.NavigationDrawerFragment;
import com.gdgvitvellore.developers.gdgvitvellore.Volley.GalleryActivity;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ActivitiesFeedPage;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ParsedActivity;
import com.gdgvitvellore.developers.gdgvitvellore.Volley.SplashActivity;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//gotta rebuild after syncing sometimes
public class HomeActivity extends ActionBarActivity {


    Toolbar toolbar;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feeds");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i=getIntent();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.feedcolor)));
        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        if(i.hasExtra("fbBundle")){
            navigationDrawerFragment.setUp(drawerLayout, toolbar,this,i.getBundleExtra("fbBundle"));
        }
        else if(i.hasExtra("gpBundle")){
            navigationDrawerFragment.setUp(drawerLayout, toolbar,this,i.getBundleExtra("gpBundle"));
        }
        else{
            navigationDrawerFragment.setUp(drawerLayout, toolbar,this);
        }
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.attachToRecyclerView(recList);//done attachment
        //-----------------------------------------------------

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment feeds = new FeedsActivity(this);
        ft.replace(R.id.fragment_holder, feeds);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();

    }
    public void setToolbarFormat(int pos){
        String title[]={"Feeds","Events","Projects","Sponsors"};
        ColorDrawable colors[]=new ColorDrawable[4];
        colors[0]=new ColorDrawable(getResources().getColor(R.color.feedcolor));
        colors[1]=new ColorDrawable(getResources().getColor(R.color.events));
        colors[2]=new ColorDrawable(getResources().getColor(R.color.procolor));
        colors[3]=new ColorDrawable(getResources().getColor(R.color.sponser));
        getSupportActionBar().setBackgroundDrawable(colors[pos]);
        getSupportActionBar().setTitle(title[pos]);
    }
    public void toggleActionBar(int tog){

        if(tog==0){
           getSupportActionBar().show();
        }
        else{
            getSupportActionBar().hide();
        }
    }
public Toolbar getToolbar(){
    return toolbar;
}






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
//who are yout oa ask me ??this is what you need.