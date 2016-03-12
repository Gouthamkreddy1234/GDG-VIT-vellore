package com.gdgvitvellore.developers.gdgvitvellore.Sponser;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdgvitvellore.developers.gdgvitvellore.R;

import java.util.ArrayList;

/**
 * Created by Gautam on 3/15/2015.
 */
public class Sponser extends Fragment implements View.OnClickListener {

    Bundle bundle= new Bundle();
    CardView lg,lr,lb,lgr,ly;
    ArrayList<CardView> arrayList;
    int i=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.events, container, false);
        setup(rootView);
        return rootView;
    }

    public void setup(ViewGroup rv) {
        lg = (CardView) rv.findViewById(R.id.gold);//important
        lg.setOnClickListener(this);
        lr = (CardView) rv.findViewById(R.id.red);
        lr.setOnClickListener(this);
        lb = (CardView) rv.findViewById(R.id.blue);
        lb.setOnClickListener(this);
        lgr = (CardView) rv.findViewById(R.id.green);
        lgr.setOnClickListener(this);
        ly = (CardView) rv.findViewById(R.id.yellow);
        ly.setOnClickListener(this);


        arrayList=new ArrayList<>();
        arrayList.add(lg);
        arrayList.add(lr);
        arrayList.add(lb);
        arrayList.add(lgr);
        arrayList.add(ly);

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {

                while (i < 5) {
                    ((Activity) getActivity()).runOnUiThread(new Runnable() //run on ui thread
                    {
                        public void run() {
                            //Log.i("timeleft", "" + timeleft[0]);
                            //update ui

                            arrayList.get(i).setVisibility(CardView.VISIBLE);
                            setAnimation(arrayList.get(i));
                            i++;

                        }

                    });
                    try

                    {
                        Thread.sleep(150);
                    } catch (
                            InterruptedException e
                            )

                    {
                        e.printStackTrace();
                    }
                }
                i=0;




            }
        });
        t.start();


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    private void setAnimation(View viewToAnimate)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

            AnimatorSet animatorSet=new AnimatorSet();
            //ObjectAnimator animationY=ObjectAnimator.ofFloat(viewToAnimate,"translationY",300,0);
            //animationY.setInterpolator(new DecelerateInterpolator());
            //ObjectAnimator animationX=ObjectAnimator.ofFloat(viewToAnimate,"translationX",-600,0);
            ObjectAnimator scaleX=ObjectAnimator.ofFloat(viewToAnimate,"scaleX",0F,1F);
            ObjectAnimator scaleY=ObjectAnimator.ofFloat(viewToAnimate,"scaleY",0F,1F);
            scaleX.setInterpolator(new AccelerateInterpolator());
            scaleY.setInterpolator(new AccelerateInterpolator());
            scaleX.setDuration(500);
            scaleY.setDuration(500);
            animatorSet.playTogether(scaleX,scaleY);
            animatorSet.start();

    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(getActivity(),SponsorFull.class);
        switch (v.getId())
        {

            case R.id.gold:
                   intent.putExtra("gold", "");
                startActivity(intent);

                break;

            case R.id.red:

                intent.putExtra("red", "");
                startActivity(intent);

                break;
            case R.id.green:

                intent.putExtra("green", "");
                startActivity(intent);

                break;
            case R.id.yellow:
                intent.putExtra("yellow", "");
                startActivity(intent);

                break;
            case R.id.blue:
                intent.putExtra("blue", "");
                startActivity(intent);

                break;

            default:
                break;

        }

    }
}
