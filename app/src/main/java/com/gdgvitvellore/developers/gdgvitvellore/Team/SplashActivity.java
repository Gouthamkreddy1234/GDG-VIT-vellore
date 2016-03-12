package com.gdgvitvellore.developers.gdgvitvellore.Team;

import android.app.Activity;
import android.os.Bundle;

import com.gdgvitvellore.developers.gdgvitvellore.R;

/**
 * Created by shalini on 19-03-2015.
 */
public class SplashActivity extends Activity {
    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        t.start();
    }
}
