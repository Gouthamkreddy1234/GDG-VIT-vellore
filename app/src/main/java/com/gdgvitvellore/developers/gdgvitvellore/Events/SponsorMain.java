package com.gdgvitvellore.developers.gdgvitvellore.Events;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gdgvitvellore.developers.gdgvitvellore.Events.EventsParser;

import com.gdgvitvellore.developers.gdgvitvellore.Events.Extendedevent;
import com.gdgvitvellore.developers.gdgvitvellore.Feeds.HomeActivity;
import com.gdgvitvellore.developers.gdgvitvellore.Volley.GalleryActivity;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ActivitiesFeedPage;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Feeds.ContactAdapter;
import com.gdgvitvellore.developers.gdgvitvellore.Feeds.ContactInfo;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ActivitiesFeedPage;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ParsedActivity;
import com.google.android.gms.plus.PlusShare;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by Gautam on 3/13/2015.
 */
public class SponsorMain extends Fragment implements SponsorAdapter.ClickListener{


    private String url ="http://www.princebansal.comeze.com/events1.json";//perfect
    private String TAG ="Eventparsing";
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private SponsorAdapter adapter;//static-->all objects
    private String[] name;
    private Context context;
    private String[] date;
    private String[] description;
    private String[] imageurl;
    private SwipeRefreshLayout swipeRefreshLayout;


    RecyclerView recList;
    ProgressBar progressBar;


    public SponsorMain(){

    }
    @SuppressLint("ValidFragment")
    public SponsorMain(Context c)
    {
        //no text
        context=c;
    }
    @SuppressLint("ValidFragment")
    public SponsorMain(JSONArray jsonarray)
    {
        this.jsonArray=jsonarray;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.sponsors_main, container, false);
        setup(rootView);
        return rootView;
    }

    public void setup(ViewGroup rv) {//consider main
        recList = (RecyclerView) rv.findViewById(R.id.cardList);
        progressBar=(ProgressBar)rv.findViewById(R.id.progressBar);
        swipeRefreshLayout =(SwipeRefreshLayout)rv.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {


            @Override
            public void onRefresh() {

                refreshItems();
            }


        });
        getData();

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


    }
    private void refreshItems() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeRefreshLayout.setRefreshing(false);//this should be false for roatation
            }
        }, 5000);


        onItemsLoadComplete();
        getData();
    }

    private void onItemsLoadComplete() {

        swipeRefreshLayout.setRefreshing(true);
    }
    public void getData()
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    JSONArray events = response.getJSONArray("event");
                    //jsonArray=events;


                    if(response!=null) {
                        progressBar.setVisibility(View.INVISIBLE);
                        jsonArray= events;
                        jsonObject =response;
                         /*new SponsorMain(jsonArray);//anonymous object*/
                        /*displayData(events);
                        getDescription(events);*/
                        getjsonData();
                    }


                } catch (JSONException e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"Please Check your internet connection",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, "events");



    }



    private void getjsonData() {

        JSONArray jsonArray1 =null;
        JSONObject jsonObject1=null;

       ActivitiesFeedPage activitiesFeedPage = new ActivitiesFeedPage(jsonObject);
        activitiesFeedPage.initializeItems("event");
        jsonArray1=activitiesFeedPage.getItemsJsonArray();
        name=new String[jsonArray1.length()];
        date=new String[jsonArray1.length()];
        description=new String[jsonArray1.length()];
        imageurl=new String[jsonArray1.length()];


        for(int i =0;i<jsonArray1.length();i++)
        {
           jsonObject1= activitiesFeedPage.getItemsJsonObject(i);
            EventsParser eventsParser=new EventsParser(jsonObject1);//for each object->shift control
            date[i]=eventsParser.getDate();

            description[i]=eventsParser.getDescription();
           name[i]=eventsParser.getName();

            imageurl[i]=eventsParser.getImageurl();
        }

        List<SponsorInfo> data = new ArrayList<>();

        for (int j = 0; j < date.length; j++) {//two as of now
            SponsorInfo current = new SponsorInfo();
            current.name = name[j];
            current.date = date[j];
            current.description = description[j];
            current.imageurl = imageurl[j];

            Log.d("date12345",date[j]);
            Log.d("description12345",description[j]);
            Log.d("name12345",name[j]);
            Log.d("imageurl12345",imageurl[j]);


            data.add(current);
        }


        adapter = new SponsorAdapter(context,data);
        adapter.setClickListener(this);
        recList.setAdapter(adapter);
        recList.setHasFixedSize(false);

        adapter.notifyDataSetChanged();//notify data set changed changed done X1000
        // Stop refresh animation
       //called from inside of the onresponse() method



    }







    @Override
    public void onItemClick(View v, int position) {

        //ImageView eventimage;

       // Toast.makeText(this,"hi",Toast.LENGTH_LONG).show();//perfect!!
        Intent intent = new Intent();
        /*eventimage=(ImageView)v.findViewById(R.id.Image_event);
        eventimage.getDrawable();*/
        if(v.getId()==R.id.clickablearea||v.getId()==R.id.Image_event) {
            String title = name[position];
            String date1 = date[position];
            String imageurl1 = imageurl[position];
            intent.setClass(getActivity(), Extendedevent.class);
            intent.putExtra("image", "");
            intent.putExtra("name", title);
            intent.putExtra("date", date1);
            intent.putExtra("imageurl", imageurl1);
            startActivity(intent);
        }
        if(v.getId()==R.id.register){
            Intent i=new Intent(getActivity(),Eventsweb.class);
            i.putExtra("title","Register");
            startActivity(i);
        }
        if(v.getId()==R.id.share_but){

                    Uri image=Uri.parse(imageurl[position]);
                    Intent shareIntent = new PlusShare.Builder(getActivity())
                            .setType("*/*")
                            .addStream(image)
                            .setText(name[position]+"\nDate:"+date[position])
                            .setContentUrl(Uri.parse(imageurl[position]))
                            .getIntent();

                    startActivityForResult(shareIntent,5);

        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        AppController.getInstance().cancelPendingRequests("events");
    }
}

//object of sponsorMain actually inherits th objects of both the clicklistener and the class that it(sponsormain) extends...therfore...
//adapter is a small link to the main activity
