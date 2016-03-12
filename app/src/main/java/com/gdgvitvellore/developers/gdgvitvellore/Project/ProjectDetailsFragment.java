package com.gdgvitvellore.developers.gdgvitvellore.Project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdgvitvellore.developers.gdgvitvellore.Parser.ProjectsParser;
import com.gdgvitvellore.developers.gdgvitvellore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shalini on 24-02-2015.
 */
public class ProjectDetailsFragment extends Fragment {
    private ProjectsParser projectsParser;
    private RecyclerView recyclerView;
    private ProjectDetailsAdapter adapter;

    public ProjectDetailsFragment() {
    }
    @SuppressLint("ValidFragment")
    public ProjectDetailsFragment(ProjectsParser p) {
        projectsParser=p;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_project_details, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.project_details_recycler_view);
        adapter = new ProjectDetailsAdapter(getActivity(),getProjectData());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    public List<ProjectDetailsInfo> getProjectData(){
        List<ProjectDetailsInfo> list=new ArrayList<>();
        String[] title={"Project Name","Status","Category","Project Lead","Project Team","Start Time","End Time","Budget"};
        String[] desc={projectsParser.getName(),projectsParser.getStatus(),projectsParser.getCategory(),projectsParser.getProjectLead(),projectsParser.getProjectTeamListString(),projectsParser.getStartTime(),projectsParser.getEndTime(),projectsParser.getBudget()};
        for(int i=0;i<title.length;i++){
            ProjectDetailsInfo info=new ProjectDetailsInfo();
            info.title=title[i];
            info.des=desc[i];
            list.add(info);
        }
        return list;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    private class ProjectDetailsAdapter extends RecyclerView.Adapter<ProjectDetailsAdapter.ProjectDetailsHolder> {
        private final List<ProjectDetailsInfo> data;
        private LayoutInflater inflater;
        private Context c;

        public ProjectDetailsAdapter(Context context, List<ProjectDetailsInfo> list) {
            data = list;
            c = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ProjectDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.fragment_project_details_recycler, parent, false);
            ProjectDetailsHolder projectViewHolder = new ProjectDetailsHolder(view);
            return projectViewHolder;
        }



        @Override
        public void onBindViewHolder(ProjectDetailsHolder holder, int position) {
            ProjectDetailsInfo info = data.get(position);
            holder.title.setText(info.title);
            holder.des.setText(info.des);
        }

        @Override
        public int getItemCount() {

            return data.size();
        }

        class ProjectDetailsHolder extends RecyclerView.ViewHolder{

            TextView title, des;
            RelativeLayout layout;

            public ProjectDetailsHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.row_head);
                des = (TextView) itemView.findViewById(R.id.row_text);
            }

        }
    }
}
