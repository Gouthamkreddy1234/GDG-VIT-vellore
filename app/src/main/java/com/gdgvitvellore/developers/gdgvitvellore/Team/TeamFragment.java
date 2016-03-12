package com.gdgvitvellore.developers.gdgvitvellore.Team;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.gdgvitvellore.developers.gdgvitvellore.Feeds.HomeActivity;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Parser.AppController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shalini on 18-03-2015.
 */
public class TeamFragment extends Fragment {
    private RecyclerView recyclerView;
    private static int TECHNICAL=0,MANAGEMENT=1;
    private int position=0;
    private DrawerLayout drawerLayout;
    private TeamNavigationFragment teamNavigationFragment;
    private TeamAdapter adapter;
    public TeamFragment() {
    }
    @SuppressLint("ValidFragment")
    public TeamFragment(int pos,TeamNavigationFragment t,DrawerLayout d) {

        position=pos;
        teamNavigationFragment=t;
        drawerLayout=d;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.team_fragment_layout, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.team_recycler_view);
        if(position==TECHNICAL)
        adapter = new TeamAdapter(getActivity(),getTechTeamData());
        else
            adapter = new TeamAdapter(getActivity(),getMngTeamData());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    public List<TeamInfo> getTechTeamData(){
        List<TeamInfo> list=new ArrayList<>();
        String[] title={"Amit","Ayush","Chirag","Darshan","Goutham","Prince","Prerit","Shamvil"};
        String[] desc={"Web Designer","Web Developer","Management","Android Dev","Android Dev","Android Dev","Android and Web","Management"};
        String[] phone={"9524461744","9524461744","9524461744","9524461744","9524461744","9524461744","9524461744","9524461744"};
        String[] mail={"abc123@gmail.com","abc123@gmail.com","abc123@gmail.com","abc123@gmail.com","abc123@gmail.com","abc123@gmail.com","abc123@gmail.com","abc123@gmail.com"};
        //String[] url={"http://i.stack.imgur.com/Sv4BC.png","http://i.stack.imgur.com/N2QWv.png","http://homepages.cae.wisc.edu/~ece533/images/baboon.png","http://homepages.cae.wisc.edu/~ece533/images/barbara.png","http://i.stack.imgur.com/Sv4BC.png","http://i.stack.imgur.com/N2QWv.png","http://i.stack.imgur.com/Sv4BC.png","http://i.stack.imgur.com/Sv4BC.png"};
        String url[]={"http://www.gdgvitvellore.com/images/team/prerit.png","http://www.gdgvitvellore.com/images/team/shamvil.jpg","http://www.gdgvitvellore.com/images/portfolio/13BCE0037_Nikhil%20Verma.png","http://www.gdgvitvellore.com/images/portfolio/12BIT0224_SARTHAK%20SAXENA.jpg","http://www.gdgvitvellore.com/images/portfolio/13BAM0032_SAURAJ%20BABU.jpg","http://www.gdgvitvellore.com/images/portfolio/12MSE0363_Rajalakshmi%20S.jpg","http://www.gdgvitvellore.com/images/portfolio/12BIT0170_Tazy%20Khan.jpg","http://www.gdgvitvellore.com/images/portfolio/12BCE0357_Nandani%20Madhukar.jpg"};
        for(int i=0;i<title.length;i++){
            TeamInfo info=new TeamInfo();
            info.name=title[i];
            info.desg=desc[i];
            info.phone=phone[i];
            info.mail=mail[i];
            info.url=url[i];
            list.add(info);
        }
        return list;

    }
    public List<TeamInfo> getMngTeamData(){
        List<TeamInfo> list=new ArrayList<>();
        String[] title={"Anshul","Abhishek","Ankit","Daksh","Harshit","Hari Krishna","Rahul","Sarthak"};
        String[] desc={"Secretary","Manager","Project Manager","Marketing Head","Marketing Co","Marketing Co","Marketing Co","Marketing Co"};
        String[] phone={"9524461744","9524461744","9524461744","9524461744","9524461744","9524461744","9524461744","9524461744"};
        String[] mail={"abc123@gmail.com","abc123@gmail.com","abc123@gmail.com","abc123@gmail.com","abc123@gmail.com","abc123@gmail.com","abc123@gmail.com","abc123@gmail.com"};
        String url[]={"http://www.gdgvitvellore.com/images/team/prerit.png","http://www.gdgvitvellore.com/images/team/shamvil.jpg","http://www.gdgvitvellore.com/images/portfolio/13BCE0037_Nikhil%20Verma.png","http://www.gdgvitvellore.com/images/portfolio/12BIT0224_SARTHAK%20SAXENA.jpg","http://www.gdgvitvellore.com/images/portfolio/13BAM0032_SAURAJ%20BABU.jpg","http://www.gdgvitvellore.com/images/portfolio/12MSE0363_Rajalakshmi%20S.jpg","http://www.gdgvitvellore.com/images/portfolio/12BIT0170_Tazy%20Khan.jpg","http://www.gdgvitvellore.com/images/portfolio/12BCE0357_Nandani%20Madhukar.jpg"};
        for(int i=0;i<title.length;i++){
            TeamInfo info=new TeamInfo();
            info.name=title[i];
            info.desg=desc[i];
            info.phone=phone[i];
            info.mail=mail[i];
            info.url=url[i];
            list.add(info);
        }
        return list;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    private class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamHolder> {
        private final List<TeamInfo> data;
        private LayoutInflater inflater;
        private Context c;

        public TeamAdapter(Context context, List<TeamInfo> list) {
            data = list;
            c = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public TeamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.team_fragment_recycler, parent, false);
            TeamHolder teamHolder = new TeamHolder(view);
            return teamHolder;
        }



        @Override
        public void onBindViewHolder(TeamHolder holder, int pos) {
            TeamInfo info = data.get(pos);
            holder.title.setText(info.name);
            AppController.getInstance().getImageLoader().get(info.url, ImageLoader.getImageListener(holder.img, R.drawable.ic_account_circle, R.drawable.ic_account_circle));

            if(position==TECHNICAL) {
                if (pos != 1 && pos != 6) {
                    holder.alpha.setVisibility(TextView.VISIBLE);
                    holder.alpha.setText(info.name.substring(0, 1));
                }
                if (pos != 0 && pos != 5) {
                    holder.bl.setVisibility(LinearLayout.VISIBLE);
                }
            }
            if(position==MANAGEMENT){
                if (pos!=1&& pos != 2 && pos != 5) {
                    holder.alpha.setVisibility(TextView.VISIBLE);
                    holder.alpha.setText(info.name.substring(0, 1));
                }
                if (pos != 0 && pos != 1 && pos!=4) {
                    holder.bl.setVisibility(LinearLayout.VISIBLE);
                }
            }

        }

        @Override
        public int getItemCount() {

            return data.size();
        }

        class TeamHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView title,alpha;
            ImageView img;
            LinearLayout bl;

            public TeamHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.row_text);
                alpha= (TextView) itemView.findViewById(R.id.row_alpha);
                img=(ImageView)itemView.findViewById(R.id.dp);
                bl=(LinearLayout)itemView.findViewById(R.id.bottom_line);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                teamNavigationFragment.setContent(data.get(getPosition()));
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        }
    }
}
