package com.gdgvitvellore.developers.gdgvitvellore.AboutUs;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.tabs.CircularImageView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

import sk.kandrac.circularview.CircularView;

/**
 * Created by Gautam on 3/22/2015.
 */
public class AboutUs extends ActionBarActivity
{
//start learning and using fragments in the place of activities{
     //private BarChart chart;//only im gonna use it,i mean ony this graph
    //private PieChart pieChart,p2,p3,p4;
    private Toolbar toolbar;
    private CircularView v1,v2,v3,v4;
    private FrameLayout frameLayout;
    private TextView percent,p2,p3,p4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutusnew);//the layout and the view inflation is done here by android rather than you doing it automatically

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal)));
        actionBar.setTitle("About Us");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        percent = (TextView) findViewById(R.id.inc);
        p2 = (TextView) findViewById(R.id.inc2);
        p3 = (TextView) findViewById(R.id.inc3);
        p4 = (TextView) findViewById(R.id.inc4);

        frameLayout = (FrameLayout) findViewById(R.id.fl);
        int width = frameLayout.getWidth();
        int height = frameLayout.getHeight();

        float w = width / 2;
        float h = height / 2;


        v1 = (CircularView) findViewById(R.id.circle);
        v1.addItem("p", 80, getResources().getColor(R.color.c1));
        v1.addItem("n", 20, getResources().getColor(R.color.ddgray));

        v2 = (CircularView) findViewById(R.id.circle2);
        // v2.setRotateSpeed(3f);
        v2.addItem("p", 65, getResources().getColor(R.color.c2));
        v2.addItem("n", 35, getResources().getColor(R.color.ddgray));


        v3 = (CircularView) findViewById(R.id.circle3);
        //v3.setRotateSpeed(3f);
        v3.addItem("p", 70, getResources().getColor(R.color.c3));
        v3.addItem("n", 30, getResources().getColor(R.color.ddgray));


        v4 = (CircularView) findViewById(R.id.circle4);
        //  v4.setRotateSpeed(3f);
        v4.addItem("p", 95, getResources().getColor(R.color.red));
        v4.addItem("n", 05, getResources().getColor(R.color.ddgray));

        RotateAnimation anim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //anim.setInterpolator(new BounceInterpolator());
        //anim.setRepeatCount(1);
        anim.setDuration(2000);//2000 millis
        //anim.setStartOffset(600);

        RotateAnimation anim2 = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //anim2.setInterpolator(new BounceInterpolator());
        //anim2.setRepeatCount(1);
        anim2.setDuration(2000);
        //anim2.setStartOffset(900);

        RotateAnimation anim3 = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //anim3.setInterpolator(new BounceInterpolator());
        //anim3.setRepeatCount(1);
        anim3.setDuration(2000);
        //anim3.setStartOffset(1200);


        RotateAnimation anim4 = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //anim4.setInterpolator(new BounceInterpolator());
        //anim4.setRepeatCount(1);
        anim4.setDuration(2000);
        //anim4.setStartOffset(1500);


        v1.startAnimation(anim);
        v2.startAnimation(anim2);
        v3.startAnimation(anim3);
        v4.startAnimation(anim4);


        final int[] i = {0};
        CountDownTimer timer = new CountDownTimer(4000,30) {//2 sec c-timer with ontick() called every 20 millis

            @Override
            public void onTick(long millisUntilFinished) {
                // this method will be executed every second ( 1000 ms : the second parameter in the CountDownTimer constructor)
               int temp= i[0]++;
                p4.setText(Integer.toString(temp)+"%");
                //if((100-(millisUntilFinished/100))<=95)
               // p4.setText(String.valueOf(temp));
                if(temp<=65)
                {
                    p2.setText(Integer.toString(temp)+"%");
                }
                if(temp<=70)
                {
                    p3.setText(Integer.toString(temp)+"%");
                }
                if(temp<=80)
                {
                    percent.setText(Integer.toString(temp)+"%");
                }

            }

            @Override
            public void onFinish() {


                p4.setText(Integer.toString(95)+"%");
                 // Auto-generated method stub

            }
        };
        timer.start();



    }
}



//remove the text on the pie graph in the end after adding the %textview
