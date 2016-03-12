package com.gdgvitvellore.developers.gdgvitvellore.Events;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Events.Eventsweb;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;
import com.melnykov.fab.FloatingActionButton;

/**
 * Created by Gautam on 3/17/2015.
 */
public class Extendedevent extends ActionBarActivity implements View.OnClickListener {
    TextView title,date;
    ImageView imageView,imagebuton;
    ImageLoader imageLoader;
    Toolbar toolbar;
    FloatingActionButton fab ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extendedevents);
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.events)));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        actionBar.setTitle("Events");


        title =(TextView)findViewById(R.id.eventname);
        date =(TextView)findViewById(R.id.eventtime);
        imageView=(ImageView)findViewById(R.id.projectimage);
        fab= (FloatingActionButton) findViewById(R.id.fab);
        imageLoader= AppController.getInstance().getImageLoader();
        Bundle bundle = getIntent().getExtras();
        //-----------------------------

        //----------------------------

        String imageurl=bundle.getString("imageurl");
        String date1 = bundle.getString("date");
        final String title1 = bundle.getString("name");

        imageLoader.get(imageurl,ImageLoader.getImageListener(imageView,R.drawable.round_3,R.drawable.round_2));
        title.setText(title1);
        date.setText(date1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("http://www.gdgvitvellore.com/"));*/

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),Eventsweb.class);
                startActivity(intent);//Fab works !!in the place of this
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            /*case R.id.location:
                break;*/
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }


}
