package com.gdgvitvellore.developers.gdgvitvellore.Navigation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdgvitvellore.developers.gdgvitvellore.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by shalini on 23-02-2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.NavigationViewHolder> {
    private LayoutInflater inflater;
    private List<NavigationDrawerInfo> data = Collections.emptyList();
    private Context c;
    private ClickListener clickListener;
    public NavigationDrawerAdapter(Context context,List<NavigationDrawerInfo> list)
    {
        c=context;
        inflater=LayoutInflater.from(context);
        data=list;
    }
    @Override
    public NavigationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.navigation_recycler_row,parent,false);
        NavigationViewHolder navigationViewHolder=new NavigationViewHolder(view);
        return navigationViewHolder;
    }

    @Override
    public void onBindViewHolder(NavigationViewHolder holder, int position) {
        NavigationDrawerInfo info=data.get(position);
        holder.title.setText(info.name);
        holder.imageView.setImageResource(info.iconId);
        if(position==3)
            holder.bl.setVisibility(LinearLayout.VISIBLE);
    }

    @Override
    public int getItemCount() {

        return data.size();
    }
    public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }
    public void setClickListener(ClickListener cl)
    {
        this.clickListener=cl;
    }
    class NavigationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        ImageView imageView;
        RelativeLayout layout;
        LinearLayout bl;
        public NavigationViewHolder(View itemView)
        {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.navigation_row_text);
            imageView=(ImageView)itemView.findViewById(R.id.navigation_row_image);
            itemView.setOnClickListener(this);
            bl=(LinearLayout)itemView.findViewById(R.id.bottom_line);
        }

        @Override
        public void onClick(View v) {
          //delete(getPosition());
          //  Toast.makeText(c,"Item clicked"+getPosition(),Toast.LENGTH_SHORT).show();
            if(clickListener!=null){
                clickListener.onRecyclerItemClick(v,getPosition());
            }
        }
    }
    public interface ClickListener{
        public void onRecyclerItemClick(View v, int position);

    }
}
