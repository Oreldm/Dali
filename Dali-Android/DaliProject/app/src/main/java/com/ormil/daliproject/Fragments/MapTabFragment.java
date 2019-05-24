package com.ormil.daliproject.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ormil.daliproject.Models.ArtworkModel;
import com.ormil.daliproject.R;
import com.ormil.daliproject.activities.ProfileActivity;

import java.util.List;
import java.util.Objects;

public class MapTabFragment extends Fragment {

    private static final String TAG = "MapTabFragment";

    MapView mMapView;
    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private List<ArtworkModel> artworkModels;


    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        try {
            artworkModels = args.getParcelableArrayList(ProfileActivity.ARTLIST_KEY);
        } catch (NullPointerException e) {
            Toast toast = Toast.makeText(getContext(), "Error while loading art map", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_tab_fragment, container, false);

        Context context = getContext();
        assert context != null;

        mMapView = rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                else {
                    mMap.setMyLocationEnabled(true);
                }

                if(artworkModels != null) {
                    for (ArtworkModel artwork : artworkModels) {
                        LatLng loc = new LatLng(artwork.getLat(), artwork.getLng());
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(loc);
                        markerOptions.title(artwork.getName());
                        //markerOptions.snippet(artwork.getInfo());
                        googleMap.addMarker(markerOptions);
                    }
                }
            }
        });

        return rootView;
    }

}
