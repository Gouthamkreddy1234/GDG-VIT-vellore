package com.gdgvitvellore.developers.gdgvitvellore.Volley;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdgvitvellore.developers.gdgvitvellore.Events.Eventsweb;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.melnykov.fab.FloatingActionButton;

/**
 * Created by shalini on 18-02-2015.
 */public class SliderContactFragment extends Fragment implements View.OnClickListener {
    private TextView content,vt,ct,mt;
    private LinearLayout va,ma,ca;
    private FloatingActionButton f;
    private LinearLayout view;
    public SliderContactFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.contact_layout_contact_fragment, container, false);
        va=(LinearLayout)rootView.findViewById(R.id.visitus);
        ma=(LinearLayout)rootView.findViewById(R.id.mailus);
        ca=(LinearLayout)rootView.findViewById(R.id.callus);
        content=(TextView)rootView.findViewById(R.id.content);
        vt=(TextView)rootView.findViewById(R.id.vatext);
        ct=(TextView)rootView.findViewById(R.id.catext);
        view=(LinearLayout)rootView.findViewById(R.id.view);
        view.setOnClickListener(this);
        mt=(TextView)rootView.findViewById(R.id.matext);
        f=(FloatingActionButton)rootView.findViewById(R.id.fab);
        f.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        vt.setTextColor(getResources().getColor(R.color.colorPrimary));
        content.setText("VIT Vellore,\nVellore, TN 632014");
        va.setOnClickListener(this);
        ca.setOnClickListener(this);
        ma.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.visitus){
            vt.setTextColor(getResources().getColor(R.color.colorPrimary));
            ct.setTextColor(getResources().getColor(R.color.dgray));
            mt.setTextColor(getResources().getColor(R.color.dgray));
            content.setText("VIT Vellore,\nVellore, TN 632014");
            Animation animation = AnimationUtils.loadAnimation(getActivity(),android.R.anim.fade_in);
            view.startAnimation(animation);

        }
        if(v.getId()== R.id.mailus){

            vt.setTextColor(getResources().getColor(R.color.dgray));
            ct.setTextColor(getResources().getColor(R.color.dgray));
            mt.setTextColor(getResources().getColor(R.color.colorPrimary));
            content.setText("gdgvitvellore@gmail.com");
            Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
            view.startAnimation(animation);
        }
        if(v.getId()== R.id.callus){

            vt.setTextColor(getResources().getColor(R.color.dgray));
            ct.setTextColor(getResources().getColor(R.color.colorPrimary));
            mt.setTextColor(getResources().getColor(R.color.dgray));
            content.setText("0416-2320145");
            Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
            view.startAnimation(animation);
        }
        if(v.getId()== R.id.fab){

            Intent i=new Intent(getActivity(),Eventsweb.class);
            i.putExtra("title","GDGVIT Website");
            startActivity(i);
        }
        if(v.getId() == R.id.view)
        {
            String redirect = content.getText().toString();
            if(redirect == "0416-2320145")
            {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0416-2320145"));
                startActivity(Intent.createChooser(intent, "Call using"));

            }

            if(redirect == "gdgvitvellore@gmail.com")
            {
                String[] addresses={"gdgvitvellore@gmail.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, addresses);
                emailIntent.setType("text/plain");
                startActivity(Intent.createChooser(emailIntent, "Send your email in:"));
            }

            if(redirect == "VIT Vellore,\nVellore, TN 632014")
            {
                String mLat="12.969129";
                String mLong="79.155787";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + mLat + "," + mLong));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, "Open using:"));
            }


        }
    }
}
