package com.gdgvitvellore.developers.gdgvitvellore.Events;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Feeds.ContactAdapter;
import com.gdgvitvellore.developers.gdgvitvellore.Feeds.ContactInfo;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ActivitiesFeedPage;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ParsedActivity;

import java.util.Collections;
import java.util.List;

/**
 * Created by Gautam on 3/13/2015.
 */
//contains an I and C->imp
public class SponsorAdapter extends  RecyclerView.Adapter<SponsorAdapter.SponsorViewHolder>
{
    private ClickListener clickListener;
    private List<SponsorInfo> info= Collections.emptyList();
    private LayoutInflater inflater;
    private ImageLoader imageloader;
    private Context context;
    private int lastPosition=-1;


    public SponsorAdapter(Context c, List<SponsorInfo> info)
    {
        context=c;
        inflater = LayoutInflater.from(context);
        this.info=info;
        imageloader= AppController.getInstance().getImageLoader();
    }



    @Override
    public int getItemCount() {

        return info.size();
    }

    @Override
    public void onBindViewHolder(SponsorViewHolder sponsorViewHolder, int i) {

        SponsorInfo ci = info.get(i);

        sponsorViewHolder.eventname.setText(ci.name);
        sponsorViewHolder.date.setText(ci.date);
        try {
            imageloader.get(ci.imageurl, ImageLoader.getImageListener(sponsorViewHolder.eventimage, R.drawable.ic_loader, R.drawable.round_2));
            //sponsorViewHolder.description.setText(ci.description);
        }
        catch(Exception e){
            e.printStackTrace();
        }
            setAnimation(sponsorViewHolder.wc,i);

    }


    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            viewToAnimate.setPivotX(0f);
            viewToAnimate.setPivotY(0f);
            AnimatorSet animatorSet=new AnimatorSet();
            //ObjectAnimator animationY=ObjectAnimator.ofFloat(viewToAnimate,"translationY",300,0);
            //animationY.setInterpolator(new DecelerateInterpolator());
            //ObjectAnimator animationX=ObjectAnimator.ofFloat(viewToAnimate,"translationX",-600,0);
            ObjectAnimator translateX=ObjectAnimator.ofFloat(viewToAnimate,"translationX",-200,0);
            ObjectAnimator translateY=ObjectAnimator.ofFloat(viewToAnimate,"translationY",600,0);
            ObjectAnimator rotate=ObjectAnimator.ofFloat(viewToAnimate,"rotation",50,0);
            translateX.setInterpolator(new AccelerateInterpolator());
            translateY.setInterpolator(new DecelerateInterpolator());
            rotate.setInterpolator(new DecelerateInterpolator());
            translateX.setDuration(300);
            translateY.setDuration(400);

            rotate.setDuration(400);
            animatorSet.playTogether(translateX,translateY,rotate);
            animatorSet.start();

        }
        lastPosition = position;
    }
    //creation happens for a row
    @Override
    public SponsorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.sponsor_card, viewGroup, false);
        SponsorViewHolder myholder= new SponsorViewHolder(view);
        return myholder;
    }
    //Using inner C from a function of the base class



    public void setClickListener(ClickListener c){
        clickListener=c;
    }



    public  class SponsorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView eventname;
        protected TextView date;
        protected TextView description;
        protected LinearLayout register;
        protected TextView regtext;
        protected ImageView eventimage;
        private LinearLayout clickable,wc,share;


        public SponsorViewHolder(View v) {
            super(v);
            eventname= (TextView)v.findViewById(R.id.textView4);
            date= (TextView)v.findViewById(R.id.textView5);
            description =(TextView)v.findViewById(R.id.share);
            regtext= (TextView)v.findViewById(R.id.regtext);
            register=(LinearLayout)v.findViewById(R.id.register);
            clickable=(LinearLayout)v.findViewById(R.id.clickablearea);
            wc=(LinearLayout)v.findViewById(R.id.wholecard);
            share=(LinearLayout)v.findViewById(R.id.share_but);
            eventimage=(ImageView)v.findViewById(R.id.Image_event);
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto_Medium.ttf");
            eventname.setTypeface(tf);
            date.setTypeface(tf);
            description.setTypeface(tf);
            regtext.setTypeface(tf);
            eventimage.setOnClickListener(this);
            share.setOnClickListener(this);
            clickable.setOnClickListener(this);
            register.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(clickListener!=null)
            {
                clickListener.onItemClick(v,getPosition());

            }
        }
    }



    public interface ClickListener{
        public void onItemClick(View v, int position);
    }



}
//global variables
//control passed to bindview
//either ill do it or ask another function to do it
