package com.gdgvitvellore.developers.gdgvitvellore.Feeds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gdgvitvellore.developers.gdgvitvellore.Navigation.NavigationDrawerFragment;
import com.gdgvitvellore.developers.gdgvitvellore.Volley.GalleryActivity;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ActivitiesFeedPage;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ParsedActivity;
import com.gdgvitvellore.developers.gdgvitvellore.Volley.SplashActivity;
import com.google.android.gms.plus.PlusShare;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

//gotta rebuild after syncing sometimes
public class FeedsActivity extends Fragment implements ContactAdapter.ClickListener{

    private ContactAdapter adapter;
    private String url = "https://www.googleapis.com/plus/v1/people/+gdgvitvellore/activities/public?key=AIzaSyBcg0ZbsGXbJ4z_u0QlbqxxSGyOUOxj72o";
    String tag_json_obj = "json_obj_req";
    String TAG = "res";
    ProgressDialog pDialog;
    JSONObject jsonObject;
    ImageView imageView;
    String dates[];
    HomeActivity home;
    String actornames[];
    String fullcontent[];
    Bitmap bit[];
    String urlarray[],feedUrl[];
    String attachmentimageurl[][];
    RecyclerView recList;

    ActivitiesFeedPage activitiesFeedPage;
    ParsedActivity parsedActivity;
    String img[],fullimg[],embed[];
    //   ArrayList<String[]> arrayList = new ArrayList<>();
    String test[];
    HashMap<String,String> testmap;
    ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public FeedsActivity(){

    }
    public FeedsActivity(HomeActivity hm){
            home=hm;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
            R.layout.feeds_layout, container, false);
    setup(rootView);
    return rootView;
}

   public void setup(ViewGroup rv){
        recList = (RecyclerView) rv.findViewById(R.id.cardList);
       /* recList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("posotion",dx+",,,,"+dy);
                if(dy>0){
                    home.toggleActionBar(1);
                }
                else if(dy<0)
                {
                    home.toggleActionBar(0);
                }
                else
                {
                    home.toggleActionBar(1);
                }
            }
        });*/
        progressBar =(ProgressBar)rv.findViewById(R.id.progressBar2);
        swipeRefreshLayout =(SwipeRefreshLayout)rv.findViewById(R.id.swipe_refresh_layout);
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

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

      // recList.setItemAnimator(animator);
        recList.setLayoutManager(llm);
       /*recList.setOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
               super.onScrollStateChanged(recyclerView, newState);
               Log.d("state", String.valueOf(newState));
           }

           @Override
           public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
               Log.d("scroll", dx+":"+dy);
               if(home.toolbar.isShown()) {
                   if (dy >= 5) {
                       home.toggleActionBar(1);
                   }
               }
               else{
                    if(dy<=-3)
                    home.toggleActionBar(0);
               }
           }
       });*/
//        adapter = new ContactAdapter(getActivity(), null,activitiesFeedPage,parsedActivity);

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

       // adapter.notifyDataSetChanged();//notify data set changed changed done X1000
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
                        Log.d("G+ response", response.toString());
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
                        Toast.makeText(getActivity(),"Refreshed!!",Toast.LENGTH_LONG).show();

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
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_LONG).show();
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
int itemnumber=0;
        JSONArray items=null;
        try {
            //Log.d("test2", String.valueOf(jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("object").getJSONArray("attachments").length()));
        itemnumber=jsonObject.getJSONArray("items").length();
            items=jsonObject.getJSONArray("items");

                  } catch (JSONException e) {
            e.printStackTrace();
            Log.d("test2", "error");
        }
       // activitiesFeedPage = new ActivitiesFeedPage(jsonObject);
        //activitiesFeedPage.initializeItems();

       // JSONArray array = activitiesFeedPage.getItemsJsonArray();
      //  Log.d("array",array.toString());
        //---------------------------------------------
        //---------------------------------------------
        dates = new String[itemnumber];
        actornames= new String[itemnumber];
        fullcontent = new String[itemnumber];
        urlarray = new String[itemnumber];
        attachmentimageurl = new String[itemnumber][];
        feedUrl = new String[itemnumber];


        for (int i = 0; i < itemnumber; i++) {

            parsedActivity = null;

            JSONObject itemObject=null;
           /* try {
                parsedActivity = new ParsedActivity((JSONObject) items.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String date = parsedActivity.getUpdatedDate();
            parsedActivity.initializeActor();//for each card in the recycler view
            parsedActivity.initializeObject();
            parsedActivity.initializeAttachments();*/
            try {
                itemObject=items.getJSONObject(i);

            Vector<String> attachmentimgurl=new Vector<String>();
            if(itemObject.getJSONObject("object").has("attachments")){
            for(int j=0;j<itemObject.getJSONObject("object").getJSONArray("attachments").length();j++)
            {
                //JSONObject attatchmentobject = parsedActivity.getAttachment(j);
                JSONObject attatchmentobject= itemObject.getJSONObject("object").getJSONArray("attachments").getJSONObject(j);
                /*if(parsedActivity.hasAttachmentImage(attatchmentobject)) {


                    attachmentimgurl = parsedActivity.getAttachmentImageUrl(attatchmentobject);
                    Log.d("attachment_object", attachmentimgurl);
                }
                else
                {
                    Log.d("attachment_object", attachmentimgurl);
                    attachmentimgurl="goutham";
                }*/
                if(attatchmentobject.has("image")) {

                    attachmentimgurl.add(attatchmentobject.getJSONObject("image").getString("url"));
                    Log.d("attachment_object", attachmentimgurl.firstElement());
                }

                else if(attatchmentobject.has("thumbnails")) {


                    for(int k=0;k<attatchmentobject.getJSONArray("thumbnails").length();k++)
                    attachmentimgurl.add(attatchmentobject.getJSONArray("thumbnails").getJSONObject(k).getJSONObject("image").getString("url"));
                    Log.d("attachment_object", "thumbnail"+attachmentimgurl);
                }
                else
                {
                    Log.d("attachment_object", "thumb");
                    attachmentimgurl=null;
                }


            }}
                else{
                attachmentimgurl=null;
            }


            String date = itemObject.getString("updated");
            String actor_name = itemObject.getJSONObject("actor").getString("displayName");
            String content = itemObject.getJSONObject("object").getString("content");
            String dpurl=itemObject.getJSONObject("actor").getJSONObject("image").getString("url");
            String furl=itemObject.getString("url");


            fullcontent[i]=content;
            actornames[i]=actor_name;
            dates[i] = date;
                if(attachmentimgurl!=null) {
                    attachmentimageurl[i] = new String[attachmentimgurl.size()];
                    attachmentimageurl[i] = attachmentimgurl.toArray(new String[attachmentimgurl.size()]);
                }
                else{
                    attachmentimageurl[i]=null;
                }
            urlarray[i]=dpurl;
            feedUrl[i]=furl;
            } catch (JSONException e) {
            e.printStackTrace();
        }

        }

        //------------recieve the data on the top------------------------
        List<ContactInfo> data = new ArrayList<ContactInfo>();

        for (int j = 0; j < dates.length; j++) {
            ContactInfo current = new ContactInfo();

            current.updated = dates[j];
            current.actorname=actornames[j];//added the actor name to an instance of the currentinfo object;
            current.content=fullcontent[j];
            current.imageurl=attachmentimageurl[j];
            current.url=urlarray[j];
            //done//ci.imageurl-->done,log all the urls and add the the urls to this atta.. array
            current.feedUrl=feedUrl[j];
            data.add(current);//object added to an arraylist of contactinfo objects
        }

        adapter = new ContactAdapter(home, data,activitiesFeedPage,parsedActivity);
        adapter.setClickListener(this);
        recList.setAdapter(adapter);
        recList.setHasFixedSize(false);


        adapter.notifyDataSetChanged();//notify data set changed changed done X1000
        // Stop refresh animation

    }












    @Override
    public void onItemClick(View v, int position) {
        if(v.getId()==R.id.attachedimage){
        Intent i=new Intent(getActivity(), GalleryActivity.class);
        Bundle b=new Bundle();
        String[] s=attachmentimageurl[position];
        b.putStringArray("imagesarray",s);
        i.putExtra("imagesbundle",b);
            if(attachmentimageurl[position]!=null)
        startActivity(i);
        }
        if(v.getId()==R.id.share_but){
            if(attachmentimageurl[position]!=null) {
                Uri image=Uri.parse(attachmentimageurl[position][0]);
                Intent shareIntent = new PlusShare.Builder(getActivity())
                        .setType("*/*")
                        .addStream(image)
                        .setText(fullcontent[position])
                        .setContentUrl(Uri.parse(feedUrl[position]))
                        .getIntent();

                startActivityForResult(shareIntent,5);
            }
            else
            {
                Intent shareIntent = new PlusShare.Builder(getActivity())
                        .setType("text/plain")
                        .setText(fullcontent[position])
                        .setContentUrl(Uri.parse(feedUrl[position]))
                        .getIntent();

                startActivityForResult(shareIntent,5);
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        AppController.getInstance().cancelPendingRequests("feeds");
    }
}
//who are yout oa ask me ??this is what you need.