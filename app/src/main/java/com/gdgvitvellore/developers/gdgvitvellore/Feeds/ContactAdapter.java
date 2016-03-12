package com.gdgvitvellore.developers.gdgvitvellore.Feeds;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ActivitiesFeedPage;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ParsedActivity;
import com.google.android.gms.plus.PlusOneButton;
import com.google.android.gms.plus.PlusShare;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Gautam on 2/18/2015.
 */
public class ContactAdapter extends
        RecyclerView.Adapter<ContactAdapter.ContactViewHolder>  {
    private ClickListener clickListener;
    private List<ContactInfo> info;
    private LayoutInflater inflater;
    ActivitiesFeedPage a;
    private String URL="www.google.com";
    ParsedActivity p;
    private Context context;
    ImageLoader imageLoader ;
    private int lastPosition=-1;
    private int PLUS_ONE_REQUEST_CODE=0;
    private Pattern TAG_REGEX =  Pattern.compile("#([A-Za-z0-9_-]+)");//constant-->meaning
    Handler m_handler;
    Thread hashAnim;
    Runnable m_handlerTask ;
    Boolean flag;
    Boolean flags[];


    public void delete(int position)
    {
        info.remove(position);
        notifyItemRemoved(position);//notifyitemInerted() for inertion
        //notifyDataSetChanged();//shoutlcnt be there for animation to be seen//can be done from outside ie usinf the adapter
        //instance itself..
        //the adapter objects data needs to be notified of a change
    }

    public ContactAdapter(Context c, List<ContactInfo> inf, ActivitiesFeedPage activitiesFeedPage, ParsedActivity parsedActivity)
    {
        context=c;
        inflater = LayoutInflater.from(context);
        info=inf;
        a=activitiesFeedPage;
        p=parsedActivity;
        imageLoader= AppController.getInstance().getImageLoader();
        flag = true;
        flags=new Boolean[info.size()];
        for(int i=0;i<info.size();i++)
        {
            flags[i]=true;
        }
    }

    @Override
    public int getItemCount() {

        return info.size();
    }

    @Override
    public void onBindViewHolder( final ContactViewHolder contactViewHolder, final int i) {
            flag=false;
        ContactInfo ci = info.get(i);//info arraylist into object

       contactViewHolder.actor_name.setText(ci.actorname);
        ci.updated=ci.updated.substring(0,10);
        contactViewHolder.updatedtime.setText(ci.updated);

       String con="<body>"+ci.content+"</body>";


       /* String s= con.replace("<br />","\n");

        String sp= s.replace("&quot;","\"");*/
        Document doc = Jsoup.parse(con);
    String con2  =    doc.getElementsByTag("body").text().toString();
        //Log.d("con21",con2);
        final List<String> lis = new ArrayList<>();
        Elements tags=doc.getElementsByClass("ot-hashtag");
        Log.d(String.valueOf(i),tags.toString());
        for(int q=0;q<tags.size();q++)
        {
             lis.add(tags.get(q).html());
        }
        if(hashAnim!=null&&hashAnim.isAlive()){
            hashAnim.interrupt();
        }
        hashAnim=new Thread(new Runnable() {

            @Override
            public void run() {
                int a=lis.size();
                int i=0;
                flag=true;
                try{
                    while(flag) {
                        final int finalI = i;
                        ((Activity) context).runOnUiThread(new Runnable() //run on ui thread
                            {
                                public void run() {
                                    //Log.i("timeleft", "" + timeleft[0]);
                                    //update ui
                                    contactViewHolder.hashtag.setText(lis.get(finalI));

                                }
                            });
                            i++;
                            if(i>=a){
                                i=0;
                            }

                            Thread.sleep(3000);
                        }

                    }catch (Exception e){}
                }
            });
        if(!lis.isEmpty()){
            hashAnim.start();
        }
        else{
            contactViewHolder.hashtag.setText("#gdgvit");
        }

        //con2 = con2.replaceAll("\\s+", " ");
        //Log.d("con22",con2);

       /* Elements rows = doc.getElementsByTag("a");
        for (Element element : rows) {
            contactViewHolder.content.setText(element.text().toString());
        }*/
        //-----------------------------------------------------

         //String str = "<tag>apple</tag><b>hello</b><tag>orange</tag><tag>pear</tag>";

     //then what is thedifference between = null and the new operator//new-->allocates memory-->null puts nothing in that memory
        int k=0;
        String temp2;

       /* if(con.contains("#")) {

            lis = getTagValues(con2);
            Log.d("Hashtag!!", lis.toString()); // Prints [apple, orange, pear]
            Log.d("hashed card", con2);
            if (lis.isEmpty() == false) {//do it here itself



                m_handler = new Handler();
                m_handlerTask = new Runnable()
                {
                    int i1=0;
                    @Override
                    public void run() {

                        // do something. update text view.

                        contactViewHolder.hashtag.setText(Integer.toString(i1));

                        m_handler.postDelayed(m_handlerTask, 1000);
                        i1++;


                    }
                };
                m_handlerTask.run();*/



               /* if(flags[i] == true) {
                final Timer _t = new Timer();

                    final int[] timeleft = {100};
                    flag = false;
                    _t.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {

                            ((Activity) context).runOnUiThread(new Runnable() //run on ui thread
                            {
                                public void run() {
                                    Log.i("timeleft", "" + timeleft[0]);
                                    //update ui
                                    contactViewHolder.hashtag.setText(Integer.toString(timeleft[0]));

                                }
                            });
                            if (timeleft[0] >= 0) {
                                timeleft[0]--;
                            } else {
                                _t.cancel();
                                flags[i] = true;
                            }
                        }
                    }, 1000, 1000);
                }

            }
        }
        */
        if(!lis.isEmpty())
        while(k < lis.size())
        {
          String temp =   lis.get(k).toString();
            temp2 = con2.replace(temp,"\b");
             con2=temp2;

            k++;
        }




        //----------------------------------------------------------




           contactViewHolder.mPlusOneButton.initialize(ci.feedUrl,i);

        contactViewHolder.content.setText(con2+"\n");//text() has to do teh trick


        //p.setImageBitmap(ci.url,contactViewHolder.imageView);
        //contactViewHolder.imageView.setImageBitmap(ci.bitmap);
        //imageLoader.get(ci.url, ImageLoader.getImageListener(contactViewHolder.dp, R.drawable.round_3, R.drawable.round_2));

        Log.d("bing", String.valueOf(i));
try{
        Log.d("imageurl",ci.imageurl[0]);
    }
catch(Exception e){

    Log.d("imageurl","null");
}
        if(ci.imageurl!=null) {
            contactViewHolder.attachedimage.setVisibility(ImageView.VISIBLE);

            if(ci.imageurl.length==1){
                contactViewHolder.imageCountBg.setVisibility(LinearLayout.GONE);
            }
            else{
                contactViewHolder.imageCountBg.setVisibility(LinearLayout.VISIBLE);
                contactViewHolder.imageCount.setText(ci.imageurl.length+" photos");
            }
            Log.d("imageurl",ci.imageurl[0]);
            imageLoader.get(ci.imageurl[0], ImageLoader.getImageListener(contactViewHolder.attachedimage, R.drawable.loader, R.drawable.round_2));
        }
        else{
            contactViewHolder.imageCountBg.setVisibility(LinearLayout.GONE);
//            imageLoader.get(ci.imageurl[0], ImageLoader.getImageListener(contactViewHolder.attachedimage, R.drawable.ic_loader, R.drawable.round_2));
            contactViewHolder.attachedimage.setVisibility(ImageView.GONE);
        }
        setAnimation(contactViewHolder.wc,i);//finish this in the bindviewholder and not the createview holder
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
           // Animation animation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
            AnimatorSet animatorSet=new AnimatorSet();
            ObjectAnimator animationY=ObjectAnimator.ofFloat(viewToAnimate,"translationY",300,0);
            animationY.setInterpolator(new DecelerateInterpolator());
            ObjectAnimator animationX=ObjectAnimator.ofFloat(viewToAnimate,"translationX",-600,0);
            ObjectAnimator scaleX=ObjectAnimator.ofFloat(viewToAnimate,"scaleX",0F,1F);
            ObjectAnimator scaleY=ObjectAnimator.ofFloat(viewToAnimate,"scaleY",0F,1F);
            animationY.setDuration(600);
            animationX.setDuration(400);
            scaleX.setDuration(500);
            scaleY.setDuration(500);
            animatorSet.playTogether(animationX,animationY,scaleX,scaleY);
            animatorSet.start();
            //viewToAnimate.startAnimation(animation);

        }
        lastPosition = position;
    }

    public  List<String> getTagValues( String str) {//cant be static-->static is a class thing and static func cannot access non static data and variables
          List<String> tagValues = new ArrayList<>();//dont change str in here
        Log.d("str",str);


         Matcher matcher = TAG_REGEX.matcher(str);
        while (matcher.find()) {//==true
            tagValues.add(matcher.group(1));
        }


        return tagValues;
    }



    //creation happens for a row
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.feeds_card, viewGroup, false);
        ContactViewHolder myholder= new ContactViewHolder(view);
        return myholder;
    }
    public void setClickListener(ClickListener c){
        clickListener=c;
    }





    public  class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView actor_name;
        protected TextView updatedtime;
        protected TextView content;
        protected  ImageView attachedimage;
        protected PlusOneButton mPlusOneButton;
        protected  ImageView dp;
        protected LinearLayout imageCountBg;
        protected TextView imageCount;
        protected CardView wc;
        protected LinearLayout share;
        protected TextView hashtag;

        public ContactViewHolder(View v) {
            super(v);
            actor_name= (TextView)v.findViewById(R.id.title123);

            hashtag= (TextView)v.findViewById(R.id.hashtag);
            wc= (CardView)v.findViewById(R.id.card_view);
            updatedtime= (TextView)v.findViewById(R.id.date123);
            mPlusOneButton = (PlusOneButton) v.findViewById(R.id.plus_one_button);
            content =(TextView)v.findViewById(R.id.content);
            imageCount =(TextView)v.findViewById(R.id.imgcount);
            attachedimage=(ImageView)v.findViewById(R.id.attachedimage);
            dp=(ImageView)v.findViewById(R.id.dp);
            share=(LinearLayout)v.findViewById(R.id.share_but);
            imageCountBg=(LinearLayout)v.findViewById(R.id.imgcountbg);

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(v,getPosition());
                }
            });
            attachedimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(v,getPosition());
                }
            });

        }



    }
    public interface ClickListener{
        public void onItemClick(View v, int position);
    }
}
//global variables
//control passed to bindview
//either ill do it or ask another function to do it