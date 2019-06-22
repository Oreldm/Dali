package com.ormil.daliproject.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ormil.daliproject.Helpers.UserMonitorHelper;
import com.ormil.daliproject.Models.ArtworkModel;
import com.ormil.daliproject.R;
import com.ormil.daliproject.Services.ExitService;
import com.ormil.daliproject.Services.HttpService;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    public static final String SELF_USER_ID = "self_UserID";
    public static final int ACTIVITY_NUMBER = 2;

    private String selfID = "";

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private LatLng currentLocation;

    private ImageButton m_OpenArBtn;
    private ImageButton m_NotificationBtn;
    private ImageButton m_ProfileButton;
    private ImageButton m_SearchButton;

    private ArrayList<ArtworkModel> artworkModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_maps);
        UserMonitorHelper.screens.add(ACTIVITY_NUMBER);
        Intent intent = new Intent(this, ExitService.class);
        startService(intent);

        selfID = getIntent().getExtras().getString(SELF_USER_ID);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        m_OpenArBtn = findViewById(R.id.arButton);
        m_OpenArBtn.setOnClickListener(view -> onArButtonClick());

        m_NotificationBtn = findViewById(R.id.notificationsButton);
        m_NotificationBtn.setOnClickListener(view -> onNotificationButtonClick());

        m_ProfileButton = findViewById(R.id.profileButton);
        m_ProfileButton.setOnClickListener(view -> onProfileButtonClick());

        m_SearchButton = findViewById(R.id.searchButton);
        m_SearchButton.setOnClickListener(view -> onSearchButtonClick());

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            gpsActions();
        }
    }

    private void gpsActions(){

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                updateMarkers(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        try {
            mMap.setMyLocationEnabled(true);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 3, mLocationListener);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3, 3, mLocationListener);
        } catch (SecurityException e) {
            Log.e(TAG, "No permissions");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gpsActions();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void updateMarkers(Location location) {
        try {

            Log.d(TAG, "Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());

            String response = HttpService.getArtworkByLocation(currentLocation.latitude, currentLocation.longitude);

            Type listType = new TypeToken<ArrayList<ArtworkModel>>() {}.getType();
            artworkModels = new Gson().fromJson(response, listType);

            mMap.clear();

            artworkModels.forEach(artwork -> {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(artwork.getLat(), artwork.getLng()));
                markerOptions.title(artwork.getName());
                mMap.addMarker(markerOptions);
            });
        } catch(Exception e) {
            Log.e(TAG, "Failed to retrieve arts");
        }
    }

    private void onArButtonClick() {
        Intent intent = new Intent(this, ArActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable(ArActivity.CURRENT_LOCATION_KEY, currentLocation);

        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void onNotificationButtonClick() {
        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);
    }

    private void onProfileButtonClick() {
        Intent intent = new Intent(this, ProfileActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(ProfileActivity.PROFILE_TYPE_KEY, ProfileActivity.ProfileType.USER_PROFILE);
        bundle.putString(ProfileActivity.PROFILE_USER_ID, selfID);

        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void onSearchButtonClick() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
