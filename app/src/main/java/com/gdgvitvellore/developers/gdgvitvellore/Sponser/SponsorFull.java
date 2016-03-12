package com.gdgvitvellore.developers.gdgvitvellore.Sponser;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdgvitvellore.developers.gdgvitvellore.R;

/**
 * Created by Gautam on 3/15/2015.
 */
public class SponsorFull extends ActionBarActivity implements View.OnClickListener{

    String color;
   String details;
    TextView title,content;
    LinearLayout bg;
    private Toolbar toolbar;
    String[] s={"Logo on Event Banner & Poster\nOnline Promotion\nGive-Away Events\nSpecial Mention in Videos/Website/nLogo on Team Apparel\nNaming Rights","Logo on Event Banner & Poster\nOnlinePromotion","Logo on Event Banner & Poster\nOnlinePromotion\nGive-Away Events","Logo on Event Banner & Poster\nOnlinePromotion","Logo on Event Banner & Poster\nOnlinePromotion\nGive-Away Events\nSpecial Mention in Videos/Website","Logo on Event Banner & Poster\nOnline Promotion\nGive-Away Events\nSpecial Mention in Videos/Website/nLogo on Team Apparel"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sponsor_full);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        bg=(LinearLayout)findViewById(R.id.linearlayout);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("");
        TextView title = (TextView)findViewById(R.id.title);
        TextView content = (TextView)findViewById(R.id.content);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        title.setTypeface(tf);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if(extras.containsKey("gold"))
            {
                bg.setBackgroundColor(getResources().getColor(R.color.gold));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gold)));
                 color= extras.getString("gold");

                content.setText(s[0]);
                title.setText("Gold Circle");
            }
            else if(extras.containsKey("red"))
            {bg.setBackgroundColor(getResources().getColor(R.color.red));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red)));
                color= extras.getString("red");


                content.setText(s[1]);
                title.setText("Red Circle");
            }
            else if(extras.containsKey("blue"))
            {
                bg.setBackgroundColor(getResources().getColor(R.color.blue));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
                color= extras.getString("blue");


                content.setText(s[3]);
                title.setText("Blue Circle");
            }
            else if(extras.containsKey("green"))
             {
                 bg.setBackgroundColor(getResources().getColor(R.color.green));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));
                color= extras.getString("green");


                content.setText(s[4]);
                title.setText("Green Circle");
            }
            else if(extras.containsKey("yellow"))
            {
                bg.setBackgroundColor(getResources().getColor(R.color.yellow));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yellow)));
                color= extras.getString("yellow");

                content.setText(s[5]);

                title.setText("Yellow Circle");
            }



        }


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



}
