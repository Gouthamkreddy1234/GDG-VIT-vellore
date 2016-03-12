package com.gdgvitvellore.developers.gdgvitvellore.Volley;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.melnykov.fab.FloatingActionButton;

/**
 * Created by shalini on 18-02-2015.
 */
public class SliderMapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    SupportMapFragment mMapFragment;
    FloatingActionButton fab;
    private double mylon,mylat;

    public SliderMapFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.contact_layout_map_fragment, container, false);
        mMapFragment = SupportMapFragment.newInstance();
        fab=(FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.maplayout, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(12.969129, 79.155787))
                .title("GDG VIT Vellore"));
        LatLng coordinate = new LatLng(12.969129, 79.155787);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 5);
        googleMap.animateCamera(yourLocation);

    }

    @Override
    public void onClick(View v) {
        String geodir="http://www.maps.google.com/maps?"+"daddr="+12.969129+","+79.155787;
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(geodir));
        startActivity(intent);
    }
}
