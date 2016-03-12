package com.gdgvitvellore.developers.gdgvitvellore.Events;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ActivitiesFeedPage;

/**
 * Created by Gautam on 3/19/2015.
 */
public class Eventsweb extends ActionBarActivity implements View.OnClickListener{

    WebView browser;
    private String url="http://www.gdgvitvellore.com/";
    ProgressBar progressBar;
    Toolbar toolbar;
    String heading="GDG VIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.eventsweb);
        browser =(WebView)findViewById(R.id.webView);
        progressBar=(ProgressBar)findViewById(R.id.progressBar3);
        progressBar.setVisibility(ProgressBar.VISIBLE);
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
        if(getIntent().hasExtra("title")) {
            String h = getIntent().getStringExtra("title").toString();
            actionBar.setTitle(h);
        }
        else
        actionBar.setTitle(heading);


        browser.setWebViewClient(new MyBrowser());

        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        browser.loadUrl(url);




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

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }


}
