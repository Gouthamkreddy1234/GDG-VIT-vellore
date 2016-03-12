package com.gdgvitvellore.developers.gdgvitvellore.Feeds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gdgvitvellore.developers.gdgvitvellore.Navigation.NavigationDrawerFragment;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ActivitiesFeedPage;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ParsedActivity;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Volley.GalleryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//gotta rebuild after syncing sometimes
public class SampleActivity extends ActionBarActivity implements ContactAdapter.ClickListener {

    private ContactAdapter adapter;
    private String url = "https://www.googleapis.com/plus/v1/people/106628518438662355516/activities/public?key=AIzaSyBcg0ZbsGXbJ4z_u0QlbqxxSGyOUOxj72o";
    String tag_json_obj = "json_obj_req";
    String TAG = "res";
    ProgressDialog pDialog;
    JSONObject jsonObject;
    ImageView imageView;
    String dates[];
    String actornames[];
    String fullcontent[];
    Bitmap bit[];
    String urlarray[];
    String attachmentimageurl[];
    RecyclerView recList;
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    ActivitiesFeedPage activitiesFeedPage;
    ParsedActivity parsedActivity;
    String img[],fullimg[],embed[];
 //   ArrayList<String[]> arrayList = new ArrayList<>();
    String test[];
    HashMap<String,String> testmap;
    ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        recList = (RecyclerView) findViewById(R.id.cardList);
        progressBar =(ProgressBar)findViewById(R.id.progressBar2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feeds");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.feedcolor)));
        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        //navigationDrawerFragment.setUp(drawerLayout, toolbar);
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.attachToRecyclerView(recList);//done attachment
        swipeRefreshLayout =(SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //---------------------swipe to refresh----------------------------------

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {


            @Override public void onRefresh() {

                refreshItems();
            }


        });



        //-----------------------------------------------------


        //-----------------------------------------------------

        getdata();//this is a very important function-->call this on every refresh in the activity form the recycler view
        //control has to reach here eventually

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        adapter = new ContactAdapter(this, null,activitiesFeedPage,parsedActivity);

        //***************animator is done//********************

        //  attach the adapter
    }

    void refreshItems() {
        // Load items
        // ...
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeRefreshLayout.setRefreshing(false);//this should be false for roatation
            }
        }, 5000);

        swipeRefreshLayout.setEnabled(true);//enabled here
        getdata();//updates the adapter in the end this is the last thing that is does

        // Load complete

        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...just finish the work

            adapter.notifyDataSetChanged();//notify data set changed changed done X1000
        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(true);//this should be true for rotation to stop
    }

    //----------------------------------------------
    public void getdata()//pass his to the adapter's constructor
    {

        //-----------volley request made which runs on a separate background thread--
        JsonObjectRequest jsonObjReq1 = new JsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String b=response.toString().substring(100);
                        Log.d("G+ response", b.toString());
                        //------------------------------------------------
                      /*  int maxLogStringSize = 1000;
                        for(int i = 0; i <= response.toString().length() / maxLogStringSize; i++) {
                            int start = i * maxLogStringSize;
                            int end = (i+1) * maxLogStringSize;
                            end = end > response.toString().length() ? response.toString().length() : end;
                            Log.v(TAG, response.toString().substring(start, end))
                            //response is perfect-->checked on browser and checked on logs!!!excelle
                        }*/

                        //-------------------------------------------------

                     //   Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"Refreshed!!",Toast.LENGTH_LONG).show();

                        jsonObject = response;
                        displaydata();
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.d(TAG, "Error: " + error.getMessage());
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });


        AppController.getInstance().addToRequestQueue(jsonObjReq1, "feeds");


    }

    //-------------------------------------------------------
    private void displaydata() {

         activitiesFeedPage = new ActivitiesFeedPage(jsonObject);
        activitiesFeedPage.initializeItems();

        JSONArray array = activitiesFeedPage.getItemsJsonArray();
        //---------------------------------------------
        //---------------------------------------------
        dates = new String[array.length()];
        actornames= new String[array.length()];
       fullcontent = new String[array.length()];
        urlarray = new String[array.length()];
        attachmentimageurl = new String[array.length()];


        for (int i = 0; i < array.length(); i++) {

             parsedActivity = null;

            try {
                parsedActivity = new ParsedActivity((JSONObject) array.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String date = parsedActivity.getUpdatedDate();
            parsedActivity.initializeActor();//for each card in the recycler view
            parsedActivity.initializeObject();
            parsedActivity.initializeAttachments();


            String attachmentimgurl="";

            for(int j=0;j<parsedActivity.getAttachmentsCount();j++)
            {
                JSONObject attatchmentobject = parsedActivity.getAttachment(j);
               if(parsedActivity.hasAttachmentImage(attatchmentobject)) {


                   attachmentimgurl = parsedActivity.getAttachmentImageUrl(attatchmentobject);
                   Log.d("attachment_object", attachmentimgurl);
               }
                else
               {
                   Log.d("attachment_object", attachmentimgurl);
                   attachmentimgurl="goutham";
               }

           }



            String actor_name = parsedActivity.getActorDisplayName();
            String content = parsedActivity.getActivityObjectContent();
            String dpurl=parsedActivity.getActorImageUrl();


            fullcontent[i]=content;
            actornames[i]=actor_name;
            dates[i] = date;
            attachmentimageurl[i]=attachmentimgurl;
            urlarray[i]=dpurl;

        }

        //------------recieve the data on the top------------------------
        List<ContactInfo> data = new ArrayList<>();

        for (int j = 0; j < dates.length; j++) {
            ContactInfo current = new ContactInfo();

            current.updated = dates[j];
            current.actorname=actornames[j];//added the actor name to an instance of the currentinfo object;
            current.content=fullcontent[j];
           // current.imageurl=attachmentimageurl[j];
            current.url=urlarray[j];
            //done//ci.imageurl-->done,log all the urls and add the the urls to this atta.. array

            data.add(current);//object added to an arraylist of contactinfo objects
        }

        adapter = new ContactAdapter(this, data,activitiesFeedPage,parsedActivity);
        adapter.setClickListener(this);
        recList.setAdapter(adapter);
        recList.setHasFixedSize(false);


        adapter.notifyDataSetChanged();//notify data set changed changed done X1000
        // Stop refresh animation

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


    @Override
    public void onItemClick(View v, int position) {
        Intent i=new Intent(this, GalleryActivity.class);
        Bundle b=new Bundle();
       //    String[] s={ attachmentimageurl[position]};
       // b.putStringArray("imagesarray",s);
        i.putExtra("imagesbundle",b);
        startActivity(i);
    }
}
//who are yout oa ask me ??this is what you need.