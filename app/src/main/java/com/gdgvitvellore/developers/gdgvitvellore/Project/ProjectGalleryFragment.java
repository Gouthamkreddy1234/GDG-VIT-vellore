package com.gdgvitvellore.developers.gdgvitvellore.Project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.gdgvitvellore.developers.gdgvitvellore.Navigation.NavigationDrawerAdapter;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ProjectsParser;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;
import com.gdgvitvellore.developers.gdgvitvellore.Volley.GalleryActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shalini on 24-02-2015.
 */
public class ProjectGalleryFragment extends Fragment implements NavigationDrawerAdapter.ClickListener{
    private ProjectsParser projectsParser;
    private RecyclerView recyclerView;
    private ProjectGalleryAdapter adapter;
    ImageLoader imageLoader;

    GridLayoutManager gridLayoutManager;
    ImageView recent_image;
    String[] url1;
    List<ProjectGalleryInfo> list;
    public ProjectGalleryFragment() {
    }
    @SuppressLint("ValidFragment")
    public ProjectGalleryFragment(ProjectsParser p) {
        projectsParser=p;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_project_gallery, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.project_gallery_recycler_view);
        //recent_image = (ImageView) rootView.findViewById(R.id.row_image_recent);
        adapter = new ProjectGalleryAdapter(getActivity(),getProjectData());
        adapter.setClickListener(this);
        gridLayoutManager=new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if(i==0)
                return 2;
                else
                    return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        imageLoader = AppController.getInstance().getImageLoader();
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    public List<ProjectGalleryInfo> getProjectData(){
        list=new ArrayList<>();
        url1=new String[projectsParser.getImageCount()];
        for(int i=0;i<projectsParser.getImageCount();i++){
            ProjectGalleryInfo info=new ProjectGalleryInfo();
            info.url=projectsParser.getImageAttributes(projectsParser.getImageObject(i)).get("url");
            url1[i]=info.url;
            info.des=projectsParser.getImageAttributes(projectsParser.getImageObject(i)).get("description");
            list.add(info);
        }
        return list;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    private class ProjectGalleryAdapter extends RecyclerView.Adapter<ProjectGalleryAdapter.ProjectGalleryHolder> {
        private final List<ProjectGalleryInfo> data;
        private LayoutInflater inflater;
        private Context c;
        private NavigationDrawerAdapter.ClickListener clickListener;

        public ProjectGalleryAdapter(Context context, List<ProjectGalleryInfo> list) {
            data = list;
            c = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ProjectGalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.fragment_project_gallery_recycler, parent, false);
            ProjectGalleryHolder projectViewHolder = new ProjectGalleryHolder(view);
            return projectViewHolder;
        }

        public void setClickListener(NavigationDrawerAdapter.ClickListener cl) {
            this.clickListener = cl;
        }


        @Override
        public void onBindViewHolder(ProjectGalleryHolder holder, int position) {
            //if(position==0)
            //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
            //else
            //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
            ProjectGalleryInfo info = data.get(position);
            imageLoader.get(info.url, ImageLoader.getImageListener(holder.image, R.drawable.ic_loader, R.drawable.hippo));
            holder.des.setText(info.des);
            if(position==1||position==0){
                holder.header.setVisibility(TextView.VISIBLE);
            }
            else{
                holder.header.setVisibility(TextView.INVISIBLE);

            }
            if(position==0){
                holder.header.setText("Recent");
            }
            if(position==1){
                holder.header.setText("Previous");
            }
        }

        @Override
        public int getItemCount() {

            return data.size();
        }

        class ProjectGalleryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView des,header;
            ImageView image;
            RelativeLayout layout;

            public ProjectGalleryHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.row_image);
                des = (TextView) itemView.findViewById(R.id.row_text);
                itemView.setOnClickListener(this);
                image.setOnClickListener(this);
                header=(TextView)itemView.findViewById(R.id.headertext);
            }

            @Override
            public void onClick(View v) {
                //delete(getPosition());
                //  Toast.makeText(c,"Item clicked"+getPosition(),Toast.LENGTH_SHORT).show();
                if (clickListener != null) {
                    clickListener.onRecyclerItemClick(v, getPosition());
                }
            }
        }
    }


    @Override
    public void onRecyclerItemClick(View v, int position) {

            Intent i=new Intent(getActivity(), GalleryActivity.class);
            Bundle b=new Bundle();
            b.putStringArray("imagesarray",url1);
            b.putInt("currentimage",position);
            i.putExtra("imagesbundle",b);
            getActivity().startActivity(i);
    }

}
