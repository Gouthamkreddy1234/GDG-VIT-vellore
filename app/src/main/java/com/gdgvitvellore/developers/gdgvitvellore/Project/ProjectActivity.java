package com.gdgvitvellore.developers.gdgvitvellore.Project;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gdgvitvellore.developers.gdgvitvellore.Feeds.ContactAdapter;
import com.gdgvitvellore.developers.gdgvitvellore.Feeds.ContactInfo;
import com.gdgvitvellore.developers.gdgvitvellore.Navigation.NavigationDrawerAdapter;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ActivitiesFeedPage;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ParsedActivity;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ProjectsParser;
import com.gdgvitvellore.developers.gdgvitvellore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shalini on 16-03-2015.
 */
public class ProjectActivity extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ProjectAdapter adapter;
    ProgressBar progressBar;
    private String currentPage="android";
    private JSONObject main;
    private LinearLayout but_android,but_php,but_python,but_java,but_network,but_ai,but_web,but_node;
    String TAG = "res";
    List<ProjectInfo> list;
    JSONArray array;
    private String url="http://www.princebansal.comeze.com/projects.json";
    private ImageLoader imageLoader;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActivitiesFeedPage activitiesFeedPage;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.project_layout, container, false);
        setup(rootView);
        return rootView;
    }

    public void setup(ViewGroup rv) {
        progressBar =(ProgressBar)rv.findViewById(R.id.progressBar);
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
        recyclerView = (RecyclerView) rv.findViewById(R.id.project_recycler_view);
        but_ai=(LinearLayout)rv.findViewById(R.id.cat_ai);
        but_android=(LinearLayout)rv.findViewById(R.id.cat_android);
        but_php=(LinearLayout)rv.findViewById(R.id.cat_php);
        but_python=(LinearLayout)rv.findViewById(R.id.cat_python);
        but_java=(LinearLayout)rv.findViewById(R.id.cat_java);
        but_network=(LinearLayout)rv.findViewById(R.id.cat_network);
        but_web=(LinearLayout)rv.findViewById(R.id.cat_web);
        but_node=(LinearLayout)rv.findViewById(R.id.cat_node);
        but_ai.setOnClickListener(this);
        but_android.setOnClickListener(this);
        but_python.setOnClickListener(this);
        but_php.setOnClickListener(this);
        but_java.setOnClickListener(this);
        but_web.setOnClickListener(this);
        but_node.setOnClickListener(this);
        but_network.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        imageLoader = AppController.getInstance().getImageLoader();
        getdata();
    }

    private void refreshItems() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeRefreshLayout.setRefreshing(false);//this should be false for roatation
            }
        }, 5000);

        swipeRefreshLayout.setEnabled(true);
        getdata();
    }

    public void getdata()//pass his to the adapter's constructor
    {
        try {
            //-----------volley request made which runs on a separate background thread--
            JsonObjectRequest jsonObjReq1 = new JsonObjectRequest(
                    url,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("G+ response", response.toString());

                            Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_LONG).show();

                            main = response;
                            displaydata(currentPage);
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq1, "projects");
        }
        catch(Exception e){
            e.printStackTrace();


        }

    }

    //-------------------------------------------------------
    private void displaydata(String cat) {
    try {
        activitiesFeedPage = new ActivitiesFeedPage(main);
        activitiesFeedPage.initializeItems(cat);

        array = activitiesFeedPage.getItemsJsonArray();
        Log.d("arraycount",String.valueOf(array.length()));
        list= new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            ProjectsParser parser = new ProjectsParser((JSONObject) array.get(i));
            ProjectInfo info = new ProjectInfo();
            info.title =parser.getName();
            info.content =parser.getShortDescription();
            info.status = parser.getStatus();
            parser.initializeImages();
            info.conimg_url = parser.getContentImageUrl();
            info.disimg_url = parser.getDisplayImageUrl();
            info.category = parser.getCategory();
            list.add(info);
        }
    }catch(Exception e){
        e.printStackTrace();
    }


        adapter = new ProjectAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
        adapter.notifyDataSetChanged();//notify data set changed changed done X1000
        // Stop refresh animation

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.cat_ai:
                displaydata("artificial");
                currentPage="artificial";
                break;
            case R.id.cat_android:
                displaydata("android");
                currentPage="android";
                break;
            case R.id.cat_java:
                displaydata("java");
                currentPage="java";
                break;
            case R.id.cat_php:
                displaydata("php");
                currentPage="php";
                break;
            case R.id.cat_python:
                displaydata("python");
                currentPage="python";
                break;
            case R.id.cat_web:
                displaydata("web");
                currentPage="web";
                break;
            case R.id.cat_node:
                displaydata("node");
                currentPage="node";
                break;
            case R.id.cat_network:
                displaydata("network");
                currentPage="network";
                break;

        }
    }


    private class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectHolder> {
        private final List<ProjectInfo> data;
        private LayoutInflater inflater;
        private Context c;
        private int lastPosition=-1;

        public ProjectAdapter(Context context, List<ProjectInfo> list) {
            data = list;
            c = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ProjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.project_card, parent, false);
            ProjectHolder projectViewHolder = new ProjectHolder(view);
            return projectViewHolder;
        }



        @Override
        public void onBindViewHolder(ProjectHolder holder, int position) {
            ProjectInfo info = data.get(position);
            holder.title.setText(info.title);
            holder.status.setText(info.status);
            holder.category.setText(info.category);
            holder.content.setText(info.content);
            imageLoader.get(info.conimg_url, ImageLoader.getImageListener(holder.cp, R.drawable.ic_loader, R.drawable.hippo));
            imageLoader.get(info.disimg_url, ImageLoader.getImageListener(holder.dp, R.drawable.ic_loader, R.drawable.hippo));

            setAnimation(holder.wl, position);

        }
        private void setAnimation(View viewToAnimate, int position)
        {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition)
            {
                Animation animation = AnimationUtils.loadAnimation(c, android.R.anim.slide_in_left);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }

        @Override
        public int getItemCount() {

            return data.size();
        }

        class ProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView title, content,status,category,learnmore;
            ImageView dp,cp;
            FrameLayout wl;

            public ProjectHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
                learnmore = (TextView) itemView.findViewById(R.id.more_but);
                learnmore.setOnClickListener(this);
                status = (TextView) itemView.findViewById(R.id.status);
                category = (TextView) itemView.findViewById(R.id.category);
                content = (TextView) itemView.findViewById(R.id.content_text);
                dp = (ImageView) itemView.findViewById(R.id.display_image);
                cp = (ImageView) itemView.findViewById(R.id.content_image);
                wl = (FrameLayout) itemView.findViewById(R.id.wholecard);
            }

            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.more_but)
                {
                    ProjectsParser parser=null;
                    try {
                        parser = new ProjectsParser((JSONObject) array.get(getPosition()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppController.getInstance().setProjectParser(parser);
                    Intent intent=new Intent(c,ProjectFullActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppController.getInstance().cancelPendingRequests("projects");
    }
}
