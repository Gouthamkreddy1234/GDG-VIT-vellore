package com.gdgvitvellore.developers.gdgvitvellore.Project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ActivitiesFeedPage;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.ProjectsParser;

import org.json.JSONObject;

/**
 * Created by shalini on 24-02-2015.
 */
public class ProjectDescriptionFragment extends Fragment {
    private ImageView projectImage;
    private TextView projectName,projectStatus,projectDescription;
    //private String url="http://www.isoivit.in/Details.json";
    private ActivitiesFeedPage projectsArray;
    private ImageLoader imageLoader;
    private ProjectsParser parser;

    public ProjectDescriptionFragment() {
    }
    @SuppressLint("ValidFragment")
    public ProjectDescriptionFragment(ProjectsParser p) {
        parser=p;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_project_description, container, false);
        projectImage=(ImageView)rootView.findViewById(R.id.projectimage);
        projectName=(TextView)rootView.findViewById(R.id.projectname);
        projectStatus=(TextView)rootView.findViewById(R.id.projectstatus);
        projectDescription=(TextView)rootView.findViewById(R.id.projectdescription);
        return rootView;
    }

    private void setContent() {
        parser.initializeImages();
        projectName.setText(parser.getName());
        projectStatus.setText(parser.getStatus());
        projectDescription.setText(parser.getLongDescription());
        imageLoader.get(parser.getContentImageUrl(), ImageLoader.getImageListener(projectImage, R.drawable.ic_loader, R.drawable.hippo));

    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageLoader= AppController.getInstance().getImageLoader();
        setContent();

    }
}
